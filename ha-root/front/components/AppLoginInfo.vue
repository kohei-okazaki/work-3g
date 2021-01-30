<template>
  <v-btn @click="editLoginUser">
    <div>{{ viewLoginUser }}</div>
  </v-btn>
</template>

<script>
export default {
  data: function() {
    return {
      messagePrefix: 'ログインID'
    }
  },
  methods: {
    editLoginUser: function () {
      this.$router.push("/user/edit/" + this.$store.state.auth.seq_login_id);
    },
  },
  computed: {
    viewLoginUser: function() {
      return this.messagePrefix + "：" + this.$store.state.auth.seq_login_id
    },
  },
};
</script>

<style scoped>
</style> 