<template>
  <v-btn color="primary" text @click="editLoginUser">
    <div>変更</div>
  </v-btn>
</template>

<script>
export default {
  methods: {
    editLoginUser: function () {
      this.$router.push("/user/edit/" + this.$store.state.auth.seq_login_id);
    },
  },
};
</script>

<style scoped>
</style> 