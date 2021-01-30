<template>
  <v-footer :absolute="!fixed" app>
    <span>&copy; {{ new Date().getFullYear() }}</span>
  </v-footer>
</template>

<script>
export default {
  data: function () {
    return {
      fixed: false,
    };
  },
};
</script>

<style scoped>
.base {
  padding-left: 10px;
}
</style>