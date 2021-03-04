<template>
  <v-toolbar-title class="app-title" v-text="title" @click="toLoginView" />
</template>

<script>
export default {
  data: function () {
    return {
      title: "管理者サイト",
    };
  },
  methods: {
    toLoginView: function () {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.app-title {
  padding: 10px;
}
.app-title:hover {
  cursor: pointer;
}
</style> 