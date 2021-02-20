<template>
  <v-row>
    <v-col class="text-left">
      <v-alert
        class="pushable"
        elevation="2"
        border="left"
        color="red"
        type="error"
        :outlined="isOutlined ? true : false"
        @click="clear"
        @mouseover="isOutlined = true"
        @mouseleave="isOutlined = false"
        transition="scale-transition"
        >{{ data.message }}</v-alert
      >
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    // hasErrorとmessageを持つオブジェクトを渡すこと
    data: Object,
  },
  data: function () {
    return {
      isOutlined: false,
    };
  },
  methods: {
    clear: function () {
      this.data.hasError = false;
    },
  },
};
</script>

<style scoped>

</style>