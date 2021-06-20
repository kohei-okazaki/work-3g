<template>
  <div>
    <v-row justify="center">
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppDocs :flow="flow" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppDocs from "~/components/AppDocs.vue";

export default {
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppDocs,
  },
  data: function () {
    return {
      breadcrumbs: [
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "健康管理バッチ",
          disabled: false,
          href: "/healthinfoapp/batch",
        },
        {
          text: "ヘルスチェックバッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthcheck",
        },
      ],
      flow: [
        {
          id: "1",
          text: "ヘルスチェックバッチJavaを呼び出す",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "トランザクションID 採番",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "API通信情報 登録<br>健康管理API:ヘルスチェックAPI",
          edgeType: "round",
          next: ["4"],
        },
        {
          id: "4",
          text: "健康管理API:ヘルスチェックAPI 実行",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "API通信情報 更新<br>健康管理API:ヘルスチェックAPI",
          edgeType: "round",
          link: [
            "-- APIが正常終了した場合 -->",
            "-- APIが異常終了した場合 -->",
          ],
          next: ["5.1", "5.2"],
        },
        {
          id: "5.1",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "6"],
        },
        {
          id: "5.2",
          text: "メール 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "5.3"],
        },
        {
          id: "5.3",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "6"],
        },

        {
          id: "6",
          text: "API通信情報 登録<br>NodeAPI:ヘルスチェックAPI",
          edgeType: "round",
          next: ["7"],
        },
        {
          id: "7",
          text: "NodeAPI:ヘルスチェックAPI 実行",
          edgeType: "round",
          next: ["8"],
        },
        {
          id: "8",
          text: "API通信情報 更新<br>NodeAPI:ヘルスチェックAPI",
          edgeType: "round",
          link: [
            "-- APIが正常終了した場合 -->",
            "-- APIが異常終了した場合 -->",
          ],
          next: ["8.1", "8.2"],
        },
        {
          id: "8.1",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "9"],
        },
        {
          id: "8.2",
          text: "メール 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "8.3"],
        },
        {
          id: "8.3",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "9"],
        },

        {
          id: "9",
          text: "API通信情報 登録<br>RootAPI:ヘルスチェックAPI",
          edgeType: "round",
          next: ["10"],
        },
        {
          id: "10",
          text: "RootAPI:ヘルスチェックAPI 実行",
          edgeType: "round",
          next: ["11"],
        },
        {
          id: "11",
          text: "API通信情報 更新<br>RootAPI:ヘルスチェックAPI",
          edgeType: "round",
          link: [
            "-- APIが正常終了した場合 -->",
            "-- APIが異常終了した場合 -->",
          ],
          next: ["11.1", "11.2"],
        },
        {
          id: "11.1",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "100"],
        },
        {
          id: "11.2",
          text: "メール 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "11.3"],
        },
        {
          id: "11.3",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "100"],
        },

        {
          id: "100",
          text: "バッチ正常ステータスを設定し<br>Shellに処理結果を返却",
          edgeType: "round",
        },
        {
          id: "101",
          text: "バッチ異常ステータスを設定し<br>Shellに処理結果を返却",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>