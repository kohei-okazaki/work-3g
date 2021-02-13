<template>
  <v-row>
    <v-col class="text-left">
      <v-alert
        class="pushable"
        elevation="2"
        border="left"
        color="green"
        type="success"
        @click="clear"
        >{{ data.message }}</v-alert
      >
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    // isSuccessとmessageを持つオブジェクトを渡すこと
    data: Object,
  },
  methods: {
    clear: function () {
      this.data.isSuccess = false;
    },
  },
};
</script>

<style scoped>
.pushable {
  cursor: pointer;
}
</style>