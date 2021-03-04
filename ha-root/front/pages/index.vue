<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <br />
    <v-row justify="center" align="center">
      <v-col cols="6" sm="8" md="6">
        <AccountRegByMonthly
          :error="error"
          :labels="accountLabels"
          :values="accountValues"
          title="ユーザ登録者数"
          text="下記年月に登録した全ユーザ数"
          color="teal"
          @get-graph="getAccountGraph"
        />
      </v-col>
      <v-col cols="6" sm="8" md="6">
        <HealthInfoRegByMonthly
          :error="error"
          :labels="healthInfoLabels"
          :values="healthInfoValues"
          title="健康情報登録者数"
          text="下記年月に登録した全ユーザの健康情報登録情報数"
          color="cyan"
          @get-graph="getHealthInfoGraph"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppMessageError from "~/components/AppMessageError.vue";
import HealthInfoRegByMonthly from "~/components/top/MonthlyGraph.vue";
import AccountRegByMonthly from "~/components/top/MonthlyGraph.vue";

const axios = require("axios");
let url = process.env.api_base_url + "top";

export default {
  components: {
    AppMessageError,
    HealthInfoRegByMonthly,
    AccountRegByMonthly,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      healthInfoLabels: [],
      healthInfoValues: [],
      accountLabels: [],
      accountValues: [],
    };
  },
  methods: {
    getGraph: function (d) {
      let param = d == null ? "" : "?date=" + d;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(url + param, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.healthInfoLabels = response.data.health_info_reg_graph_list.map(
              (item) => item.date
            );
            this.healthInfoValues = response.data.health_info_reg_graph_list.map(
              (item) => item.count
            );
            this.accountLabels = response.data.account_reg_graph_list.map(
              (item) => item.date
            );
            this.accountValues = response.data.account_reg_graph_list.map(
              (item) => item.count
            );
          } else {
            console.log(response.data.error.message);
            this.error.hasError = true;
            this.error.message = response.data.error.message;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
    getHealthInfoGraph: function (d) {
      let param = d == null ? "" : "?date=" + d;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(url + "/healthinfo" + param, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.healthInfoLabels = response.data.health_info_reg_graph_list.map(
              (item) => item.date
            );
            this.healthInfoValues = response.data.health_info_reg_graph_list.map(
              (item) => item.count
            );
          } else {
            console.log(response.data.error.message);
            this.error.hasError = true;
            this.error.message = response.data.error.message;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
    getAccountGraph: function (d) {
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let param = d == null ? "" : "?date=" + d;
      axios.get(url + "/account" + param, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.accountLabels = response.data.account_reg_graph_list.map(
              (item) => item.date
            );
            this.accountValues = response.data.account_reg_graph_list.map(
              (item) => item.count
            );
          } else {
            console.log(response.data.error.message);
            this.error.hasError = true;
            this.error.message = response.data.error.message;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
  },
  mounted: function () {
    this.getGraph();
  },
};
</script>

<style scoped>
</style>