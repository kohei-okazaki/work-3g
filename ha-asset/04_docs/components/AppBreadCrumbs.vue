<template>
  <v-breadcrumbs large divider="/" :items="items"></v-breadcrumbs>
</template>
<script>
export default {
  props: {
    items: Array,
  },
};
</script>

<style scoped>
</style>
