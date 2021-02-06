<template>
  <div>
    <AppTitle icon="mdi-newspaper" title="お知らせ一覧" />
    <template v-if="entry_mode">
      <NewsEntry @get-news="getNews" />
    </template>
    <template v-else>
      <NewsEdit @get-news="getNews" :edit_news_form="edit_news_form" />
    </template>
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
          <template v-slot:[`item.tag.name`]="{ item }">
            <v-chip
              :color="getTagColor(item.tag.color)"
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

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    NewsEntry,
    NewsEdit,
    ConfirmModal,
    ProcessFinishModal,
    AppTitle,
  },
  data: function () {
    return {
      entry_mode: true,
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
      tag_color_select_list: [
        {
          color: "blue",
          label: "青色",
        },
        {
          color: "yellow",
          label: "黄色",
        },
        {
          color: "red",
          label: "赤色",
        },
      ],
      edit_news_form: {
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
    this.getNews();
  },
  methods: {
    getTagColor: function (tag_color) {
      for (var i = 0; i < this.tag_color_select_list.length; i++) {
        let tag = this.tag_color_select_list[i];
        if (tag.color == tag_color) {
          return tag.color;
        }
      }
    },
    getNews() {
      axios
        .get(url, {
          headers: { Authorization: this.$store.state.auth.token },
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
    changeNewsEditView: function (edit_news_id) {
      for (var i = 0; i < this.news_list.length; i++) {
        let targetNews = this.news_list[i];
        if (targetNews.index == edit_news_id) {
          // カレンダー表示に対応させるため、YYYY-MM-DD形式に変換する
          targetNews.date = targetNews.date.replaceAll("/", "-");
          this.edit_news_form = targetNews;
          this.entry_mode = false;
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