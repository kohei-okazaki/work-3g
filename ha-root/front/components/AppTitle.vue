<template>
  <v-row>
    <v-col class="text-center">
      <h1><v-icon class="icon" size="35">{{ icon }}</v-icon>{{ title }}</h1>
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
  padding-right: 10px;
  padding-bottom: 7px;
}
</style>