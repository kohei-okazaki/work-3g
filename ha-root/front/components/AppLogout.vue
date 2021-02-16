<template>
  <v-btn @click="logout" text>
    <v-icon>mdi-account-cancel</v-icon>
    <div>{{ message }}</div>
  </v-btn>
</template>

<script>
export default {
  data: function () {
    return {
      message: "ログアウト",
    };
  },
  methods: {
    logout: function () {
      this.$auth.logout();
    },
  },
};
</script>

<style scoped>
</style>