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
          text: "アカウント登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/regist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "登録入力画面を表示",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "必要事項を入力して確認を押下",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "必須・形式・日付・範囲を検証",
          edgeType: "diamond",
          next: ["a3", "a4"],
        },
        {
          id: "a3",
          text: "入力不正：エラー付きで入力画面を再表示",
          edgeType: "circle",
        },
        {
          id: "a4",
          text: "入力正常: USER件数取得で重複確認<br/>USER.MAIL_ADDRESS =<br/>AES暗号化した画面入力.メールアドレス<br/>USER.DELETE_FLAG = false",
          edgeType: "diamond",
          next: ["a5", "a6"],
        },
        {
          id: "a5",
          text: "登録済み：エラー付きで入力画面を再表示",
          edgeType: "circle",
        },
        {
          id: "a6",
          text: "未登録：入力内容をセッションに保存<br/>二重送信防止情報を発行",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a7",
          text: "登録確認画面を表示",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "登録確認画面",
          edgeType: "circle",
          next: ["b1", "b2"],
        },
        {
          id: "b1",
          text: "戻る：ブラウザ履歴で入力画面へ",
          edgeType: "circle",
        },
        {
          id: "b2",
          text: "確定：二重送信防止情報と<br/>セッションの入力内容を確認",
          edgeType: "diamond",
          next: ["b3", "b4"],
        },
        {
          id: "b3",
          text: "手順不正・入力内容なし：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b4",
          text: "保持あり：パスワードをハッシュ化<br/>パスワード有効期限を6か月後に設定<br/>APIキーを発行",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "USER・HEALTH_INFO_FILE_SETTING・<br/>USER_HEALTH_GOALを同じトランザクションで登録",
          edgeType: "round",
          next: ["b6"],
        },
        {
          id: "b6",
          text: "登録処理の結果",
          edgeType: "diamond",
          next: ["b7", "b8"],
        },
        {
          id: "b7",
          text: "登録例外：全件を取り消して<br/>共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b8",
          text: "登録成功：確定して<br/>セッションの入力内容を削除",
          edgeType: "round",
          next: ["b9"],
        },
        {
          id: "b9",
          text: "登録完了画面を表示",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
