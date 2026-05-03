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
  // 健康管理Dashboardのレイアウトを適用
  layout: "healthinfoappDashboardLayout",
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
          text: "健康管理ダッシュボード",
          disabled: false,
          href: "/healthinfoapp/dashboard",
        },
        {
          text: "お知らせ情報一覧",
          disabled: true,
          href: "/healthinfoapp/dashboard/news",
        },
      ],
      flow: [
        {
          id: "start",
          text: "お知らせ情報<br>一覧画面",
          edgeType: "circle",
          next: ["0"],
        },
        {
          id: "0",
          text: "設定ファイルより<br>ページング情報を取得",
          edgeType: "round",
          next: ["1"],
        },
        {
          id: "1",
          text: "NEWS_INFO 検索",
          edgeType: "round",
          next: ["2"],
          style: "fill:#c6ffc6",
        },
        {
          id: "2",
          text: "NEWS_INFO の各レコードに対し<br>S3.お知らせJSON ダウンロード",
          edgeType: "round",
          next: ["2", "3"],
        },
        {
          id: "3",
          text: "NEWS_INFO 件数取得",
          edgeType: "round",
          next: ["4"],
        },
        {
          id: "4",
          text: "お知らせ情報一覧表示",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>