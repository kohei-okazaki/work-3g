<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
    <AppTimeChart :timelines="timelines" color="green" />
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppTimeChart from "~/components/AppTimeChart.vue";

export default {
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppTimeChart,
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
          text: "健康管理バッチ",
          disabled: true,
          href: "/healthinfoapp/batch",
        },
      ],
      timelines: [
        {
          name: "ヘルスチェックバッチ",
          date: "毎日 毎時:00:00",
        },
        {
          name: "月次健康情報集計バッチ",
          date: "毎月 1日 00:00:00",
        },
        {
          name: "健康情報一括登録バッチ",
          date: "毎日 10:00:00",
        },
        {
          name: "健康情報連携バッチ",
          date: "毎日 22:00:00",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>