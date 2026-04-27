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
          icon: "mdi-apps",
          title: "プロジェクト構成",
          to: "/wiki/project",
        },
        {
          icon: "mdi-apps",
          title: "ライブラリ一覧",
          to: "/wiki/libraries",
        },
        {
          icon: "mdi-apps",
          title: "エラーコード一覧",
          to: "/wiki/errorCode",
        },
        {
          icon: "mdi-apps",
          title: "Docker環境構築手順",
          to: "/wiki/dockerEnv",
        },
        {
          icon: "mdi-apps",
          title: "ローカル環境構築手順",
          to: "/wiki/localEnv",
        },
        {
          icon: "mdi-apps",
          title: "ローカル環境ビルド手順",
          to: "/wiki/localBuild",
        },
        {
          icon: "mdi-apps",
          title: "DB変更手順",
          to: "/wiki/dbMod",
        },
        {
          icon: "mdi-apps",
          title: "接続先一覧",
          to: "/wiki/dest",
        },
        {
          icon: "mdi-apps",
          title: "健康情報計算方法",
          to: "/wiki/healthinfoCalc",
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
