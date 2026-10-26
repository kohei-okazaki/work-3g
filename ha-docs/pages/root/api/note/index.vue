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
          text: "メモ情報一覧取得API",
          disabled: true,
          href: "/root/api/note",
        },
      ],
      flow: [
        {
          id: "start",
          text: "メモ一覧取得リクエスト受付",
          edgeType: "round",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "JWTの署名・有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "input"],
        },
        {
          id: "auth_error",
          text: "認証不可: 処理終了",
          edgeType: "round",
        },
        {
          id: "input",
          text: "認証成功: ログインIDの必須・型と<br/>ページ番号の型・範囲を検証",
          edgeType: "diamond",
          next: ["invalid", "query"],
        },
        {
          id: "query",
          text: "ROOT_USER_NOTE_INFO検索<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_LOGIN_INFO_ID =<br/>クエリ.seq_login_id<br/>AND ROOT_USER_NOTE_INFO.DELETE_FLAG = false<br/>ORDER BY<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_USER_NOTE_INFO_ID DESC<br/>指定ページの開始位置・設定件数で取得",
          edgeType: "round",
          next: ["details"],
        },
        {
          id: "details",
          text: "ROOT_USER_NOTE_INFO.S3_KEYをキーに本文をS3取得<br/>件名・本文などを一覧に設定",
          edgeType: "round",
          next: ["failure", "paging"],
        },
        {
          id: "paging",
          text: "ROOT_USER_NOTE_INFOの全件数でページ情報を作成<br/>WHERE条件なし<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_LOGIN_INFO_ID・<br/>ROOT_USER_NOTE_INFO.DELETE_FLAGによる絞込なし",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "メモ一覧・ページングをJSON返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "invalid",
          text: "入力不正: Slackに例外通知<br/>HTTP 400・result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "failure",
          text: "DB・S3等の処理例外: Slackに通知<br/>HTTP 500・result: #quot;1#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
