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
          id: "0",
          text: "設定ファイルより<br>ページング情報を取得",
          edgeType: "round",
          next: ["1"],
        },
        {
          id: "1",
          text: "お知らせ情報検索",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "お知らせ情報検索",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "お知らせ情報の各レコードに対し<br>AWS-S3へJSONファイルを取得",
          edgeType: "round",
          next: ["3", "4"],
        },
        {
          id: "4",
          text: "お知らせ情報件数取得",
          edgeType: "round",
          next: ["5"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>