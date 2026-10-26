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
          text: "管理ユーザ作成API",
          disabled: true,
          href: "/root/api/user/entry",
        },
      ],
      flow: [
        {
          id: "start",
          text: "管理者ユーザ登録リクエスト受付",
          edgeType: "round",
          next: ["validate"],
        },
        {
          id: "validate",
          text: "パスワード・確認用パスワードの<br/>必須チェック",
          edgeType: "diamond",
          next: ["match","input_error"],
        },
        {
          id: "match",
          text: "入力正常：パスワードと<br/>確認用パスワードを照合",
          edgeType: "diamond",
          next: ["transaction","mismatch"],
        },
        {
          id: "transaction",
          text: "一致：トランザクション開始",
          edgeType: "round",
          next: ["role"],
        },
        {
          id: "role",
          text: "ROOT_ROLE_MT全件取得・有効キャッシュがあれば再利用<br/>取得後にROOT_ROLE_MT.ROLE = #quot;01#quot;の先頭を選択",
          edgeType: "round",
          next: ["register","rollback"],
        },
        {
          id: "register",
          text: "ROOT_USER_ROLE_MNG_MT・ROOT_USER_ROLE_DETAIL_MT登録<br/>ROOT_LOGIN_INFO登録：パスワードはハッシュ化<br/>ROOT_LOGIN_INFO.PASSWORD_EXPIRE = 当日の6か月後<br/>ROOT_LOGIN_INFO.DELETE_FLAG = false",
          edgeType: "round",
          next: ["commit","rollback"],
        },
        {
          id: "commit",
          text: "登録成功：コミット",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "発行したログインIDを設定して返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "rollback",
          text: "登録例外：Slack通知・ロールバック",
          edgeType: "round",
          next: ["update_error"],
        },
        {
          id: "update_error",
          text: "登録失敗：HTTP 400<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "mismatch",
          text: "不一致：HTTP 400<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "input_error",
          text: "入力不正：Slack通知<br/>HTTP 400・result: #quot;1#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
