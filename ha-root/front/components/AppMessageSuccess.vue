<template>
  <v-row>
    <v-col class="text-left">
      <v-alert
        class="pushable"
        elevation="2"
        border="left"
        color="green"
        type="success"
        :outlined="isOutlined ? true : false"
        @click="clear"
        @mouseover="isOutlined = true"
        @mouseleave="isOutlined = false"
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
  data: function () {
    return {
      isOutlined: false,
    };
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