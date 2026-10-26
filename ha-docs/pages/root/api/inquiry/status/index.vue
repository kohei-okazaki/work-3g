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
          text: "問い合わせステータスマスタ一覧取得API",
          disabled: true,
          href: "/root/api/inquiry/status",
        },
      ],
      flow: [
        {
          id: "start",
          text: "リクエスト受付",
          edgeType: "round",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "JWTの署名と有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "list"],
        },
        {
          id: "auth_error",
          text: "認証不可<br/>処理を終了",
          edgeType: "round",
        },
        {
          id: "list",
          text: "INQUIRY_STATUS_MT全件検索<br/>WHERE条件なし<br/>ORDER BY INQUIRY_STATUS_MT.INQUIRY_STATUS ASC",
          edgeType: "round",
          next: ["format"],
        },
        {
          id: "format",
          text: "コードと表示名の一覧を作成",
          edgeType: "round",
          next: ["result"],
        },
        {
          id: "result",
          text: "取得処理の結果",
          edgeType: "diamond",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "正常終了<br/>状態一覧をHTTP 200で<br/>処理結果0のJSONとして返却",
          edgeType: "round",
        },
        {
          id: "exception",
          text: "例外発生<br/>Slack通知後にHTTP 500で<br/>処理結果1のJSONを返却",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
