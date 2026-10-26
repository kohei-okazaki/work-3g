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
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
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
          text: "管理者サイト用API",
          disabled: false,
          href: "/root/api",
        },
        {
          text: "ログインAPI",
          disabled: true,
          href: "/root/api/login",
        },
      ],
      flow: [
        {
          id: "start",
          text: "ログインリクエスト受付",
          edgeType: "round",
          next: ["read"],
        },
        {
          id: "read",
          text: "本文からログインID・パスワードを取得",
          edgeType: "round",
          next: ["validate","read_error"],
        },
        {
          id: "validate",
          text: "読込成功：ログインIDの<br/>必須・半角数字チェック",
          edgeType: "diamond",
          next: ["search","auth_error"],
        },
        {
          id: "search",
          text: "ID正常：ROOT_LOGIN_INFO検索<br/>ROOT_LOGIN_INFO.SEQ_ROOT_LOGIN_INFO_ID = 本文.ログインID",
          edgeType: "round",
          next: ["account"],
        },
        {
          id: "account",
          text: "取得後にユーザの存在と有効状態を確認<br/>ROOT_LOGIN_INFO.DELETE_FLAG = false<br/>ROOT_LOGIN_INFO.PASSWORD_EXPIRE ≧ 当日",
          edgeType: "diamond",
          next: ["password","auth_error"],
        },
        {
          id: "password",
          text: "ユーザ有効：本文.パスワードをハッシュ化<br/>ROOT_LOGIN_INFO.PASSWORDと照合",
          edgeType: "diamond",
          next: ["token","auth_error"],
        },
        {
          id: "token",
          text: "照合一致：有効期間8時間のJWTを生成",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "Authorizationヘッダーに<br/>Bearerトークンを設定して返却",
          edgeType: "round",
        },
        {
          id: "auth_error",
          text: "認証不成立：認証エラーで終了",
          edgeType: "round",
        },
        {
          id: "read_error",
          text: "本文読込失敗：処理異常で終了",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
