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
            <v-list-item-title v-text="item.title" />
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
    <v-footer :absolute="!fixed" app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
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
          icon: "mdi-api",
          title: "NodeAPI",
          to: "/node/api",
        },
        {
          icon: "mdi-api",
          title: "管理者サイト用API",
          to: "/root/api",
        },
        {
          icon: "mdi-link",
          title: "開発用リンク",
          to: "/locallink",
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