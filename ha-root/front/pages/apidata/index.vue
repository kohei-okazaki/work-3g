<template>
  <div>
    <AppTitle icon="mdi-api" title="API通信情報一覧" />
    <AppError v-if="error.hasError" :message="error.message" />
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
    <v-row>
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
          :items="api_data_list"
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
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppError from "~/components/AppError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "apidata";

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
      timelines: [],
      search: "",
      api_data_list: [],
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
    axios
      .get(url, {
        headers: { Authorization: this.$store.state.auth.token },
      })
      .then(
        (response) => {
          this.api_data_list = response.data.api_data_list;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
  },
  methods: {
    getHttpStatusColor: function (http_status) {
      if (http_status == 200) {
        return "green";
      } else if (400 <= http_status && http_status < 500) {
        return "yellow";
      } else if (
        (500 <= http_status && http_status < 600) ||
        http_status == null
      ) {
        return "red";
      }
    },
    openTimelineModal: function (item) {
      console.log(JSON.stringify(item, null, "\t"));
      // タイムラインデータをクリア
      this.timelines = [];
      for (var i = 0; i < this.api_data_list.length; i++) {
        let api_data = this.api_data_list[i];
        if (item.transaction_id == api_data.transaction_id) {
          let timeline = {
            seq_api_communication_data_id:
              api_data.seq_api_communication_data_id,
            api_name: api_data.api_name,
            http_status: api_data.http_status,
            request_date: api_data.request_date,
            response_date: api_data.response_date,
            color: this.getHttpStatusColor(api_data.http_status),
          };
          this.timelines.push(timeline);
        }
      }
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
.pushable {
  cursor: pointer;
}
.fade-enter-active,
.fade-leave-active {
  transition: 0.8s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
  transform: scale(0);
}
</style>