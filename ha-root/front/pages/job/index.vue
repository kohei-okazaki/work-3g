<template>
  <div>
    <AppTitle icon="mdi-bash" title="バッチ実行ログ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table :headers="headers" :items="jobLogList" hide-default-footer="true">
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.status`]="{ item }">
            <v-chip :color="getStatusColor(item.status)" v-if="item.status != null">
              {{ item.status }}
            </v-chip>
          </template>
          <template v-slot:[`item.parameterList`]="{ item }">
            {{ JSON.stringify(item.parameter_list) }}
          </template>
        </v-data-table>
        <AppLoading :loading="loading" />
        <v-pagination v-model="paging.page" :length="paging.totalPage" @input="pageChange()" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "job";

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
      jobLogList: [],
      headers: [
        {
          // 詳細画面へのリンクのための空ヘッダー
          text: "",
          value: "detail",
          sortable: false,
          width: "80px",
        },
        {
          text: "jobInstanceId",
          value: "job_instance_id",
        },
        {
          text: "Job名",
          value: "job_name",
        },
        {
          text: "バッチステータス",
          value: "status",
        },
        {
          text: "開始日時",
          value: "start_time",
        },
        {
          text: "終了日時",
          value: "end_time",
        },
        {
          text: "パラメータ",
          value: "parameter_list",
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
      // 照会権限がある場合、バッチ実行ログ取得
      this.getJobLogList();
    }
  },
  methods: {
    /**
     * ステータスに応じた色を取得する
     * @param status ステータス
     * @returns 色
     */
    getStatusColor: function (status) {
      if (status == "COMPLETED") {
        return "green";
      } else if (status == "FAILED") {
        return "red";
      }
      return "gray";
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
     * 指定したページ数のバッチ実行ログリストを取得
     * @param page ページ数
     */
    getJobLogList: function (page) {
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
            this.jobLogList = response.data.job_list;
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
          console.log("joblist [error]=" + error);
          return error;
        }
      );
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getJobLogList(this.paging.page);
    },
  },
};
</script>

<style scoped></style>