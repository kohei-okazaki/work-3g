<template>
  <AppNavigationLayout :items="items" :title="title">
    <slot />
  </AppNavigationLayout>
</template>

<script>
import AppScroll from "~/components/AppScroll.vue";

export default {
  components: {
    AppScroll,
  },
  data: function () {
    return {
      clipped: true,
      drawer: true,
      fixed: false,
      items: [
        {
          icon: "mdi-apps",
          title: "TOPページ",
          to: "/",
        },
        {
          icon: "mdi-api",
          title: "健康管理API",
          to: "/healthinfoapp/api",
        },
        {
          icon: "mdi-view-dashboard",
          title: "健康管理Dashboard",
          to: "/healthinfoapp/dashboard",
        },
        {
          icon: "mdi-server",
          title: "健康管理バッチ",
          to: "/healthinfoapp/batch",
        },
        {
          icon: "mdi-api",
          title: "健康情報計算API",
          to: "/calc_api",
        },
        {
          icon: "mdi-api",
          title: "管理者サイト用API",
          to: "/root/api",
        },
        {
          icon: "mdi-link",
          title: "開発用リンク",
          to: "/developlink",
        },
        {
          icon: "mdi-database",
          title: "DB",
          to: "/db",
        },
        {
          icon: "mdi-message-processing",
          title: "Wiki",
          to: "/wiki",
        },
      ],
      miniVariant: false,
      right: true,
      title: "健康管理ドキュメント",
    };
  },
};
</script>

<style>
</style>
