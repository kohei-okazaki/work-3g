<template>
  <div>
    <AppTitle icon="mdi-newspaper" title="お知らせ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <template v-if="isEntryShow">
      <template v-if="entryMode">
        <NewsEntry @get-news="getNews" />
      </template>
      <template v-else>
        <NewsEdit
          @get-news="getNews"
          @back-entry="backEntry"
          :edit_news_form="editNewsForm"
        />
      </template>
    </template>
    <v-row v-if="isRefShow">
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
            <v-btn small class="mx-1" @click="changeNewsEditView(item.index)">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.delete_action`]="{ item }">
            <v-btn small class="mx-1" @click="openNewsDeleteModal(item.index)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
        <ConfirmModal ref="confirm" />
        <ProcessFinishModal ref="finish" />
        <v-overlay :value="loading">
          <v-progress-circular indeterminate size="128"></v-progress-circular>
        </v-overlay>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NewsEntry from "~/components/news/NewsEntry.vue";
import NewsEdit from "~/components/news/NewsEdit.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    NewsEntry,
    NewsEdit,
    ConfirmModal,
    ProcessFinishModal,
    AppTitle,
    AppMessageError,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      isRefShow: false,
      isEntryShow: false,
      entryMode: true,
      loading: false,
      search: "",
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
          value: "tag.name",
        },
      ],
      editNewsForm: {
        index: "",
        title: "",
        date: "",
        detail: "",
        tag: {
          color: "",
          name: "",
        },
      },
    };
  },
  created: function () {
    this.isEntryView();
    this.isRefView();
    if (!this.isRefShow) {
      return;
    }
    this.getNews();
  },
  methods: {
    getNews: function () {
      axios
        .get(url, {
          headers: { Authorization: this.$store.state.auth.token },
        })
        .then(
          (response) => {
            this.news_list = response.data.news_list;
          },
          (error) => {
            this.error.hasError = true;
            this.error.message = error;
            console.log("[error]=" + error);
            return error;
          }
        );
    },
    changeNewsEditView: function (targetNewsId) {
      for (var i = 0; i < this.news_list.length; i++) {
        let targetNews = this.news_list[i];
        if (targetNews.index == targetNewsId) {
          // カレンダー表示に対応させるため、YYYY-MM-DD形式に変換する
          targetNews.date = targetNews.date.replaceAll("/", "-");
          this.editNewsForm = targetNews;
          this.entryMode = false;
          break;
        }
      }
    },
    async openNewsDeleteModal(id) {
      let modalInfo = {
        color: "red",
        width: 400,
      };

      if (
        await this.$refs.confirm.open(
          "削除確認",
          "お知らせ情報ID=" + id + "のレコードを削除しますか？",
          modalInfo
        )
      ) {
        // 確認モーダルで削除に同意した場合
        this.deleteNews(id);
      }
    },
    async deleteNews(id) {
      this.loading = true;
      let deleteUrl = url + "/" + id;
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
    backEntry: function () {
      this.entryMode = true;
    },
    isRefView: function () {
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
    isEntryView: function () {
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