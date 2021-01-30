<template>
  <v-row>
    <v-col class="text-center">
      <h1><v-icon class="icon">{{ icon }}</v-icon>{{ title }}</h1>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    icon: String,
    title: String,
  },
};
</script>

<style scoped>
.icon {
  margin: 0;
  padding: 0;
}
</style>