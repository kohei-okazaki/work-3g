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
          <template v-slot:[`item.edit_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              :to="'/news/edit/' + item.index"
              @click="toNewsEditView('/news/edit/' + item.index)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.delete_action`]="{ item }">
            <v-btn small class="mx-1" @click="openNewsDeleteModal(item.index)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
            <ConfirmModal ref="confirm" />
            <ProcessFinishModal ref="finish" />
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NewsEntry from "~/components/news/NewsEntry.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";

const axios = require("axios");
let url = process.env.api_base_url + "news";

export default {
  components: {
    NewsEntry,
    ConfirmModal,
    ProcessFinishModal,
  },
  data: function () {
    return {
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
    this.getNews();
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
    getNews() {
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
    toNewsEditView: function (url) {
      console.log("reqUrl = " + url);
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
    deleteNews: function (id) {
      let deleteUrl = url + "/" + id;
      let token = this.$store.state.auth.token;

      axios
        .delete(deleteUrl, {
          headers: { Authorization: token },
        })
        .then(
          (response) => {
            if (response.data.result == "0") {
              // 削除成功時
            }

            // おしらせ情報取得
            this.getNews();

            // 処理完了モーダルを表示
            this.$refs.finish.open("お知らせ情報削除処理", "", {
              color: "blue",
              width: 550,
            });
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