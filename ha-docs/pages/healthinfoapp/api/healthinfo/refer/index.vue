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
  // 健康管理APIのレイアウトを適用
  layout: "healthinfoappApiLayout",
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
          text: "健康管理API",
          disabled: false,
          href: "/healthinfoapp/api",
        },
        {
          text: "健康情報照会API",
          disabled: true,
          href: "/healthinfoapp/api/healthinfo_refer",
        },
      ],
      flow: [
        {
          id: "1",
          text: "健康情報照会リクエスト受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "必須・形式チェック",
          edgeType: "diamond",
          next: ["20","3"],
        },
        {
          id: "20",
          text: "入力不正",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "3",
          text: "入力正常：USER検索<br/>USER.SEQ_USER_ID = パス.ユーザID",
          edgeType: "round",
          next: ["100","4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "4",
          text: "USERの存在と<br/>USER.API_KEY = ヘッダー.Api-Keyを確認",
          edgeType: "diamond",
          next: ["40","5"],
        },
        {
          id: "40",
          text: "ユーザなし／APIキー不一致",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "5",
          text: "認証成功：HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID = パス.健康情報ID<br/>AND HEALTH_INFO.SEQ_USER_ID = パス.ユーザID",
          edgeType: "round",
          next: ["100","6"],
          style: "fill:#c6ffc6",
        },
        {
          id: "6",
          text: "健康情報の検索結果件数",
          edgeType: "diamond",
          next: ["60","61","7"],
        },
        {
          id: "60",
          text: "0件：対象データなし",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "61",
          text: "複数件：データ重複",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "7",
          text: "1件：ユーザID・健康情報を<br/>正常レスポンスに設定",
          edgeType: "round",
          next: ["101"],
        },
        {
          id: "101",
          text: "HTTP 200 OKでJSON返却<br/>result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "100",
          text: "エラー通知",
          edgeType: "round",
          next: ["102"],
        },
        {
          id: "102",
          text: "異常レスポンス生成・ログ出力<br/>エラーコード・詳細を設定",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "110",
          text: "HTTP 400 Bad RequestでJSON返却<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
