<template>
  <v-row>
    <v-col class="text-center">
      <v-card>
        <v-card-text>
          <v-form ref="edit_form">
            <v-text-field
              v-model="edit_news_form.index"
              label="更新対象お知らせ情報ID"
              disabled
              >{{ edit_news_form.index }}</v-text-field
            >
            <v-text-field
              v-model="edit_news_form.title"
              label="タイトル"
              clearable
            ></v-text-field>
            <v-text-field
              v-model="edit_news_form.date"
              label="日付"
              hint="年/月/日"
              persistent-hint
              clearable
              @click="controllCalendar"
            >
            </v-text-field>
            <template v-if="isDispCalendar">
              <v-date-picker
                v-model="edit_news_form.date"
                no-title
                @input="controllCalendar"
              ></v-date-picker>
            </template>
            <v-textarea
              v-model="edit_news_form.detail"
              label="詳細(htmlタグの入力も可能です)"
              clearable
            ></v-textarea>
            <NewsTagPullDown
              v-model="edit_news_form.tag.color"
              :color="edit_news_form.tag.color"
            />
            <v-text-field
              v-model="edit_news_form.tag.name"
              label="タグ名"
              clearable
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            @click="submit"
            :loading="loading"
            :disabled="loading"
          >
            <v-icon>mdi-comment-edit</v-icon>&ensp;更新
          </v-btn>
        </v-card-actions>
        <ProcessFinishModal ref="finish" />
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import NewsTagPullDown from "~/components/news/NewsTagPullDown.vue";
const axios = require("axios");
let url = process.env.api_base_url + "news/";

export default {
  components: {
    ProcessFinishModal,
    NewsTagPullDown,
  },
  props: {
    edit_news_form: Object,
  },
  data: function () {
    return {
      isDispCalendar: false,
      loading: false,
    };
  },
  methods: {
    controllCalendar: function () {
      this.isDispCalendar = !this.isDispCalendar;
    },
    submit: function () {
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + this.edit_news_form.index;
      let reqBody = {
        index: this.edit_news_form.index,
        title: this.edit_news_form.title,
        date: this.edit_news_form.date.replaceAll("-", "/"),
        detail: this.edit_news_form.detail,
        tag: {
          color: this.edit_news_form.tag.color,
          name: this.edit_news_form.tag.name,
        },
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // 処理完了モーダルを表示
            let json = JSON.stringify(this.edit_news_form, null, "\t");
            this.$refs.finish.open("お知らせ情報更新処理", json, {
              color: "blue",
              width: 400,
            });

            // お知らせ情報更新後、最新のお知らせ情報を取得する
            this.$emit("get-news");
            this.loading = false;
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