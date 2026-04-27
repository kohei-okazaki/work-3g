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
      fixed: true,
      items: [
        {
          icon: "mdi-apps",
          title: "TOPページ",
          to: "/",
        },
        {
          icon: "mdi-format-list-bulleted",
          title: "IF一覧",
          to: "/node/api/interface",
        },
        {
          icon: "mdi-api",
          title: "ヘルスチェックAPI",
          to: "/node/api/healthcheck",
        },
        {
          icon: "mdi-api",
          title: "トークン発行API",
          to: "/node/api/token",
        },
        {
          icon: "mdi-api",
          title: "基礎健康情報計算API",
          to: "/node/api/basic",
        },
        {
          icon: "mdi-api",
          title: "カロリー計算API",
          to: "/node/api/calorie",
        },
        {
          icon: "mdi-api",
          title: "肺活量計算API",
          to: "/node/api/breath_capacity",
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
