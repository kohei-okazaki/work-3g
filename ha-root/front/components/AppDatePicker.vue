<script setup>
import { VDatePicker } from "vuetify/components";
import { formatDateInput } from "~/utils/dateInput.mjs";

defineProps({
  modelValue: {
    type: [String, Date],
    default: "",
  },
});

const emit = defineEmits(["update:modelValue"]);
</script>

<template>
  <VDatePicker
    :model-value="modelValue || null"
    @update:model-value="emit('update:modelValue', formatDateInput($event))"
  />
</template>
