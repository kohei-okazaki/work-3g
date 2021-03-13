<template>
  <div>
    <v-card>
      <v-card-text class="text-subtitle-1">処理フロー</v-card-text>
      <v-card-text>
        <vue-mermaid :nodes="flow" type="graph TB"></vue-mermaid
      ></v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    flow: Array,
  },
};
</script>

<style scoped>
</style>