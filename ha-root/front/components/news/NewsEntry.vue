<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="6">
        <v-card>
          <v-card-text>
            <v-form ref="entryForm">
              <v-text-field
                v-model="entryInfo.title"
                label="タイトル"
                clearable
                :rules="[required]"
              ></v-text-field>
              <v-text-field
                v-model="entryInfo.date"
                label="日付"
                hint="年/月/日"
                persistent-hint
                @click="controllCalendar"
                clearable
                :rules="[required]"
              ></v-text-field>
              <template v-if="isDispCalendar">
                <v-date-picker
                  v-model="entryInfo.date"
                  no-title
                  @input="controllCalendar"
                ></v-date-picker>
              </template>
              <v-textarea
                v-model="entryInfo.detail"
                label="詳細(htmlタグの入力も可能です)"
                clearable
                :rules="[required]"
              ></v-textarea>
              <NewsTagPullDown v-model="entryInfo.tag.color" color="blue" />
              <v-text-field
                v-model="entryInfo.tag.name"
                label="タグ名"
                clearable
                :rules="[required]"
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
              <v-icon>mdi-newspaper-plus</v-icon>&ensp;登録
            </v-btn>
            <v-btn color="accent" @click="reset">
              <v-icon>mdi-alert</v-icon>&ensp;リセット
            </v-btn>
          </v-card-actions>
          <ProcessFinishModal ref="finish" />
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import NewsTagPullDown from "~/components/news/NewsTagPullDown.vue";
import AppMessageError from "~/components/AppMessageError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    ProcessFinishModal,
    NewsTagPullDown,
    AppMessageError,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      isDispCalendar: false,
      loading: false,
      entryInfo: {
        title: "",
        date: new Date().toISOString().substr(0, 10),
        detail: "",
        tag: {
          color: "",
          name: "",
        },
      },
      required: (value) => !!value || "必ず入力してください",
    };
  },
  methods: {
    controllCalendar: function () {
      this.isDispCalendar = !this.isDispCalendar;
    },
    reset() {
      this.$refs.entryForm.reset();
    },
    submit: function () {
      this.loading = true;
      if (!this.$refs.entryForm.validate()) {
        // 入力値エラーの場合
        this.loading = false;
        return;
      }
      let reqBody = {
        index: this.entryInfo.index,
        title: this.entryInfo.title,
        date: this.entryInfo.date.replaceAll("-", "/"),
        detail: this.entryInfo.detail,
        tag: {
          color: this.entryInfo.tag.color,
          name: this.entryInfo.tag.name,
        },
      };

      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      console.log(JSON.stringify(reqBody, null, "\t"));
      axios.post(url, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // 処理完了モーダルを表示
            let json = JSON.stringify(this.entryInfo, null, "\t");
            this.$refs.finish.open("お知らせ情報登録処理", json, {
              color: "blue",
              width: 400,
            });

            // お知らせ情報登録後、最新のお知らせ情報を取得する
            this.$emit("get-news");

            this.loading = false;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          this.loading = false;
          return error;
        }
      );
    },
  },
};
</script>

<style scoped>
</style>