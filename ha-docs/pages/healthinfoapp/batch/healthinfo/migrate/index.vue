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
          text: "健康情報連携バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthinfo/migrate",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>健康情報連携を開始",
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
          text: "第1引数から対象日を取得<br/>実行を識別するIDを生成",
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
          text: "DockerでJavaバッチを起動して待機<br/>対象日・実行IDを渡す",
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
          text: "Java処理<br/>健康情報連携を開始",
          edgeType: "circle",
          next: ["java_params"],
        },
        {
          id: "java_params",
          text: "指定時は対象日の日付形式を検証<br/>Java引数が未指定なら当日",
          edgeType: "diamond",
          next: ["java_rejected", "java_begin"],
        },
        {
          id: "java_rejected",
          text: "不正な対象日: ジョブ起動を拒否",
          edgeType: "round",
        },
        {
          id: "java_begin",
          text: "開始ログを記録<br/>対象日0時から23時59分59秒を対象化",
          edgeType: "round",
          next: ["java_read"],
        },
        {
          id: "java_read",
          text: "HEALTH_INFO検索<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>対象日 00:00:00 AND 対象日 23:59:59<br/>対象日 = バッチ引数.d・未指定時は当日<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID ASCでページ取得<br/>削除状態による絞込なし<br/>次の1件があるか・0件は処理終了",
          edgeType: "diamond",
          next: ["java_exception", "java_prepare", "java_notify"],
        },
        {
          id: "java_prepare",
          text: "1件のユーザID・健康情報を連携用に整形<br/>連携先の移行設定を付与",
          edgeType: "round",
          next: ["java_id"],
        },
        {
          id: "java_id",
          text: "1件ごとに通信を識別するIDを採番",
          edgeType: "round",
          next: ["java_api"],
        },
        {
          id: "java_api",
          text: "健康情報連携APIを呼び出す",
          edgeType: "round",
          next: ["java_exception", "java_log"],
        },
        {
          id: "java_log",
          text: "通信ログをSQS送信<br/>API応答の結果はここでは判定しない",
          edgeType: "round",
          next: ["java_exception", "java_next"],
        },
        {
          id: "java_next",
          text: "1件単位で処理を確定<br/>次の健康情報へ",
          edgeType: "round",
          next: ["java_read"],
        },
        {
          id: "java_exception",
          text: "外へ伝わった例外を処理<br/>一部通信例外は上限付き再試行<br/>HTTP4xx例外は最大10件スキップ",
          edgeType: "diamond",
          next: ["java_next", "java_failed"],
        },
        {
          id: "java_failed",
          text: "継続不可: ジョブ失敗を記録",
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

<style scoped></style>
