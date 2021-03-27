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
          text: "月次健康情報集計バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthinfo/monthlySummary",
        },
      ],
      flow: [
        {
          id: "1",
          text: "月次健康情報集計バッチJavaを呼び出す",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "処理対象年月 取得",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "処理対象年月 妥当性チェック",
          edgeType: "round",
          link: ["-- 日付が未指定<br>または日付形式でない場合 -->", "-- それ以外の場合 -->"],
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
          text: "健康情報CSV 作成",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "S3 Upload",
          edgeType: "round",
          link: ["-- Upload 失敗 -->", "-- Upload 成功 -->"],
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