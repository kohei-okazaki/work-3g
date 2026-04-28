<template>
  <v-overlay
    :model-value="loading"
    :absolute="absolute"
    class="align-center justify-center"
  >
    <v-progress-circular
      indeterminate
      size="100"
      width="7"
      color="info"
    ></v-progress-circular>
  </v-overlay>
</template>

<script>
export default {
  props: {
    loading: Boolean,
  },
  data: function () {
    return {
      absolute: false,
    };
  },
};
</script>

<style scoped></style>
