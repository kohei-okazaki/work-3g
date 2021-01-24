<template>
  <v-app dark>
    <v-app-bar :clipped-left="clipped" fixed app>
      <AppTop />
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
import AppTop from '~/components/Top.vue'

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
  },
};
</script>

<style scope>
</style> 