<template>
  <v-app>
    <v-app-bar :clipped-left="clipped" fixed app>
      <AppTop />
      <v-spacer />
      <AppTheme />
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
import AppTop from "~/components/AppTop.vue";
import AppTheme from "~/components/AppTheme.vue";
import AppScroll from "~/components/AppScroll.vue";

export default {
  components: {
    AppFooter,
    AppTop,
    AppTheme,
    AppScroll,
  },
  data: function () {
    return {
      clipped: false,
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
