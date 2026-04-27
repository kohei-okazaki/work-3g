<script setup>
import { computed, useAttrs } from "vue";

defineOptions({
  inheritAttrs: false,
});

const attrs = useAttrs();

const forwardedAttrs = computed(() => {
  const {
    disableIconRotate,
    "disable-icon-rotate": _disableIconRotate,
    ...rest
  } = attrs;

  return rest;
});
</script>

<template>
  <v-expansion-panel-title v-bind="forwardedAttrs">
    <slot />
    <template v-if="$slots.actions" #actions>
      <slot name="actions" />
    </template>
  </v-expansion-panel-title>
</template>
