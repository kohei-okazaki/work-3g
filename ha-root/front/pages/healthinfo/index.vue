<template>
  <div>
    <AppTitle icon="mdi-pill" title="健康情報一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row>
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索条件"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table
          :headers="headers"
          :items="health_info_list"
          :search="search"
        ></v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "healthinfo";

export default {
  components: {
    AppTitle,
    AppMessageError,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      search: "",
      health_info_list: [],
      headers: [
        {
          text: "健康情報ID",
          value: "seq_health_info_id",
        },
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "身長(cm)",
          value: "height",
        },
        {
          text: "体重(kg)",
          value: "weight",
        },
        {
          text: "BMI",
          value: "bmi",
        },
        {
          text: "標準体重(kg)",
          value: "standard_weight",
        },
        {
          text: "前回と比較",
          value: "health_info_status.message",
        },
        {
          text: "肥満度",
          value: "bmi_status.message",
        },
        {
          text: "健康情報登録日時",
          value: "health_info_reg_date",
        },
      ],
    };
  },
  created: function () {
    // 保存済のAPIトークンを取得
    let token = this.$store.state.auth.token;

    axios
      .get(url, {
        headers: { Authorization: token },
      })
      .then(
        (response) => {
          this.health_info_list = response.data.health_info_list;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("healthinfo [error]=" + error);
          return error;
        }
      );
  },
};
</script>

<style scoped>
</style>