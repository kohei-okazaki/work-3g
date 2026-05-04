<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" :rail="miniVariant" order="1">
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          :prepend-icon="item.icon"
          router
          exact
        >
          <v-list-item-title class="text-subtitle-2" v-text="item.title" />
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar>
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
        <slot />
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
      drawer: true,
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
          title: "API通信ログ一覧",
          to: "/apidata",
        },
        {
          icon: "mdi-bash",
          title: "バッチ実行ログ一覧",
          to: "/job",
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
      if (this.$isDarkTheme()) {
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
