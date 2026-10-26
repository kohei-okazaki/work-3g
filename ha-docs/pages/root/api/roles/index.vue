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
          text: "権限マスタ一覧取得API",
          disabled: true,
          href: "/root/api/roles",
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
          next: ["auth_error", "cache"],
        },
        {
          id: "auth_error",
          text: "認証不可<br/>処理を終了",
          edgeType: "round",
        },
        {
          id: "cache",
          text: "認証成功<br/>有効な権限情報のキャッシュはあるか",
          edgeType: "diamond",
          next: ["cached", "load"],
        },
        {
          id: "cached",
          text: "有効な情報あり<br/>キャッシュの権限情報を取得",
          edgeType: "round",
          next: ["format"],
        },
        {
          id: "load",
          text: "キャッシュ未取得・期限切れ時<br/>ROOT_ROLE_MT全件検索してキャッシュ保存<br/>WHERE・ORDER BY指定なし",
          edgeType: "round",
          next: ["format"],
        },
        {
          id: "format",
          text: "権限コードと名称の一覧を作成",
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
          text: "正常終了<br/>権限一覧をHTTP 200で<br/>処理結果0のJSONとして返却",
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
