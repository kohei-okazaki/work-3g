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
          to: "/healthinfoapp/api/interface",
        },
        {
          icon: "mdi-api",
          title: "健康情報登録API",
          to: "/healthinfoapp/api/healthinfo/regist",
        },
        {
          icon: "mdi-api",
          title: "健康情報照会API",
          to: "/healthinfoapp/api/healthinfo/refer",
        },
        {
          icon: "mdi-api",
          title: "ヘルスチェックAPI",
          to: "/healthinfoapp/api/healthcheck",
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
