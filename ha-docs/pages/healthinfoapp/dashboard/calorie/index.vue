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
          text: "カロリー計算",
          disabled: true,
          href: "/healthinfoapp/dashboard/calorie",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "計算前画面",
          edgeType: "circle",
          next: ["a1", "b1"],
        },
        {
          id: "a1",
          text: "画面表示",
          edgeType: "round",
          next: [""],
        },
        {
          id: "b1",
          text: "画面表示",
          edgeType: "round",
          next: [""],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>