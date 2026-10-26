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
          text: "管理ユーザ取得API",
          disabled: true,
          href: "/root/api/user",
        },
      ],
      flow: [
        {
          id: "start",
          text: "管理者ユーザ取得リクエスト受付",
          edgeType: "round",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "JWTの署名・有効期限を確認",
          edgeType: "diamond",
          next: ["authenticated","auth_error"],
        },
        {
          id: "authenticated",
          text: "認証成功：ROOT_LOGIN_INFOと権限情報を内部結合して検索<br/>ROOT_LOGIN_INFO.SEQ_ROOT_LOGIN_INFO_ID = パス.ログインID<br/>ROOT_LOGIN_INFO.SEQ_ROOT_USER_ROLE_MNG_MT_ID =<br/>ROOT_USER_ROLE_MNG_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID<br/>ROOT_USER_ROLE_MNG_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID =<br/>ROOT_USER_ROLE_DETAIL_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID<br/>ROOT_USER_ROLE_DETAIL_MT.SEQ_ROOT_ROLE_MT_ID =<br/>ROOT_ROLE_MT.SEQ_ROOT_ROLE_MT_ID<br/>ROOT_ROLE_MT.SEQ_ROOT_ROLE_MT_ID 昇順",
          edgeType: "round",
          next: ["exists","server_error"],
        },
        {
          id: "exists",
          text: "検索成功：対象データが存在するか",
          edgeType: "diamond",
          next: ["response","not_found"],
        },
        {
          id: "response",
          text: "存在する：ログインID・権限一覧・<br/>削除状態・有効期限・備考を設定",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "正常レスポンス返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "not_found",
          text: "存在しない：HTTP 400<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "auth_error",
          text: "認証不成立：認証エラーで終了",
          edgeType: "round",
        },
        {
          id: "server_error",
          text: "処理例外：Slack通知<br/>HTTP 500・result: #quot;1#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
