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
    toLoginView: function (isLogin) {
      if (isLogin) {
        // ログイン状態の場合
        this.$router.push("/");
      } else {
        // 未ログインの場合
        this.$router.push("/login");
      }
    },
  },
};
</script>

<style scope>
.app-title {
  padding: 10px;
}
.app-title:hover {
  cursor: pointer;
}
</style> 