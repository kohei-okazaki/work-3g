<template>
  <v-btn @click="edit_login_user">
    <div>{{ view_login_user }}</div>
  </v-btn>
</template>

<script>
export default {
  data: function() {
    return {
      message_prefix: 'ログインID'
    }
  },
  methods: {
    edit_login_user: function () {
      this.$router.push("/user/edit/" + this.$store.state.auth.seq_login_id);
    },
  },
  computed: {
    view_login_user: function() {
      return this.message_prefix + "：" + this.$store.state.auth.seq_login_id
    },
  },
};
</script>

<style scope>
</style> 