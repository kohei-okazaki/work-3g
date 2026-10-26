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
          text: "健康情報登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/regist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "健康情報登録画面を表示",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "身長・体重を入力して確認",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "必須・数値を検証<br/>身長・体重は1以上300以下",
          edgeType: "diamond",
          next: ["a_error", "a3"],
        },
        {
          id: "a_error",
          text: "不備あり: 入力画面にエラー表示",
          edgeType: "round",
        },
        {
          id: "a3",
          text: "入力内容をセッションに保存",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "二重送信防止情報を発行<br/>確認画面を表示",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "確認画面で確定",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "二重送信防止情報を確認<br/>保存した入力内容・ユーザIDを取得",
          edgeType: "round",
          next: ["b_error", "b2"],
        },
        {
          id: "b2",
          text: "HEALTH_INFO件数を取得し過去登録の有無を判定<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID",
          edgeType: "diamond",
          next: ["b3", "b4"],
        },
        {
          id: "b3",
          text: "過去の登録あり: HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>ORDER BY HEALTH_INFO.SEQ_HEALTH_INFO_ID DESC<br/>LIMIT 1<br/>取得した記録と体重差・増減を判定",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "USER検索・APIキー取得<br/>USER.SEQ_USER_ID = セッション.ユーザID<br/>通信を識別するIDを採番",
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
          text: "登録APIの結果が成功か",
          edgeType: "diamond",
          next: ["b_error", "b8"],
        },
        {
          id: "b8",
          text: "USER検索・メールアドレス取得<br/>USER.SEQ_USER_ID = API応答.アカウント.ユーザID<br/>登録完了メールを送信",
          edgeType: "round",
          next: ["b_error", "b9"],
        },
        {
          id: "b9",
          text: "登録結果を画面・セッションに設定",
          edgeType: "round",
          next: ["b10"],
        },
        {
          id: "b10",
          text: "二重送信防止情報を破棄<br/>登録完了画面を表示",
          edgeType: "circle",
        },
        {
          id: "b_error",
          text: "保存情報なし・登録失敗等<br/>共通エラー処理へ",
          edgeType: "round",
        },
        {
          id: "c0",
          text: "完了画面でCSVまたはExcelを選択",
          edgeType: "circle",
          next: ["c1"],
        },
        {
          id: "c1",
          text: "セッションからユーザIDと<br/>登録した健康情報IDを取得",
          edgeType: "round",
          next: ["c_error", "c2"],
        },
        {
          id: "c2",
          text: "HEALTH_INFO再検索・対象なしはエラー<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>セッション.登録結果.健康情報ID",
          edgeType: "round",
          next: ["c_error", "c3"],
        },
        {
          id: "c3",
          text: "HEALTH_INFO_FILE_SETTING検索<br/>HEALTH_INFO_FILE_SETTING.SEQ_USER_ID =<br/>セッション.ユーザID",
          edgeType: "round",
          next: ["c4"],
        },
        {
          id: "c4",
          text: "選択した形式で出力",
          edgeType: "diamond",
          next: ["c5", "c6"],
        },
        {
          id: "c5",
          text: "CSVを生成してダウンロード",
          edgeType: "round",
        },
        {
          id: "c6",
          text: "Excelを生成してダウンロード",
          edgeType: "round",
        },
        {
          id: "c_error",
          text: "保存情報なし・対象なし<br/>共通エラー処理へ",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>