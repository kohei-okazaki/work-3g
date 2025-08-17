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
      <AppTop />
      <v-spacer />
      <AppTheme />
      &nbsp;&nbsp;
      <AppLoginUserMenu />
      &nbsp;&nbsp;
      <AppLogout />
    </v-app-bar>
    <v-main :class="isDark">
      <v-container>
        <nuxt />
      </v-container>
    </v-main>

    <AppScroll />
    <AppFooter />
  </v-app>
</template>

<script>
import AppFooter from "~/components/AppFooter.vue";
import AppLogout from "~/components/AppLogout.vue";
import AppTop from "~/components/AppTop.vue";
import AppTheme from "~/components/AppTheme.vue";
import AppScroll from "~/components/AppScroll.vue";
import AppLoginUserMenu from "~/components/AppLoginUserMenu.vue";

export default {
  components: {
    AppFooter,
    AppLogout,
    AppTop,
    AppTheme,
    AppScroll,
    AppLoginUserMenu,
  },
  data: function () {
    return {
      clipped: true,
      drawer: true,
      fixed: false,
      items: [
        {
          icon: "mdi-apps",
          title: "Top",
          to: "/",
        },
        {
          icon: "mdi-account",
          title: "アカウント情報一覧",
          to: "/account",
        },
        {
          icon: "mdi-api",
          title: "API通信情報一覧",
          to: "/apidata",
        },
        {
          icon: "mdi-pill",
          title: "健康情報一覧",
          to: "/healthinfo",
        },
        {
          icon: "mdi-newspaper",
          title: "お知らせ一覧",
          to: "/news",
        },
        {
          icon: "mdi-notebook",
          title: "メモ一覧",
          to: "/note",
        },
        {
          icon: "mdi-notebook",
          title: "問い合わせ一覧",
          to: "/inquiry",
        },
      ],
      miniVariant: false,
      right: true,
    };
  },
  computed: {
    isDark: function () {
      if (this.$vuetify.theme.dark) {
        return "";
      }
      return "light-main";
    },
  },
};
</script>

<style>
.pushable {
  cursor: pointer;
}
</style> 