<template>
  <v-row>
    <v-col class="text-left">
      <v-alert border="left" color="red" type="error">{{ message }}</v-alert>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    message: String,
  },
};
</script>

<style scoped>
</style>