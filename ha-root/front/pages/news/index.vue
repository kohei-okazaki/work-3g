<template>
  <div>
    <v-row>
      <v-col class="text-center">
        <h1>お知らせ情報一覧</h1>
      </v-col>
    </v-row>
    <NewsEntry />
    <v-row>
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索条件"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table :headers="headers" :items="news_list" :search="search">
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.tag_name`]="{ item }">
            <v-chip
              :color="getTagColor(item.tag_color)"
              v-if="item.tag_color != null && item.tag_name != null"
            >
              <b>{{ item.tag_name }}</b>
            </v-chip>
          </template>
          <template v-slot:[`item.detail`]="{ item }">
            <div v-html="item.detail"></div>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NewsEntry from "~/components/news/entry.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    NewsEntry,
  },
  data: function () {
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
          text: "日付",
          value: "date",
        },
        {
          text: "タグ名",
          value: "tag_name",
        },
      ],
      tag_color_select_list: [
        {
          value: "1",
          label: "1(blue)",
        },
        {
          value: "2",
          label: "2(yellow)",
        },
        {
          value: "3",
          label: "3(red)",
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
          this.news_list = response.data.news_list;
        },
        (error) => {
          console.log("[error]=" + error);
          return error;
        }
      );
  },
  methods: {
    getTagColor: function (tag_color) {
      if (tag_color == 1) {
        return "blue";
      } else if (tag_color == 2) {
        return "red";
      } else if (tag_color == 3) {
        return "yellow";
      }
    },
  },
};
</script>

<style>
</style>