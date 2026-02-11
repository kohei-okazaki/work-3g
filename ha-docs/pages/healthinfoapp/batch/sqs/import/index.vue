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
          text: "AWS SQS取込バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/sqs/import",
        },
      ],
      flow: [
        {
          id: "1",
          text: "AWS SQS取込バッチJavaを呼び出す",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "API_LOG用キュー名を取得",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "AWS SQSからメッセージを取得",
          edgeType: "round",
          link: ["-- キュー情報の取得に失敗した場合 -->", "-- キュー情報が存在しない場合 -->", "-- キュー情報が存在する場合 -->"],
          next: ["101", "100", "4"],
        },
        {
          id: "4",
          text: "API_LOG 登録",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "AWS SQSからキュー情報を削除",
          edgeType: "round",
          next: ["100"],
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
