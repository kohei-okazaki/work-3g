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
          text: "パスワード再設定",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/password",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "再設定メールの入力画面を表示",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "メールアドレスを入力して送信を押下",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "メールアドレスの必須・長さ・形式を検証",
          edgeType: "diamond",
          next: ["a3", "a4"],
        },
        {
          id: "a3",
          text: "入力不正：同じ画面を返す",
          edgeType: "circle",
        },
        {
          id: "a4",
          text: "入力正常: USER検索<br/>USER.MAIL_ADDRESS =<br/>AES暗号化した画面入力.メールアドレス<br/>USER.DELETE_FLAG = false",
          edgeType: "diamond",
          next: ["a5", "a6"],
        },
        {
          id: "a5",
          text: "未登録：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "a6",
          text: "登録あり: 再設定トークンを生成<br/>USER_RECOVERY_TOKENに保存",
          edgeType: "round",
          next: ["a7", "a9"],
        },
        {
          id: "a7",
          text: "再設定URLをメール送信",
          edgeType: "round",
          next: ["a8", "a9"],
        },
        {
          id: "a8",
          text: "送信完了メッセージを表示",
          edgeType: "circle",
        },
        {
          id: "a9",
          text: "保存・送信時に例外：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "メール内の再設定URLを開く",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "トークンの指定・ユーザIDの形式を確認",
          edgeType: "diamond",
          next: ["b2", "b3"],
        },
        {
          id: "b2",
          text: "未指定・形式不正・ユーザなし<br/>共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b3",
          text: "形式正常: USER検索<br/>USER.SEQ_USER_ID = URLパス.ユーザID",
          edgeType: "diamond",
          next: ["b2", "b4"],
        },
        {
          id: "b4",
          text: "ユーザあり: USER_RECOVERY_TOKEN検索<br/>USER_RECOVERY_TOKEN.SEQ_USER_ID =<br/>取得USER.SEQ_USER_ID<br/>USER_RECOVERY_TOKEN.TOKEN = URLクエリ.トークン<br/>USER_RECOVERY_TOKEN.TOKEN_CREATE_DATE #gt;<br/>システム日時の1時間前",
          edgeType: "diamond",
          next: ["b5", "b6"],
        },
        {
          id: "b5",
          text: "トークン不一致・期限切れ：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b6",
          text: "有効：パスワード再設定入力画面を表示",
          edgeType: "circle",
        },
        {
          id: "c0",
          text: "新しいパスワードを入力して確認を押下",
          edgeType: "circle",
          next: ["c1"],
        },
        {
          id: "c1",
          text: "必須・形式・長さと<br/>確認用パスワードの一致を検証",
          edgeType: "diamond",
          next: ["c2", "c3"],
        },
        {
          id: "c2",
          text: "入力不正・不一致：入力画面を返す",
          edgeType: "circle",
        },
        {
          id: "c3",
          text: "入力正常: ユーザIDの形式を確認してUSER検索<br/>USER.SEQ_USER_ID = URLパス.ユーザID",
          edgeType: "diamond",
          next: ["c4", "c5"],
        },
        {
          id: "c4",
          text: "ユーザ不正：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "c5",
          text: "ユーザあり：確認画面を表示<br/>二重送信防止情報を発行",
          edgeType: "circle",
        },
        {
          id: "d0",
          text: "パスワード再設定確認画面",
          edgeType: "circle",
          next: ["d1", "d2"],
        },
        {
          id: "d1",
          text: "戻る：ブラウザ履歴で入力画面へ",
          edgeType: "circle",
        },
        {
          id: "d2",
          text: "確定：二重送信防止情報を確認",
          edgeType: "diamond",
          next: ["d3", "d4"],
        },
        {
          id: "d3",
          text: "手順不正・処理例外：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "d4",
          text: "保持あり: ユーザIDの形式を確認してUSER検索<br/>USER.SEQ_USER_ID = URLパス.ユーザID",
          edgeType: "diamond",
          next: ["d3", "d5"],
        },
        {
          id: "d5",
          text: "ユーザあり：新パスワードと<br/>メールアドレスからハッシュ値を作成",
          edgeType: "round",
          next: ["d6", "d3"],
        },
        {
          id: "d6",
          text: "USER.PASSWORDを更新<br/>USER.SEQ_USER_ID = 取得USER.SEQ_USER_ID",
          edgeType: "round",
          next: ["d7", "d3"],
        },
        {
          id: "d7",
          text: "二重送信防止情報を削除<br/>ログイン画面へ転送し完了メッセージを表示",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>