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
          text: "問い合わせ情報通知API",
          disabled: true,
          href: "/root/api/inquiry/notice",
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
          next: ["auth_error", "status"],
        },
        {
          id: "auth_error",
          text: "認証不可<br/>処理を終了",
          edgeType: "round",
        },
        {
          id: "status",
          text: "認証成功<br/>指定ステータスは有効か",
          edgeType: "diamond",
          next: ["specified", "default"],
        },
        {
          id: "specified",
          text: "有効：クエリ.statusを集計する状態に使用",
          edgeType: "round",
          next: ["count"],
        },
        {
          id: "default",
          text: "未指定または不正：状態#quot;00#quot;を使用",
          edgeType: "round",
          next: ["count"],
        },
        {
          id: "count",
          text: "INQUIRY_MANAGEMENTの該当件数を集計<br/>INQUIRY_MANAGEMENT.INQUIRY_STATUS IN 選択した状態<br/>AND INQUIRY_MANAGEMENT.DELETE_FLAG = false",
          edgeType: "round",
          next: ["result"],
        },
        {
          id: "result",
          text: "集計処理の結果",
          edgeType: "diamond",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "正常終了<br/>件数をHTTP 200で<br/>処理結果0のJSONとして返却",
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
