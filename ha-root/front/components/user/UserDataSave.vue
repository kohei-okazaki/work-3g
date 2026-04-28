<template></template>

<script>
import axios from "axios";
let retriveUrl = process.env.api_base_url + "user/";

export default {
  methods: {
    save: function (seqLoginId) {
      let headers = { Authorization: this.$store.state.auth.token };
      return axios.get(retriveUrl + seqLoginId, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            // 正常終了した場合、storeにユーザ情報を保存
            let userData = {
              roles: response.data.roles,
            };
            this.$store.commit("auth/setUserData", userData);
            return true;
          }
          console.log("retrieve [error]=" + response.data.error.message);
          return false;
        },
        (error) => {
          console.log("retrieve [error]=" + error);
          return false;
        },
      );
    },
  },
};
</script>

<style scoped></style>
