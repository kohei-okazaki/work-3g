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
          text: "ヘルスチェックバッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthcheck",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>開始ログを出力",
          edgeType: "circle",
          next: ["sh_prepare"],
        },
        {
          id: "sh_prepare",
          text: "共通設定を読み込み<br/>実行識別子を生成",
          edgeType: "round",
          next: ["sh_lock"],
        },
        {
          id: "sh_lock",
          text: "二重起動防止のロックを取得できたか",
          edgeType: "diamond",
          next: ["sh_busy", "sh_run"],
        },
        {
          id: "sh_busy",
          text: "取得不可<br/>終了コード50で終了",
          edgeType: "round",
        },
        {
          id: "sh_run",
          text: "取得成功<br/>ヘルスチェックバッチをコンテナで実行",
          edgeType: "round",
          next: ["sh_exit_code"],
        },
        {
          id: "sh_exit_code",
          text: "コンテナ実行の終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_end"],
        },
        {
          id: "sh_error",
          text: "非0<br/>その時点で異常終了",
          edgeType: "round",
        },
        {
          id: "sh_end",
          text: "0<br/>終了ログを出力してシェル終了",
          edgeType: "round",
        },
        {
          id: "java_start",
          text: "Java処理<br/>ジョブ開始ログを出力",
          edgeType: "round",
          next: ["java_app"],
        },
        {
          id: "java_app",
          text: "健康管理APIの稼働確認を実行",
          edgeType: "round",
          next: ["java_app_log"],
        },
        {
          id: "java_app_log",
          text: "通信ログをキューへ送信",
          edgeType: "round",
          next: ["java_app_result"],
        },
        {
          id: "java_app_result",
          text: "応答結果と通知内容を保存",
          edgeType: "round",
          next: ["java_app_notify"],
        },
        {
          id: "java_app_notify",
          text: "結果通知の処理へ遷移",
          edgeType: "round",
          next: ["java_response"],
        },
        {
          id: "java_response",
          text: "保存した応答は成功か",
          edgeType: "diamond",
          next: ["java_up", "java_down_mail"],
        },
        {
          id: "java_up",
          text: "成功<br/>稼働中をSlack通知",
          edgeType: "round",
          next: ["java_root"],
        },
        {
          id: "java_down_mail",
          text: "失敗<br/>障害メールを送信",
          edgeType: "round",
          next: ["java_down_slack"],
        },
        {
          id: "java_down_slack",
          text: "停止をSlack通知",
          edgeType: "round",
          next: ["java_root"],
        },
        {
          id: "java_root",
          text: "管理者用APIの稼働確認を実行",
          edgeType: "round",
          next: ["java_root_log"],
        },
        {
          id: "java_root_log",
          text: "通信ログをキューへ送信",
          edgeType: "round",
          next: ["java_root_result"],
        },
        {
          id: "java_root_result",
          text: "応答結果と通知内容を保存",
          edgeType: "round",
          next: ["java_root_notify"],
        },
        {
          id: "java_root_notify",
          text: "共通の結果通知処理へ遷移",
          edgeType: "round",
          next: ["java_status"],
        },
        {
          id: "java_status",
          text: "終了時のジョブ状態は失敗か",
          edgeType: "diamond",
          next: ["java_failed", "java_finished"],
        },
        {
          id: "java_failed",
          text: "失敗<br/>エラー概要と詳細をSlack通知",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_finished",
          text: "失敗以外",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_notify",
          text: "ジョブ結果をSlack通知し<br/>終了ログを出力",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>