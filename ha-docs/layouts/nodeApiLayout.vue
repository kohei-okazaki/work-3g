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