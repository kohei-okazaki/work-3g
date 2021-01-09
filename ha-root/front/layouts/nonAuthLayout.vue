<template>
  <v-app dark>
    <v-app-bar :clipped-left="clipped" fixed app>
      <v-toolbar-title v-text="title" />
    </v-app-bar>
    <v-main>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <AppFooter />
  </v-app>
</template>

<script>
import AppFooter from '~/components/Footer.vue'

// ログイン前のレイアウト
export default {
  data: function () {
    return {
      title: "Root App",
      clipped: false,
    };
  },
  components: {
    AppFooter,
  }
};
</script>

<style>
</style> 