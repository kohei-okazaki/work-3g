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
          icon: "mdi-database",
          title: "テーブル一覧",
          to: "/db/tables",
        },
        {
          icon: "mdi-database",
          title: "カラム追加",
          to: "/db/addColumn",
        },
        {
          icon: "mdi-database",
          title: "カラム変更",
          to: "/db/modColumn",
        },
        {
          icon: "mdi-database",
          title: "カラム削除",
          to: "/db/dropColumn",
        },
        {
          icon: "mdi-database",
          title: "検索",
          to: "/db/select",
        },
        {
          icon: "mdi-database",
          title: "更新",
          to: "/db/update",
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
