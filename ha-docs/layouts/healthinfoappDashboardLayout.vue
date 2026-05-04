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
          title: "画面一覧",
          to: "/healthinfoapp/dashboard/list",
        },
        {
          icon: "mdi-view-dashboard",
          title: "ログイン",
          to: "/healthinfoapp/dashboard/login",
        },
        {
          icon: "mdi-view-dashboard",
          title: "TOP",
          to: "/healthinfoapp/dashboard/top",
        },
        {
          icon: "mdi-view-dashboard",
          title: "健康情報登録",
          to: "/healthinfoapp/dashboard/healthinfo/regist",
        },
        {
          icon: "mdi-view-dashboard",
          title: "健康情報照会",
          to: "/healthinfoapp/dashboard/healthinfo/refer",
        },
        {
          icon: "mdi-view-dashboard",
          title: "健康情報詳細",
          to: "/healthinfoapp/dashboard/healthinfo/detail",
        },
        {
          icon: "mdi-view-dashboard",
          title: "健康情報一括登録",
          to: "/healthinfoapp/dashboard/healthinfo/fileRegist",
        },
        {
          icon: "mdi-view-dashboard",
          title: "アカウント登録",
          to: "/healthinfoapp/dashboard/account/regist",
        },
        {
          icon: "mdi-view-dashboard",
          title: "アカウント設定",
          to: "/healthinfoapp/dashboard/account/setting",
        },
        {
          icon: "mdi-view-dashboard",
          title: "パスワード再設定",
          to: "/healthinfoapp/dashboard/account/password",
        },
        {
          icon: "mdi-view-dashboard",
          title: "カロリー計算",
          to: "/healthinfoapp/dashboard/calorie",
        },
        {
          icon: "mdi-view-dashboard",
          title: "肺活量計算",
          to: "/healthinfoapp/dashboard/breath",
        },
        {
          icon: "mdi-view-dashboard",
          title: "お知らせ情報一覧",
          to: "/healthinfoapp/dashboard/news",
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
