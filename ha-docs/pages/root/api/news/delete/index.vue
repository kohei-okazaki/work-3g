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
          text: "お知らせ情報削除API",
          disabled: true,
          href: "/root/api/news/delete",
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
          text: "リクエスト.お知らせ情報IDチェック",
          edgeType: "round",
          link: ["-- 未指定の場合 -->", "-- それ以外の場合 -->"],
          next: ["101", "4"],
        },
        {
          id: "4",
          text: "お知らせ情報JSON 取得",
          edgeType: "round",
          link: ["-- 取得失敗 -->", "-- 取得成功 -->"],
          next: ["101", "5"],
        },
        {
          id: "5",
          text:
            "お知らせ情報JSONから<br>指定したおしらせ情報IDのレコードを削除",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "お知らせ情報JSON<br>Upload",
          edgeType: "round",
          link: ["-- Upload失敗 -->", "-- Upload成功 -->"],
          next: ["101", "7"],
        },
        {
          id: "7",
          text: "Slack通知",
          edgeType: "round",
          link: ["-- 通知失敗 -->", "-- 通知成功 -->"],
          next: ["101", "100"],
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