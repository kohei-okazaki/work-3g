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
          text: "アカウント情報編集API",
          disabled: true,
          href: "/root/api/account/edit",
        },
      ],
      flow: [
        {
          id: "start",
          text: "リクエスト受付",
          edgeType: "circle",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "認証トークンの署名・有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "validate"],
        },
        {
          id: "auth_error",
          text: "認証不可：処理終了",
          edgeType: "circle",
        },
        {
          id: "validate",
          text: "認証成功：メールアドレスの必須・形式と<br/>有効期限の必須・未来日を検証",
          edgeType: "diamond",
          next: ["bad_request", "exists"],
        },
        {
          id: "bad_request",
          text: "入力不正：Slack通知・ログ出力<br/>HTTP 400／result: #quot;1#quot;",
          edgeType: "circle",
        },
        {
          id: "exists",
          text: "USER検索<br/>USER.SEQ_USER_ID = パス.seq_user_id<br/>USER.DELETE_FLAG条件なし",
          edgeType: "diamond",
          next: ["not_found", "update", "exception"],
        },
        {
          id: "not_found",
          text: "ユーザなし：HTTP 400でエラー返却<br/>result: #quot;1#quot;",
          edgeType: "circle",
        },
        {
          id: "update",
          text: "USER更新<br/>USER.SEQ_USER_ID = パス.seq_user_id<br/>USER.MAIL_ADDRESS = ボディ.mail_addressを暗号化<br/>USER.PASSWORD_EXPIRE = ボディ.password_expire<br/>USER.UPDATE_DATE = システム日時",
          edgeType: "round",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "例外なし：HTTP 200でJSON返却<br/>result: #quot;0#quot;",
          edgeType: "circle",
        },
        {
          id: "exception",
          text: "検索・更新で例外：Slack通知・ログ出力",
          edgeType: "round",
          next: ["server_error"],
        },
        {
          id: "server_error",
          text: "HTTP 500でエラーJSON返却<br/>result: #quot;1#quot;",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
