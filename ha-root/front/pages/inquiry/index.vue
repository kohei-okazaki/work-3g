<template>
  <div>
    <AppTitle icon="mdi-notebook" title="問い合わせ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table
          :headers="headers"
          :items="inquiryList"
          hide-default-footer="true"
        >
        </v-data-table>
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
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "inquiry";

export default {
  components: {
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
      loading: false,
      isRefShow: false,
      inquiryList: [],
      headers: [
        {
          text: "管理ID",
          value: "seq_inquriy_mng_id",
          sortable: false,
        },
        {
          text: "問い合わせユーザID",
          value: "seq_user_id",
          sortable: false,
        },
        {
          text: "対応者ID",
          value: "responder_login_id",
          sortable: false,
        },
        {
          text: "ステータス",
          value: "status.label",
          sortable: false,
        },
        {
          text: "問い合わせ種別",
          value: "type.label",
          sortable: false,
        },
        {
          text: "タイトル",
          value: "title",
          sortable: false,
        },
        {
          text: "詳細",
          value: "body",
          sortable: false,
        },
        {
          text: "登録日時",
          value: "reg_date",
          sortable: false,
        },
        {
          text: "更新日時",
          value: "update_date",
          sortable: false,
        },
      ],
      paging: {
        // 現在のページ数
        page: 0,
        // 総ページ数
        totalPage: 0,
      },
    };
  },
  created: function () {
    this.checkRefView();
    if (this.isRefShow) {
      // 照会権限がある場合、問い合わせ情報取得
      this.getInquiryList();
    }
  },
  methods: {
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
     * 指定したページ数の問い合わせ情報リストを取得
     * @param page ページ数
     */
    getInquiryList: function (page) {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // APIは0~、frontは1~なのでAPIに合わせfrontのページ数に-1
      let reqUrl = url + "?page=" + (page == null ? 0 : page - 1);
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.inquiryList = response.data.inquiry_list;
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
          console.log("inquiry [error]=" + error);
          return error;
        }
      );
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getInquiryList(this.paging.page);
    },
  },
};
</script>

<style scoped>
</style>