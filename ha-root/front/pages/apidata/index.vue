<template>
  <div>
    <v-row>
      <v-col class="text-center">
        <h1>API通信情報一覧 index</h1>
      </v-col>
    </v-row>
    <v-row>
     <v-col>
        <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>
        <v-data-table :headers="headers" :items="api_data_list" :search="search"></v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
const axios = require("axios");
let url = process.env.api_base_url + "apidata";

export default{

  data: function() {
    return {
      search: '',
      api_data_list: [],
      headers: [
        {
          text: 'API通信情報ID',
          value: 'seq_api_communication_data_id'
        },
        {
          text: 'API名',
          value: 'api_name'
        },
        {
          text: 'ユーザID',
          value: 'seq_user_id'
        },
        {
          text: 'HTTPステータス',
          value: 'http_status'
        },
        {
          text: '処理結果',
          value: 'result'
        },
        {
          text: '詳細',
          value: 'detail'
        },
        {
          text: 'APIリクエスト日時',
          value: 'request_date'
        },
        {
          text: 'APIレスポンス日時',
          value: 'response_date'
        },
      ],

    };
  },

  asyncData: async function () {
    // API情報一覧取得API 実行
    let result = await axios.get(url);
    return {
      api_data_list: result.data.api_data_list,
    };
  },
}
</script>

<style>
.base {
  padding-left: 10px;
}
</style>