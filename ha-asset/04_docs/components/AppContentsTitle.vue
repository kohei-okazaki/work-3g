<template>
  <v-row justify="center">
    <v-col class="text-left" cols="12" sm="8" md="10">
      <v-alert border="left" color="purple" text>{{ title }}</v-alert>
    </v-col>
  </v-row>
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
