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
          text: "メモ情報削除API",
          disabled: true,
          href: "/root/api/note/delete",
        },
      ],
      flow: [
        {
          id: "start",
          text: "メモ削除リクエスト受付",
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
          text: "認証成功: メモIDの型を確認",
          edgeType: "diamond",
          next: ["invalid", "find"],
        },
        {
          id: "find",
          text: "ROOT_USER_NOTE_INFO検索<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_USER_NOTE_INFO_ID =<br/>パス.seq_root_user_note_info_id<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_LOGIN_INFO_ID・<br/>ROOT_USER_NOTE_INFO.DELETE_FLAG条件なし",
          edgeType: "diamond",
          next: ["missing", "delete"],
        },
        {
          id: "missing",
          text: "対象なし: メモ未存在を返却<br/>HTTP 400・result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "delete",
          text: "ROOT_USER_NOTE_INFO更新<br/>ROOT_USER_NOTE_INFO.SEQ_ROOT_USER_NOTE_INFO_ID =<br/>パス.seq_root_user_note_info_id<br/>ROOT_USER_NOTE_INFO.DELETE_FLAG = trueに更新",
          edgeType: "round",
          next: ["failure", "notice"],
        },
        {
          id: "notice",
          text: "メモの削除をSlack通知",
          edgeType: "round",
          next: ["failure", "success"],
        },
        {
          id: "success",
          text: "削除完了のJSONを返却<br/>HTTP 200・result: #quot;0#quot;",
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
