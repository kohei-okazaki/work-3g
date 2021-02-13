<template></template>

<script>
const axios = require("axios");
let retriveUrl = process.env.api_base_url + "user/";

export default {
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
    };
  },
  methods: {
    retrieve: function (seqLoginId) {
      console.log("★★★retrieve★★★");
      let headers = { Authorization: this.$store.state.auth.token };
      let apiResult = {
        result: false,
        message: null,
      };
      axios.get(retriveUrl + seqLoginId, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            // 正常終了した場合
            // storeにユーザ情報を保存
            let userData = {
              roles: response.data.roles,
            };
            this.$store.commit("auth/setUserData", userData);
            apiResult.result = true;
          } else {
            apiResult.result = false;
            this.error.message = response.data.error.message;
          }
        },
        (error) => {
          apiResult.result = false;
          apiResult.message = error;
          console.log("retrieve [error]=" + error);
        }
      );
      return apiResult;
    },
  },
};
</script>

<style scoped>
</style>