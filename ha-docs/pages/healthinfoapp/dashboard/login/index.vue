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
          text: "ログイン",
          disabled: true,
          href: "/healthinfoapp/dashboard/login",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "ログイン画面表示要求",
          edgeType: "round",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "ログアウト・認証エラーの<br/>メッセージを設定",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "ログイン画面表示",
          edgeType: "circle",
          next: ["b1", "c1", "d1"],
        },
        {
          id: "b1",
          text: "ログインボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "画面側の必須・形式チェック",
          edgeType: "diamond",
          next: ["b3", "b4"],
        },
        {
          id: "b3",
          text: "入力不正：ログイン画面でエラー表示",
          edgeType: "circle",
        },
        {
          id: "b4",
          text: "入力正常: USER検索<br/>USER.MAIL_ADDRESS =<br/>AES暗号化した画面入力.メールアドレス<br/>USER.DELETE_FLAG = false",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "ユーザ存在・パスワード一致を確認<br/>削除状態・パスワード有効期限を確認",
          edgeType: "diamond",
          next: ["b6", "b8"],
        },
        {
          id: "b6",
          text: "認証失敗：エラーメッセージを保存",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "ログイン画面へ戻りエラー表示",
          edgeType: "circle",
        },
        {
          id: "b8",
          text: "認証成功：セッションにユーザIDを保存",
          edgeType: "round",
          next: ["b9"],
        },
        {
          id: "b9",
          text: "TOP画面へ遷移",
          edgeType: "circle",
        },
        {
          id: "c1",
          text: "ユーザ作成ボタン押下",
          edgeType: "round",
          next: ["c2"],
        },
        {
          id: "c2",
          text: "アカウント登録画面へ遷移",
          edgeType: "circle",
        },
        {
          id: "d1",
          text: "パスワードを忘れた場合のリンク押下",
          edgeType: "round",
          next: ["d2"],
        },
        {
          id: "d2",
          text: "パスワード再設定メールの<br/>送信画面へ遷移",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
