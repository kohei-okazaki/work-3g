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
          text: "日次健康情報データ分析連携バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/daily/healthinfo",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>開始ログ・共通設定の読込",
          edgeType: "circle",
          next: ["sh_lock"],
        },
        {
          id: "sh_lock",
          text: "二重起動防止ロックを取得",
          edgeType: "diamond",
          next: ["sh_locked", "sh_args"],
        },
        {
          id: "sh_locked",
          text: "取得不可：終了コード50で終了",
          edgeType: "circle",
        },
        {
          id: "sh_args",
          text: "取得成功：対象日引数を受け取り<br/>実行識別子を発行",
          edgeType: "round",
          next: ["sh_run", "sh_noargs"],
        },
        {
          id: "sh_noargs",
          text: "対象日引数なし：異常終了",
          edgeType: "circle",
        },
        {
          id: "sh_run",
          text: "対象日・実行識別子を渡して<br/>分析バッチのコンテナを実行",
          edgeType: "round",
          next: ["sh_result"],
        },
        {
          id: "sh_result",
          text: "コンテナ実行コマンドの終了結果",
          edgeType: "diamond",
          next: ["sh_ok", "sh_error"],
        },
        {
          id: "sh_ok",
          text: "終了コード0：終了ログを出力",
          edgeType: "round",
          next: ["sh_end"],
        },
        {
          id: "sh_error",
          text: "非0：その時点で異常終了",
          edgeType: "circle",
        },
        {
          id: "sh_end",
          text: "シェル終了",
          edgeType: "circle",
        },
        {
          id: "java_start",
          text: "Java処理<br/>対象日引数を受け取る",
          edgeType: "circle",
          next: ["java_date"],
        },
        {
          id: "java_date",
          text: "対象日の日付形式を検証",
          edgeType: "diamond",
          next: ["java_invalid", "java_begin"],
        },
        {
          id: "java_invalid",
          text: "日付不正：ジョブを開始せず終了",
          edgeType: "circle",
        },
        {
          id: "java_begin",
          text: "検証成功：ジョブ開始を記録<br/>対象日が空なら当日を使用",
          edgeType: "round",
          next: ["java_csv"],
        },
        {
          id: "java_csv",
          text: "ヘッダー付きCSVを準備",
          edgeType: "round",
          next: ["java_search", "java_failed"],
        },
        {
          id: "java_search",
          text: "HEALTH_INFO検索<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>対象日 00:00:00 AND 対象日 23:59:59<br/>対象日 = バッチ引数.d・未指定時は当日<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID ASCでページ取得<br/>削除状態による絞込なし",
          edgeType: "round",
          next: ["java_count", "java_failed"],
        },
        {
          id: "java_count",
          text: "検索結果の有無",
          edgeType: "diamond",
          next: ["java_empty", "java_rows"],
        },
        {
          id: "java_empty",
          text: "0件：ヘッダーのみで出力を終了",
          edgeType: "round",
          next: ["java_status"],
        },
        {
          id: "java_rows",
          text: "データあり：出力項目・日時を整形し<br/>1件ずつCSVに追記して全件処理",
          edgeType: "round",
          next: ["java_status", "java_failed"],
        },
        {
          id: "java_failed",
          text: "検索・変換・CSV出力で例外<br/>ジョブを失敗として扱う",
          edgeType: "round",
          next: ["java_status"],
        },
        {
          id: "java_status",
          text: "ステップの終了状態",
          edgeType: "diamond",
          next: ["java_skip", "java_gzip"],
        },
        {
          id: "java_skip",
          text: "失敗：圧縮・転送を省略",
          edgeType: "round",
          next: ["java_failure_notice"],
        },
        {
          id: "java_gzip",
          text: "正常：CSVをgzip圧縮",
          edgeType: "round",
          next: ["java_upload", "java_post_error"],
        },
        {
          id: "java_upload",
          text: "対象日別にS3へアップロード",
          edgeType: "round",
          next: ["java_file_notice", "java_post_error"],
        },
        {
          id: "java_file_notice",
          text: "圧縮ファイルをSlackへ送信",
          edgeType: "round",
          next: ["java_cleanup", "java_post_error"],
        },
        {
          id: "java_cleanup",
          text: "ローカルのCSV・圧縮ファイルを削除",
          edgeType: "round",
          next: ["java_result_notice", "java_post_error"],
        },
        {
          id: "java_post_error",
          text: "後処理例外：エラーログのみ<br/>ジョブ失敗へ変更しない",
          edgeType: "round",
          next: ["java_result_notice"],
        },
        {
          id: "java_failure_notice",
          text: "失敗原因とスタックトレースをSlack通知",
          edgeType: "round",
          next: ["java_result_notice"],
        },
        {
          id: "java_result_notice",
          text: "ジョブの終了結果をSlack通知・ログ記録",
          edgeType: "round",
          next: ["java_end"],
        },
        {
          id: "java_end",
          text: "Java処理終了",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>