<template>
  <v-alert border="left" color="purple" text elevation="2">{{ title }}</v-alert>
</template>
<script>
export default {
  props: {
    title: String,
  },
};
</script>

<style scoped>
</style>
