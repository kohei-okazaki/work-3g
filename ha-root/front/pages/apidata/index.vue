<template>
  <div>
    <AppTitle icon="mdi-api" title="API通信情報一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row>
      <v-col>
        <transition name="fade">
          <v-timeline align-top dense v-if="timelines.length != 0">
            <v-timeline-item
              small
              v-for="(timeline, i) in timelines"
              :key="i"
              :color="timeline.color"
            >
              <v-card max-width="650" :color="timeline.color">
                <v-card-title class="title">{{
                  timeline.api_name
                }}</v-card-title>
                <!-- 白色を固定するとダークモードに切り替えたときに白色のままなので算出プロパティで常にモードを見て背景を決めるようにする -->
                <v-card-text :class="timelineCardTextBgColor">
                  <br />
                  <div>
                    <b>API通信情報ID</b>={{
                      timeline.seq_api_communication_data_id
                    }}
                  </div>
                  <div><b>API名</b>={{ timeline.api_name }}</div>
                  <div><b>HTTPステータス</b>={{ timeline.http_status }}</div>
                  <div><b>送信日時</b>={{ timeline.request_date }}</div>
                  <div><b>受信日時</b>={{ timeline.response_date }}</div>
                </v-card-text>
              </v-card>
            </v-timeline-item>
          </v-timeline></transition
        >
      </v-col>
    </v-row>
    <v-row v-if="isRefShow">
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索条件"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table
          :headers="headers"
          :items="apiDataList"
          :search="search"
          class="pushable"
          @click:row="openTimelineModal"
        >
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.http_status`]="{ item }">
            <v-chip
              :color="getHttpStatusColor(item.http_status)"
              v-if="item.http_status != null"
            >
              {{ item.http_status }}
            </v-chip>
          </template>
        </v-data-table>
        <AppLoading :loading="loading" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "apidata";

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
      timelines: [],
      search: "",
      apiDataList: [],
      headers: [
        {
          text: "API通信情報ID",
          value: "seq_api_communication_data_id",
        },
        {
          text: "トランザクションID",
          value: "transaction_id",
        },
        {
          text: "API名",
          value: "api_name",
        },
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "HTTPステータス",
          value: "http_status",
        },
        {
          text: "処理結果",
          value: "result",
        },
        {
          text: "詳細",
          value: "detail",
        },
        {
          text: "送信日時",
          value: "request_date",
        },
        {
          text: "受信日時",
          value: "response_date",
        },
      ],
    };
  },
  created: function () {
    this.isRefView();
    if (!this.isRefShow) {
      return;
    }
    this.getApiDataList();
  },
  methods: {
    getHttpStatusColor: function (httpStatus) {
      if (httpStatus == 200) {
        return "green";
      } else if (400 <= httpStatus && httpStatus < 500) {
        return "yellow";
      } else if (
        (500 <= httpStatus && httpStatus < 600) ||
        httpStatus == null
      ) {
        return "red";
      }
    },
    openTimelineModal: function (item) {
      console.log(JSON.stringify(item, null, "\t"));
      // タイムラインデータをクリア
      this.timelines = [];
      for (var i = 0; i < this.apiDataList.length; i++) {
        let apiData = this.apiDataList[i];
        if (item.transaction_id == apiData.transaction_id) {
          let timeline = {
            seq_api_communication_data_id:
              apiData.seq_api_communication_data_id,
            api_name: apiData.api_name,
            http_status: apiData.http_status,
            request_date: apiData.request_date,
            response_date: apiData.response_date,
            color: this.getHttpStatusColor(apiData.http_status),
          };
          this.timelines.push(timeline);
        }
      }
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
    getApiDataList: function () {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(url, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.apiDataList = response.data.api_data_list;
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
          console.log("apidata [error]=" + error);
          return error;
        }
      );
    },
  },
  computed: {
    timelineCardTextBgColor: function () {
      return this.$vuetify.theme.dark ? "black" : "white";
    },
  },
};
</script>

<style scoped>
.base {
  padding-left: 10px;
}
.fade-enter-active,
.fade-leave-active {
  transition: 0.6s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
  transform: scale(0);
}
</style>