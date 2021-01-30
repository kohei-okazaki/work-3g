<template>
  <v-row>
    <v-col class="text-center">
      <v-card>
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
            <v-textarea v-model="entry_info.detail" label="詳細(htmlタグの入力も可能です)"></v-textarea>
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
            <v-icon>mdi-newspaper-plus</v-icon>&ensp;登録
          </v-btn>
        </v-card-actions>
        <ProcessFinishModal ref="finish" />
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    ProcessFinishModal,
  },
  data: function () {
    return {
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
  methods: {
    controllCalendar: function () {
      this.isDispCalendar = !this.isDispCalendar;
    },
    submit: function () {
      let params = new URLSearchParams();
      params.append("title", this.entry_info.title);
      params.append("date", this.entry_info.date.replaceAll("-", "/"));
      params.append("detail", this.entry_info.detail);
      params.append("tag_color", this.entry_info.tag_color.value);
      params.append("tag_name", this.entry_info.tag_name);
      // 保存済のAPIトークンを取得
      let token = this.$store.state.auth.token;
      let headers = {
        Authorization: token,
      };

      axios.post(url, params, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // お知らせ情報登録APIが正常終了した場合
            this.api_data.api_result = result.data.result;
            this.api_data.api_entry_info = result.data.entry_data;

            // 処理完了モーダルを表示
            let json = JSON.stringify(this.entry_info, null, "\t");
            this.$refs.finish.open("お知らせ情報登録処理", json, {
              color: "blue",
              width: 400,
            });

            // お知らせ情報登録後、最新のお知らせ情報を取得する
            this.$emit("get-news");
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

<style scoped>
</style>