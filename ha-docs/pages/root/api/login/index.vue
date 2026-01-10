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
          text: "ログインAPI",
          disabled: true,
          href: "/root/api/login",
        },
      ],
      flow: [
        {
          id: "1",
          text: "リクエスト受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "ログインIDチェック",
          edgeType: "round",
          link: [
            "-- ログインIDが未指定<br>またはログインIDが半角数字以外の場合 -->",
            "-- それ以外の場合 -->",
          ],
          next: ["404", "3"],
        },
        {
          id: "3",
          text: "ROOT_LOGIN_INFO 検索",
          edgeType: "round",
          link: ["-- 検索結果 == 0 -->", "-- それ以外の場合 -->"],
          next: ["404", "4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "4",
          text: "トークン情報 生成",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "トークン情報を<br>レスポンスヘッダに設定",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "レスポンス返却",
          edgeType: "round",
        },
        {
          id: "404",
          text: "404エラー",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>