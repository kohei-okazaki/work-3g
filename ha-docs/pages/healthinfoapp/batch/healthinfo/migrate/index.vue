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
          text: "健康情報連携バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthinfo/migrate",
        },
      ],
      flow: [
        {
          id: "1",
          text: "健康情報連携バッチJavaを呼び出す",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "処理対象日 取得",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "処理対象日 妥当性チェック",
          edgeType: "round",
          link: ["-- 日付形式でない場合 -->", "-- それ以外の場合 -->"],
          next: ["101", "4"],
        },
        {
          id: "4",
          text: "健康情報 検索",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "トランザクションID 採番",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "API通信情報 登録<br>Track API:健康情報連携API",
          edgeType: "round",
          next: ["7"],
        },
        {
          id: "7",
          text: "Track API:健康情報連携API",
          edgeType: "round",
          next: ["8"],
        },
        {
          id: "8",
          text: "API通信情報 更新<br>Track API:健康情報連携API",
          edgeType: "round",
          next: ["9"],
        },

        {
          id: "9",
          text: "Slack 通知",
          edgeType: "round",
          link: ["-- API異常終了 -->", "-- API正常終了 -->"],
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

<style scoped></style>
