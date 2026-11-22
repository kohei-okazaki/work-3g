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
export default {
  data: function () {
    return {
      clipped: false,
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
