<template>
  <div>
    <v-row>
      <v-col class="text-center">
        <h1>アカウント情報一覧 index</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>
        <v-data-table :headers="headers" :items="account_list" :search="search"></v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
const axios = require("axios");
let url = process.env.api_base_url + "account";

export default {
  data: function() {
    return {
      search: '',
      account_list: [],
      headers: [
        {
          text: 'ユーザID',
          value: 'seq_user_id'
        },
        {
          text: 'メールアドレス',
          value: 'mail_address'
        },
        {
          text: '削除フラグ',
          value: 'delete_flag'
        },
        {
          text: 'パスワード有効期限',
          value: 'password_expire'
        },
        {
          text: '備考',
          value: 'remarks'
        },
        {
          text: 'APIキー',
          value: 'api_key'
        },
        {
          text: 'ヘッダ利用有無フラグ',
          value: 'header_flag'
        },
        {
          text: 'フッタ利用有無フラグ',
          value: 'footer_flag'
        },
        {
          text: 'マスク利用有無フラグ',
          value: 'mask_flag'
        },
        {
          text: '囲み文字利用有無フラグ',
          value: 'enclosure_char_flag'
        }
      ],

    };
  },

  asyncData: async function () {
    // アカウント情報一覧取得API 実行
    let result = await axios.get(url);
    return {
      account_list: result.data.account_list,
    };
  },
};
</script>

<style>
</style>