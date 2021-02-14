<template>
  <v-row>
    <v-col class="text-left">
      <v-alert
        class="pushable"
        elevation="2"
        border="left"
        color="red"
        type="error"
        @click="clear"
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
  methods: {
    clear: function () {
      this.data.hasError = false;
    },
  },
};
</script>

<style scoped>
.pushable {
  cursor: pointer;
}
</style>