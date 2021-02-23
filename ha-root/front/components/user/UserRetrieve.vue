<template></template>

<script>
const axios = require("axios");
let retriveUrl = process.env.api_base_url + "user/";

export default {
  props: {
    // RetrieveAPI対象のログインID
    seqLoginId: String,
  },
  data: function () {
    return {
      apiResult: {
        result: false,
        message: null,
        seqLoginId: null,
        deleteFlag: false,
        passwordExpire: null,
        remarks: null,
      },
    };
  },
  mounted: function () {
    this.retrieve(this.seqLoginId);
    this.$emit("setRetrieveApiResult", this.apiResult);
  },
  methods: {
    retrieve: function (seqLoginId) {
      let headers = { Authorization: this.$store.state.auth.token };
      axios.get(retriveUrl + seqLoginId, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.apiResult.result = true;
            this.apiResult.seqLoginId = response.data.seq_login_id.toString();
            this.apiResult.deleteFlag = response.data.delete_flag == "1";
            this.apiResult.passwordExpire = response.data.password_expire;
            this.apiResult.remarks = response.data.remarks;
          } else {
            this.apiResult.result = false;
            this.apiResult.message = response.data.error.message;
          }
        },
        (error) => {
          this.apiResult.result = false;
          this.apiResult.message = error.toString();
          console.log("retrieve [error]=" + error);
        }
      );
    },
  },
};
</script>

<style scoped>
</style>