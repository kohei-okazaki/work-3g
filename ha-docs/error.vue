<script setup>
const props = defineProps({
  error: {
    type: Object,
    default: null,
  },
});

const pageNotFound = "Page not found";
const otherError = "An error occurred";

useHead({
  title: props.error?.statusCode === 404 ? pageNotFound : otherError,
});
</script>

<template>
  <v-app>
    <v-main>
      <v-container class="py-8">
        <h1 v-if="error?.statusCode === 404">
          {{ pageNotFound }}
        </h1>
        <h1 v-else>
          {{ otherError }}
        </h1>
        <NuxtLink to="/">Back to top</NuxtLink>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>
h1 {
  font-size: 20px;
}
</style>
