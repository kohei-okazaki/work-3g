<template>
  <v-app dark>
    <v-app-bar :clipped-left="clipped" fixed app>
      <AppTop />
      <v-spacer />
      <AppTheme />
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
import AppTop from "~/components/AppTop.vue";
import AppTheme from "~/components/AppTheme.vue";
import AppScroll from "~/components/AppScroll.vue";

// ログイン前のレイアウト
export default {
  data: function () {
    return {
      clipped: false,
    };
  },
  components: {
    AppFooter,
    AppTop,
    AppTheme,
    AppScroll,
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

<style scoped>
</style> 