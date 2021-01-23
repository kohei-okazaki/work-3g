<template>
  <div>
    <v-row>
      <v-col class="text-center">
        <h1>お知らせ情報一覧 index</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table
          :headers="headers"
          :items="news_list"
          :search="search"
        ></v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
const axios = require("axios");
let url = process.env.api_base_url + "news";

export default{
  data: function() {
    return {
      search: "",
      news_list: [],
      headers: [
        {
          text: "ID",
          value: "index",
        },
        {
          text: "タイトル",
          value: "title",
        },
        {
          text: "詳細",
          value: "detail",
        },
        {
          text: "タグ色",
          value: "tag_color",
        },
        {
          text: "タグ名",
          value: "tag_name",
        },
      ]
    }
  },
  created: function () {
    // 保存済のAPIトークンを取得
    let token = this.$store.state.auth.token;
    
    axios.get(url, {
      headers: { "Authorization": token },
    }).then((response) => {
      this.news_list = response.data.news_list;
    }, (error) => {
      console.log('[error]=' + error);
      return error;
    });
  },
}
</script>

<style>

</style>