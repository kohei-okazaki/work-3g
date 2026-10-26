<template>
  <div>
    <v-row justify="center">
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppDocs :flow="flow" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppDocs from "~/components/AppDocs.vue";

export default {
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppDocs,
  },
  data: function () {
    return {
      breadcrumbs: [
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "健康管理バッチ",
          disabled: false,
          href: "/healthinfoapp/batch",
        },
        {
          text: "月次健康情報集計バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthinfo/monthlySummary",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>月次健康情報出力を開始",
          edgeType: "circle",
          next: ["sh_common"],
        },
        {
          id: "sh_common",
          text: "共通設定を読込",
          edgeType: "round",
          next: ["sh_lock"],
        },
        {
          id: "sh_lock",
          text: "同じシェルの実行ロックを取得",
          edgeType: "diamond",
          next: ["sh_locked", "sh_args"],
        },
        {
          id: "sh_locked",
          text: "取得不可: 二重起動として終了<br/>終了コード50",
          edgeType: "round",
        },
        {
          id: "sh_args",
          text: "第1引数から対象年月を取得<br/>実行を識別するIDを生成",
          edgeType: "round",
          next: ["sh_missing", "sh_run"],
        },
        {
          id: "sh_missing",
          text: "引数未指定: Java起動前に停止",
          edgeType: "round",
        },
        {
          id: "sh_run",
          text: "DockerでJavaバッチを起動して待機<br/>対象年月・実行IDを渡す",
          edgeType: "round",
          next: ["sh_failed", "sh_end"],
        },
        {
          id: "sh_failed",
          text: "Docker実行が非0: シェル停止",
          edgeType: "round",
        },
        {
          id: "sh_end",
          text: "コマンド完了後に終了メッセージ出力",
          edgeType: "round",
        },
        {
          id: "java_start",
          text: "Java処理<br/>月次健康情報出力を開始",
          edgeType: "circle",
          next: ["java_params"],
        },
        {
          id: "java_params",
          text: "指定時は対象年月の形式を検証<br/>Java引数が未指定なら当月",
          edgeType: "diamond",
          next: ["java_rejected", "java_begin"],
        },
        {
          id: "java_rejected",
          text: "不正な対象年月: ジョブ起動を拒否",
          edgeType: "round",
        },
        {
          id: "java_begin",
          text: "開始ログを記録<br/>対象月の月初から月末を対象化",
          edgeType: "round",
          next: ["java_open"],
        },
        {
          id: "java_open",
          text: "ヘッダー付きCSVの出力を開始<br/>同名ファイルは上書き",
          edgeType: "round",
          next: ["java_failed", "java_read"],
        },
        {
          id: "java_read",
          text: "HEALTH_INFO検索<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>対象月の月初 00:00:00 AND 月末 23:59:59<br/>対象月 = バッチ引数.m・未指定時は当月<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID ASCでページ取得<br/>削除状態による絞込なし・次の1件があるか",
          edgeType: "diamond",
          next: ["java_failed", "java_convert", "java_close"],
        },
        {
          id: "java_convert",
          text: "1件ごとにCSV出力項目へ変換<br/>健康値・作成日時・登録更新日時",
          edgeType: "round",
          next: ["java_failed", "java_write"],
        },
        {
          id: "java_write",
          text: "CSVに1件書込み・処理を確定",
          edgeType: "round",
          next: ["java_failed", "java_read"],
        },
        {
          id: "java_failed",
          text: "読込・変換・CSV出力の異常<br/>ジョブ失敗を記録",
          edgeType: "round",
          next: ["java_close"],
        },
        {
          id: "java_close",
          text: "CSV出力を終了<br/>0件でもヘッダーを保持",
          edgeType: "round",
          next: ["java_status"],
        },
        {
          id: "java_status",
          text: "処理が失敗状態でないか",
          edgeType: "diamond",
          next: ["java_zip", "java_notify"],
        },
        {
          id: "java_zip",
          text: "失敗なし: CSVをgzip圧縮",
          edgeType: "round",
          next: ["java_post_error", "java_s3"],
        },
        {
          id: "java_s3",
          text: "対象年月の圧縮CSVをS3へ転送",
          edgeType: "round",
          next: ["java_post_error", "java_file_notify"],
        },
        {
          id: "java_file_notify",
          text: "圧縮ファイルをSlack添付で通知",
          edgeType: "round",
          next: ["java_post_error", "java_cleanup"],
        },
        {
          id: "java_cleanup",
          text: "ローカルCSV・圧縮ファイルを削除",
          edgeType: "round",
          next: ["java_post_error", "java_notify"],
        },
        {
          id: "java_post_error",
          text: "後処理の異常をログ記録<br/>ジョブ失敗へは変更しない",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_notify",
          text: "終了状態をSlack通知・ログ記録<br/>ジョブ失敗時は例外詳細も通知",
          edgeType: "round",
          next: ["java_end"],
        },
        {
          id: "java_end",
          text: "Java処理を終了",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>