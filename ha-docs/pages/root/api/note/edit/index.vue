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
          text: "メモ情報編集API",
          disabled: true,
          href: "/root/api/note/edit",
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
          text: "トークン認証",
          edgeType: "round",
          link: ["-- 認証エラー -->", "-- それ以外の場合 -->"],
          next: ["404", "3"],
        },
        {
          id: "3",
          text: "管理者サイトユーザメモ情報ID チェック",
          edgeType: "round",
          link: ["-- 未指定 -->", "-- 指定ありの場合 -->"],
          next: ["101", "4"],
        },
        {
          id: "4",
          text: "管理者サイトユーザメモ情報 更新",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "管理者サイトユーザメモ情報 取得",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "メモ情報 更新",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "100",
          text: "正常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "101",
          text: "異常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "999",
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