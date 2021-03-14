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
          text: "API通信情報 登録",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "ヘルスチェックAPI 実行",
          edgeType: "round",
          url: "/healthinfoapp/api/healthcheck",
          next: ["4"],
        },
        {
          id: "4",
          text: "API通信情報 更新",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "API処理結果判定",
          edgeType: "round",
          link: ["-- API処理結果 == 0 -->", "-- API処理結果 <> 0 -->"],
          next: ["6.1", "6.2"],
        },
        {
          id: "6.1",
          text: "Slack 通知",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "6.2",
          text: "メール 通知",
          edgeType: "round",
          link: ["-- 通知 失敗 -->", "-- 通知 成功 -->"],
          next: ["101", "7"],
        },
        {
          id: "7",
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