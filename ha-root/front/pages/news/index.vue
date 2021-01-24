<template>
  <div>
    <v-row>
      <v-col class="text-center">
        <h1>お知らせ情報一覧</h1>
      </v-col>
    </v-row>
    <!-- お知らせ情報登録部 ここから -->
    <v-row>
      <v-col class="text-center">
        <v-card>
          <v-card-title>{{ title }}</v-card-title>
          <v-card-text>
            <v-form ref="entry_form">
              <v-text-field
                v-model="entry_info.title"
                label="タイトル"
              ></v-text-field>
              <v-text-field
                v-model="entry_info.date"
                label="日付"
                hint="年/月/日"
                persistent-hint
                @click="controllCalendar"
              ></v-text-field>
              <template v-if="isDispCalendar">
                <v-date-picker
                  v-model="entry_info.date"
                  no-title
                  @input="controllCalendar"
                ></v-date-picker>
              </template>
              <v-textarea v-model="entry_info.detail" label="詳細"></v-textarea>
              <v-select
                v-model="entry_info.tag_color"
                :items="tag_color_select_list"
                label="タグ色"
                item-text="label"
                item-value="value"
                return-object
                single-line
              ></v-select>
              <v-text-field
                v-model="entry_info.tag_name"
                label="タグ名"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="submit">
              <v-icon>mdi-newspaper-plus</v-icon>登録
            </v-btn>
          </v-card-actions>
          <Modal ref="finish" />
        </v-card>
      </v-col>
    </v-row>
    <!-- お知らせ情報登録部 ここまで -->
    <!-- お知らせ情報表示部 ここから -->
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
    <!-- お知らせ情報表示部 ここまで -->
  </div>
</template>

<script>
import Modal from "~/components/ProcessFinishModal.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";
let entry_url = process.env.api_base_url + "news/entry";

export default {
  components: {
    Modal,
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
      isDispCalendar: false,
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
      entry_info: {
        title: "",
        date: new Date().toISOString().substr(0, 10),
        detail: "",
        tag_color: "",
        tag_name: "",
      },
      api_data: {
        api_result: "",
        api_entry_info: {
          index: "",
          title: "",
          date: "",
          detail: "",
          tag_color: "",
          tag_name: "",
        },
      },
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
    controllCalendar: function () {
      this.isDispCalendar = !this.isDispCalendar;
    },
    submit: function () {
      // 保存済のAPIトークンを取得
      let token = this.$store.state.auth.token;
      console.log("title=" + this.entry_info.title);
      console.log("date=" + this.entry_info.date);

      let params = new URLSearchParams();
      params.append("title", this.entry_info.title);
      params.append("date", this.entry_info.date.replaceAll("-", "/"));
      params.append("detail", this.entry_info.detail);
      params.append("tag_color", this.entry_info.tag_color.value);
      params.append("tag_name", this.entry_info.tag_name);

      let headers = {
        Authorization: token,
      };

      axios.post(entry_url, params, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // お知らせ情報登録APIが正常終了した場合
            this.api_data.api_result = result.data.result;
            this.api_data.api_entry_info = result.data.entry_data;

            // 処理完了モーダルを表示
            this.$refs.finish.open("お知らせ情報登録処理", this.entry_info, {
              color: "blue",
              width: 550,
            });
          }
        },
        (error) => {
          console.log("[error]=" + error);
          return error;
        }
      );
    },
  },
};
</script>

<style>
</style>