<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <br />
    <v-row justify="center" align="center">
      <v-col cols="6" sm="8" md="6">
        <HealthInfoRegByMonthly :error="error" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppMessageError from "~/components/AppMessageError.vue";
import HealthInfoRegByMonthly from "~/components/top/HealthInfoRegByMonthly.vue";

export default {
  components: {
    AppMessageError,
    HealthInfoRegByMonthly,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
    };
  },
};
</script>

<style scoped>
</style>