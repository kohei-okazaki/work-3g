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
          text: "管理ユーザ編集API",
          disabled: true,
          href: "/root/api/user/edit",
        },
      ],
      flow: [
        {
          id: "start",
          text: "管理者ユーザ編集リクエスト受付",
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
          text: "認証成功：必須項目・有効期限・<br/>備考の長さをチェック",
          edgeType: "diamond",
          next: ["search","input_error"],
        },
        {
          id: "search",
          text: "入力正常：ROOT_LOGIN_INFOと権限情報を内部結合して検索<br/>ROOT_LOGIN_INFO.SEQ_ROOT_LOGIN_INFO_ID = パス.ログインID<br/>ROOT_LOGIN_INFO.SEQ_ROOT_USER_ROLE_MNG_MT_ID =<br/>ROOT_USER_ROLE_MNG_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID<br/>ROOT_USER_ROLE_MNG_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID =<br/>ROOT_USER_ROLE_DETAIL_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID<br/>ROOT_USER_ROLE_DETAIL_MT.SEQ_ROOT_ROLE_MT_ID =<br/>ROOT_ROLE_MT.SEQ_ROOT_ROLE_MT_ID<br/>ROOT_ROLE_MT.SEQ_ROOT_ROLE_MT_ID 昇順",
          edgeType: "round",
          next: ["exists","server_error"],
        },
        {
          id: "exists",
          text: "検索成功：対象データが存在するか",
          edgeType: "diamond",
          next: ["transaction","not_found"],
        },
        {
          id: "transaction",
          text: "存在する：トランザクション開始",
          edgeType: "round",
          next: ["delete_roles"],
        },
        {
          id: "delete_roles",
          text: "ROOT_USER_ROLE_DETAIL_MTの既存権限を削除<br/>ROOT_USER_ROLE_DETAIL_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID =<br/>取得したROOT_USER_ROLE_MNG_MT.SEQ_ROOT_USER_ROLE_MNG_MT_ID",
          edgeType: "round",
          next: ["roles","rollback"],
        },
        {
          id: "roles",
          text: "ROOT_ROLE_MT全件取得・有効キャッシュがあれば再利用<br/>取得後にROOT_ROLE_MT.ROLEが本文.権限一覧に含まれる行を選択",
          edgeType: "round",
          next: ["update","rollback"],
        },
        {
          id: "update",
          text: "取得した権限をROOT_USER_ROLE_DETAIL_MTに登録<br/>ROOT_LOGIN_INFOをログインID条件で部分更新<br/>ROOT_LOGIN_INFO.SEQ_ROOT_LOGIN_INFO_ID = パス.ログインID<br/>有効期限・備考・削除状態を更新<br/>パスワード指定時はハッシュ化して更新",
          edgeType: "round",
          next: ["commit","rollback"],
        },
        {
          id: "commit",
          text: "更新成功：コミット",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "正常レスポンス返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "rollback",
          text: "更新例外：Slack通知・ロールバック",
          edgeType: "round",
          next: ["update_error"],
        },
        {
          id: "update_error",
          text: "更新失敗：HTTP 400<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "not_found",
          text: "存在しない：HTTP 400<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "input_error",
          text: "入力不正：Slack通知<br/>HTTP 400・result: #quot;1#quot;",
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
