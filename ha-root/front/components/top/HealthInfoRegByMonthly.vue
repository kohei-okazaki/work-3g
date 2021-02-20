<template>
  <v-card class="mt-4 mx-auto" max-width="400">
    <v-sheet
      class="v-sheet--offset mx-auto"
      color="info"
      elevation="12"
      max-width="calc(100% - 32px)"
    >
      <v-sparkline
        :labels="healthInfoMonthlyLabels"
        :value="healthInfoMonthlyValues"
        color="white"
        line-width="2"
        padding="16"
        smooth
      >
        <template v-slot:label="item">
          {{
            new Date(item.value).getMonth() +
            1 +
            "/" +
            new Date(item.value).getDate()
          }}
        </template>
      </v-sparkline>
    </v-sheet>

    <v-card-text class="pt-0">
      <div class="title font-weight-light mb-2">今月の登録件数</div>
      <div class="subheading font-weight-light grey--text">
        今月の全ユーザの健康情報登録情報の登録数
      </div>
      <v-divider class="my-2"></v-divider>
      <div class="pushable" @click="getGraph">
        <v-icon class="mr-2" small>mdi-reload</v-icon>
        <span class="caption grey--text font-weight-light"
          >最新の登録情報を取得</span
        >
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
const axios = require("axios");
let url = process.env.api_base_url + "top";

export default {
  props: {
    error: Object,
  },
  data: function () {
    return { healthInfoMonthlyLabels: [], healthInfoMonthlyValues: [] };
  },
  methods: {
    getGraph: function () {
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(url, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.healthInfoMonthlyLabels = response.data.health_info_reg_graph_list.map(
              (item) => item.date
            );
            this.healthInfoMonthlyValues = response.data.health_info_reg_graph_list.map(
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
.v-sheet--offset {
  top: -24px;
  position: relative;
}
</style>