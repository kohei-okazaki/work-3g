<template>
  <div>
    <AppTitle icon="mdi-account" title="アカウント情報一覧" />
    <AppError v-if="error.hasError" :data="error" />
    <v-row>
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索条件"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table :headers="headers" :items="account_list" :search="search">
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.delete_flag`]="{ item }">
            <v-icon v-if="item.delete_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.delete_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.header_flag`]="{ item }">
            <v-icon v-if="item.header_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.header_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.footer_flag`]="{ item }">
            <v-icon v-if="item.footer_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.footer_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.mask_flag`]="{ item }">
            <v-icon v-if="item.mask_flag == 1" color="green">mdi-check</v-icon>
            <v-icon v-else-if="item.mask_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.enclosure_char_flag`]="{ item }">
            <v-icon v-if="item.enclosure_char_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.enclosure_char_flag == 0">mdi-minus</v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppError from "~/components/AppError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "account";

export default {
  components: {
    AppTitle,
    AppError,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      search: "",
      account_list: [],
      headers: [
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "メールアドレス",
          value: "mail_address",
        },
        {
          text: "削除フラグ",
          value: "delete_flag",
        },
        {
          text: "パスワード有効期限",
          value: "password_expire",
        },
        {
          text: "備考",
          value: "remarks",
        },
        {
          text: "APIキー",
          value: "api_key",
        },
        {
          text: "登録日時",
          value: "reg_date",
        },
        {
          text: "更新日時",
          value: "update_date",
        },
        {
          text: "ヘッダ利用有無",
          value: "header_flag",
        },
        {
          text: "フッタ利用有無",
          value: "footer_flag",
        },
        {
          text: "マスク利用有無",
          value: "mask_flag",
        },
        {
          text: "囲み文字利用有無",
          value: "enclosure_char_flag",
        },
      ],
    };
  },

  created: function () {
    axios
      .get(url, {
        headers: { Authorization: this.$store.state.auth.token },
      })
      .then(
        (response) => {
          this.account_list = response.data.account_list;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
  },
};
</script>

<style scoped>
</style>