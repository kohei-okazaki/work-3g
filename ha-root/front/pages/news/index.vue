<template>
  <div>
    <AppTitle icon="mdi-newspaper" title="お知らせ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <template v-if="isEntryShow">
      <NewsEntry @get-news="getNews" />
      <v-dialog v-model="newsEditModal.dialog" max-width="600px">
        <!-- ダイアログ本体 -->
        <v-card>
          <v-card-title>お知らせ情報更新</v-card-title>
          <v-divider></v-divider>
          <br />
          <v-card-text>
            <v-row>
              <v-col class="text-center" cols="12" sm="12">
                <v-form ref="editNewsForm">
                  <v-text-field
                    v-model="newsEditModal.title"
                    label="タイトル"
                    clearable
                    :rules="[required]"
                  ></v-text-field>
                  <v-text-field
                    v-model="newsEditModal.date"
                    label="日付"
                    hint="年/月/日"
                    persistent-hint
                    @click="
                      newsEditModal.isDispCalendar = !newsEditModal.isDispCalendar
                    "
                    clearable
                    :rules="[required]"
                  ></v-text-field>
                  <template v-if="newsEditModal.isDispCalendar">
                    <v-date-picker
                      v-model="newsEditModal.date"
                      no-title
                      @input="
                        newsEditModal.isDispCalendar = !newsEditModal.isDispCalendar
                      "
                    ></v-date-picker>
                  </template>
                  <v-textarea
                    v-model="newsEditModal.detail"
                    label="詳細(htmlタグの入力も可能です)"
                    clearable
                    :rules="[required]"
                  ></v-textarea>
                  <NewsTagPullDown
                    v-model="newsEditModal.tag.color"
                    color="blue" />
                  <v-text-field
                    v-model="newsEditModal.tag.name"
                    label="タグ名"
                    clearable
                    :rules="[required]"
                  ></v-text-field
                ></v-form>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions class="pt-0">
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="editNews">
              <v-icon>mdi-newspaper-plus</v-icon>&ensp;更新
            </v-btn>
            <v-btn color="accent" @click="reset">
              <v-icon>mdi-alert</v-icon>&ensp;リセット
            </v-btn>
            <v-btn color="grey" @click="newsEditModal.dialog = false"
              >取消</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table
          :headers="headers"
          :items="news_list"
          hide-default-footer="true"
        >
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.tag.name`]="{ item }">
            <v-chip
              :color="item.tag.color"
              v-if="item.tag.color != null && item.tag.name != null"
            >
              <b>{{ item.tag.name }}</b>
            </v-chip>
          </template>
          <template v-slot:[`item.detail`]="{ item }">
            <div v-html="item.detail"></div>
          </template>
          <template v-slot:[`item.edit_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openNewsEditModal(item.seq_news_info_id)"
              v-if="isEntryShow"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.delete_action`]="{ item }">
            <v-btn small class="mx-1" @click="openNewsDeleteModal(item.seq_news_info_id)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
        <ConfirmModal ref="confirm" />
        <ProcessFinishModal ref="finish" />
        <AppLoading :loading="loading" />
        <v-pagination
          v-model="paging.page"
          :length="paging.totalPage"
          @input="pageChange()"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NewsEntry from "~/components/news/NewsEntry.vue";
import NewsTagPullDown from "~/components/news/NewsTagPullDown.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    NewsEntry,
    NewsTagPullDown,
    ConfirmModal,
    ProcessFinishModal,
    AppTitle,
    AppMessageError,
    AppLoading,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      isRefShow: false,
      isEntryShow: false,
      loading: false,
      news_list: [],
      headers: [
        {
          text: "",
          value: "edit_action",
          sortable: false,
        },
        {
          text: "",
          value: "delete_action",
          sortable: false,
        },
        {
          text: "ID",
          value: "seq_news_info_id",
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
          value: "tag.name",
        },
      ],
      paging: {
        // 現在のページ数
        page: 0,
        // 総ページ数
        totalPage: 0,
      },
      newsEditModal: {
        dialog: false,
        seq_news_info_id: "",
        title: "",
        date: "",
        detail: "",
        tag: {
          color: "",
          name: "",
        },
        isDispCalendar: false,
      },
      required: (value) => !!value || "必ず入力してください",
    };
  },
  created: function () {
    this.checkEntryView();
    this.checkRefView();
    if (this.isRefShow) {
      this.getNews();
    }
  },
  methods: {
    /**
     * 指定したページのお知らせ情報を取得
     * @param page 取得対象ページ
     */
    getNews: function (page) {
      this.loading = true;

      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // お知らせ情報一覧取得API
      // APIは0~、frontは1~なのでAPIに合わせfrontのページ数に-1
      let reqUrl = url + "?page=" + (page == null ? 0 : page - 1);
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result === "0") {
            this.news_list = response.data.news_list;
            // APIは0~、frontは1~なのでfrontに合わせAPIの戻り値に+1
            this.paging.page = response.data.paging.current_page_num + 1;
            this.paging.totalPage = response.data.paging.total_page;
          } else {
            this.error.hasError = true;
            this.error.message = response.data.error.message;
          }
          this.loading = false;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          this.loading = false;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
    /**
     * お知らせ情報編集モーダル表示
     * @param targetNewsId お知らせ情報ID
     */
    openNewsEditModal: function (targetNewsId) {
      this.newsEditModal.dialog = true;
      this.newsEditModal.seq_news_info_id = targetNewsId;
      for (var i = 0; i < this.news_list.length; i++) {
        let news = this.news_list[i];
        if (news.seq_news_info_id == targetNewsId) {
          // カレンダー表示に対応させるため、YYYY-MM-DD形式に変換する
          news.date = news.date.replaceAll("/", "-");
          this.newsEditModal.title = news.title;
          this.newsEditModal.date = news.date;
          this.newsEditModal.detail = news.detail;
          this.newsEditModal.tag.color = news.tag.color;
          this.newsEditModal.tag.name = news.tag.name;
          break;
        }
      }
    },
    /**
     * お知らせ情報編集処理
     */
    editNews: function () {
      if (!this.$refs.editNewsForm.validate()) {
        // 入力値エラーの場合
        return;
      }

      this.loading = true;
      // お知らせ情報編集API
      let reqUrl = url + "/" + this.newsEditModal.seq_news_info_id;
      let reqBody = {
        seq_news_info_id: this.newsEditModal.seq_news_info_id,
        title: this.newsEditModal.title,
        date: this.newsEditModal.date.replaceAll("-", "/"),
        detail: this.newsEditModal.detail,
        tag: {
          color: this.newsEditModal.tag.color,
          name: this.newsEditModal.tag.name,
        },
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      let headers = {
        Authorization: this.$store.state.auth.token,
      };

      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // 処理完了モーダルを表示
            let json = JSON.stringify(reqBody, null, "\t");
            this.$refs.finish.open("お知らせ情報 更新処理", json, {
              color: "blue",
              width: 400,
            });

            // 最新のお知らせ情報を取得する
            this.getNews();
            this.loading = false;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          this.loading = false;
          console.log("[error]=" + error);
          return error;
        }
      );
      this.newsEditModal.dialog = false;
    },
    /**
     * 編集モーダルの入力情報をクリアする
     */
    reset: function () {
      this.$refs.editNewsForm.reset();
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getNews(this.paging.page);
    },
    /**
     * お知らせ情報削除モーダル表示
     * @param seq_news_info_id お知らせ情報ID
     */
    async openNewsDeleteModal(seq_news_info_id) {
      let modalInfo = {
        color: "red",
        width: 400,
      };

      if (
        await this.$refs.confirm.open(
          "削除確認",
          "お知らせ情報ID=" + seq_news_info_id + "のレコードを削除しますか？",
          modalInfo
        )
      ) {
        // 確認モーダルで削除に同意した場合
        this.deleteNews(seq_news_info_id);
      }
    },
    /**
     * お知らせ情報を削除
     * @param seq_news_info_id お知らせ情報ID
     */
    async deleteNews(seq_news_info_id) {
      this.loading = true;
      // お知らせ情報削除API
      let deleteUrl = url + "/" + seq_news_info_id;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios
        .delete(deleteUrl, {
          headers,
        })
        .then(
          (response) => {
            if (response.data.result == "0") {
              // 削除成功時

              // 処理完了モーダルを表示
              this.$refs.finish.open("お知らせ情報削除処理", "", {
                color: "blue",
                width: 400,
              });

              // おしらせ情報取得
              this.getNews();
              this.loading = false;
            }
          },
          (error) => {
            console.log("[error]=" + error);
            return error;
          }
        );
    },
    /**
     * 照会権限判定処理
     */
    checkRefView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "01") {
          this.isRefShow = true;
          return;
        }
      }
      this.isRefShow = false;
    },
    /**
     * 登録権限判定処理
     */
    checkEntryView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "02") {
          this.isEntryShow = true;
          return;
        }
      }
      this.isEntryShow = false;
    },
  },
};
</script>

<style scoped>
</style>