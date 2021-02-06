<template>
  <div>
    <AppTitle icon="mdi-api" title="API通信情報一覧" />
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
          :items="api_data_list"
          :search="search"
        >
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.http_status`]="{ item }">
            <v-chip
              :color="getHttpStatusColor(item.http_status)"
              v-if="item.http_status != null"
            >
              {{ item.http_status }}
            </v-chip>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";

const axios = require("axios");
let url = process.env.api_base_url + "apidata";

export default {
  components: {
    AppTitle,
  },
  data: function () {
    return {
      search: "",
      api_data_list: [],
      headers: [
        {
          text: "API通信情報ID",
          value: "seq_api_communication_data_id",
        },
        {
          text: "API名",
          value: "api_name",
        },
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "HTTPステータス",
          value: "http_status",
        },
        {
          text: "処理結果",
          value: "result",
        },
        {
          text: "詳細",
          value: "detail",
        },
        {
          text: "APIリクエスト日時",
          value: "request_date",
        },
        {
          text: "APIレスポンス日時",
          value: "response_date",
        },
      ],
    };
  },
  created: function () {
    axios
      .get(url, {
        headers: { Authorization: this.$store.state.auth.token },
      })
      .then(
        (response) => {
          this.api_data_list = response.data.api_data_list;
        },
        (error) => {
          console.log("[error]=" + error);
          return error;
        }
      );
  },
  methods: {
    getHttpStatusColor: function (http_status) {
      if (http_status == 200) {
        return "green";
      } else if (400 <= http_status && http_status < 500) {
        return "yellow";
      } else if (500 <= http_status && http_status < 600) {
        return "red";
      }
    },
  },
};
</script>

<style>
.base {
  padding-left: 10px;
}
</style>