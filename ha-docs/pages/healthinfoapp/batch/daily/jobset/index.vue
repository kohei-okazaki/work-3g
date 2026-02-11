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
          text: "日次データ分析連携JobSet",
          disabled: true,
          href: "/healthinfoapp/batch/daily/jobset",
        },
      ],
      flow: [
        {
          id: "1",
          text: "日次健康情報データ分析連携バッチを呼び出す",
          edgeType: "round",
          next: ["101", "2"],
        },
        {
          id: "2",
          text: "日次ユーザ情報データ分析連携バッチを呼び出す",
          edgeType: "round",
          next: ["101", "3"],
        },
        {
          id: "3",
          text: "日次API通信ログデータ分析連携バッチを呼び出す",
          edgeType: "round",
          next: ["101", "4"],
        },
        {
          id: "4",
          text: "日次バッチ実行ログデータ分析連携バッチを呼び出す",
          edgeType: "round",
          next: ["101", "100"],
        },
        {
          id: "100",
          text: "バッチ正常ステータスを設定し<br>Shellに処理結果を返却",
          edgeType: "round",
        },
        {
          id: "101",
          text: "失敗したバッチ名をログ出力する",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped></style>