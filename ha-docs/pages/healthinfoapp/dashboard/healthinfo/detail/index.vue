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
          text: "健康情報詳細",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/detail",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "詳細リンク押下",
          edgeType: "round",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "入力チェック",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "健康情報 + BMI範囲マスタ検索",
          edgeType: "round",
          next: ["a4"],
          style: "fill:#c6ffc6",
        },

        {
          id: "a1.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a1.2"],
        },
        {
          id: "a1.2",
          text: "数値チェック",
          edgeType: "round",
          group: "入力チェック",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>