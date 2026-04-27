<script setup>
import { computed, useAttrs } from "vue";

defineOptions({
  inheritAttrs: false,
});

const attrs = useAttrs();

const forwardedAttrs = computed(() => {
  const { dense, ...rest } = attrs;

  return {
    ...rest,
    density: dense !== undefined && dense !== false ? "compact" : rest.density,
  };
});
</script>

<template>
  <v-table v-bind="forwardedAttrs">
    <slot />
  </v-table>
</template>
