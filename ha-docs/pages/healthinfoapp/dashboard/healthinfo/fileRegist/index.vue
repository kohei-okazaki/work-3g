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
  // 健康管理Dashboardのレイアウトを適用
  layout: "healthinfoappDashboardLayout",
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
          text: "健康管理ダッシュボード",
          disabled: false,
          href: "/healthinfoapp/dashboard",
        },
        {
          text: "健康情報一括登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/fileRegist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "健康情報一括登録画面を表示",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "CSVを選択してアップロード",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "アップロードファイルの必須検証",
          edgeType: "diamond",
          next: ["a_input_error", "a3"],
        },
        {
          id: "a_input_error",
          text: "未指定: 入力画面にエラー表示",
          edgeType: "round",
        },
        {
          id: "a3",
          text: "ユーザIDを取得<br/>CSVを読込み・列数を検証",
          edgeType: "round",
          next: ["a_error", "a4"],
        },
        {
          id: "a4",
          text: "CSVをS3にアップロード",
          edgeType: "round",
          next: ["a_error", "a5"],
        },
        {
          id: "a5",
          text: "各行の必須・数値形式を検証<br/>CSVのユーザIDと本人の一致を確認",
          edgeType: "diamond",
          next: ["a_error", "a6"],
        },
        {
          id: "a6",
          text: "アップロード名をセッションに保存<br/>確認画面に内容・件数を設定",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a7",
          text: "二重送信防止情報を発行<br/>確認画面を表示",
          edgeType: "circle",
        },
        {
          id: "a_error",
          text: "読込・保存・内容の異常<br/>共通エラー処理へ",
          edgeType: "round",
        },
        {
          id: "b0",
          text: "確認画面で確定",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "二重送信防止情報を確認<br/>ユーザIDと保存したファイル名を取得",
          edgeType: "round",
          next: ["b_error", "b2"],
        },
        {
          id: "b2",
          text: "S3からCSVを再取得<br/>データがあることを確認",
          edgeType: "round",
          next: ["b_error", "b3"],
        },
        {
          id: "b3",
          text: "USER検索・APIキー取得<br/>USER.SEQ_USER_ID = セッション.ユーザID",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "1行ごとに通信を識別するIDを採番",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "健康情報登録APIを呼び出す",
          edgeType: "round",
          next: ["b6"],
          url: "/healthinfoapp/api/healthinfo/regist",
        },
        {
          id: "b6",
          text: "通信ログをSQS送信",
          edgeType: "round",
          next: ["b_error", "b7"],
        },
        {
          id: "b7",
          text: "登録結果を集約<br/>失敗結果があっても残行を続行",
          edgeType: "round",
          next: ["b8"],
        },
        {
          id: "b8",
          text: "未処理の行があるか",
          edgeType: "diamond",
          next: ["b4", "b9"],
        },
        {
          id: "b9",
          text: "全行のAPI結果が成功か",
          edgeType: "diamond",
          next: ["b10", "b11"],
        },
        {
          id: "b10",
          text: "全件成功: 保存したファイル名を削除<br/>S3へ削除を要求",
          edgeType: "round",
          next: ["b_error", "b11"],
        },
        {
          id: "b11",
          text: "二重送信防止情報を破棄<br/>失敗結果があっても完了画面を表示",
          edgeType: "circle",
        },
        {
          id: "b_error",
          text: "保存情報なし・読込やSQS処理等の異常<br/>共通エラー処理へ",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>