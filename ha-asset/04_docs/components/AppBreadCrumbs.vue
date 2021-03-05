<template>
  <v-row>
    <v-col class="text-center" cols="12" sm="8" md="10">
      <v-breadcrumbs large divider="/" :items="items"></v-breadcrumbs>
    </v-col>
  </v-row>
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
