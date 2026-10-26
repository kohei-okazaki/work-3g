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
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
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
          text: "管理者サイト用API",
          disabled: false,
          href: "/root/api",
        },
        {
          text: "健康情報一覧取得API",
          disabled: true,
          href: "/root/api/healthinfo",
        },
      ],
      flow: [
        {
          id: "start",
          text: "リクエスト受付",
          edgeType: "circle",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "認証トークンの署名・有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "validate"],
        },
        {
          id: "auth_error",
          text: "認証不可：処理終了",
          edgeType: "circle",
        },
        {
          id: "validate",
          text: "認証成功：ページ番号を検証<br/>省略時は先頭ページ",
          edgeType: "diamond",
          next: ["bad_request", "paging"],
        },
        {
          id: "bad_request",
          text: "入力不正：Slack通知・ログ出力<br/>HTTP 400／result: #quot;1#quot;",
          edgeType: "circle",
        },
        {
          id: "paging",
          text: "入力正常：指定ページと設定件数から<br/>取得範囲を決定",
          edgeType: "round",
          next: ["search"],
        },
        {
          id: "search",
          text: "HEALTH_INFO検索／BMI_RANGE_MTをLEFT JOIN<br/>HEALTH_INFO.SEQ_BMI_RANGE_MT_ID =<br/>BMI_RANGE_MT.SEQ_BMI_RANGE_MT_ID<br/>WHERE条件なし<br/>ORDER BY HEALTH_INFO.SEQ_HEALTH_INFO_ID DESC<br/>指定ページの開始位置・設定件数で取得",
          edgeType: "round",
          next: ["convert", "exception"],
        },
        {
          id: "convert",
          text: "BMIの肥満度コードを<br/>日本語の判定メッセージへ変換",
          edgeType: "round",
          next: ["count"],
        },
        {
          id: "count",
          text: "HEALTH_INFOの全件数を取得してページ情報を作成<br/>WHERE条件なし",
          edgeType: "round",
          next: ["response"],
        },
        {
          id: "response",
          text: "一覧とページ情報を設定<br/>0件も空の一覧で正常応答",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "HTTP 200でJSON返却<br/>result: #quot;0#quot;",
          edgeType: "circle",
        },
        {
          id: "exception",
          text: "検索・変換・件数取得などで例外<br/>Slack通知・ログ出力",
          edgeType: "round",
          next: ["server_error"],
        },
        {
          id: "server_error",
          text: "HTTP 500でエラーJSON返却<br/>result: #quot;1#quot;",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
