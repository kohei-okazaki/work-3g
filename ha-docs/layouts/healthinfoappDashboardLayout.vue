<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title class="text-subtitle-2" v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar :clipped-left="clipped" fixed app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon>mdi-{{ `chevron-${miniVariant ? "right" : "left"}` }}</v-icon>
      </v-btn>
      <v-toolbar-title v-text="title" />
      <v-spacer />
    </v-app-bar>
    <v-main class="base">
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <AppScroll />
    <v-footer :absolute="fixed" app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
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