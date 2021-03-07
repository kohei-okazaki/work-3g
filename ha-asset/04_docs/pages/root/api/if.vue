<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />

    <v-row justify="center" align="center">
      <v-col cols="12" sm="10" md="10">
        <AppBaseIF :ifs="ifs" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppBaseIF from "~/components/AppBaseIF.vue";

export default {
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppBaseIF,
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
          text: "IF一覧",
          disabled: true,
          href: "/root/api/if",
        },
      ],
      ifs: [
        {
          name: "ログインAPI",
          endpoint: "/root/api/login",
          httpMethod: "POST",
          description: "管理者サイトへログインするためのAPI",
          request: [
            {
              physicalName: "seq_login_id",
              logicalName: "ログインID",
              required: true,
              type: "半角数字",
              byte: null,
              description: null,
            },
            {
              physicalName: "password",
              logicalName: "パスワード",
              required: true,
              type: null,
              byte: null,
              description: null,
            },
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "半角数字",
              byte: 1,
              description:
                "処理結果<ul><li>0:正常終了</li><li>1:異常終了</li></ul>",
            },
            {
              physicalName: "error",
              logicalName: "エラー情報",
              required: false,
              type: null,
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "　message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "token",
              logicalName: "トークン",
              required: false,
              type: "半角英数記号",
              byte: null,
              description: "result='0'の場合、必須",
            },
          ],
        },

        {
          name: "Top情報取得API",
          endpoint: "/api/root/top",
          httpMethod: "GET",
          description: "管理サイトTop画面情報の取得を行うAPI",
          request: [
            {
              physicalName: "date",
              logicalName: "対象年月",
              required: false,
              type: "YYYYMM",
              byte: null,
              description: "取得対象のTOP情報の年月を指定",
            },
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "半角数字",
              byte: 1,
              description:
                "処理結果<ul><li>0:正常終了</li><li>1:異常終了</li></ul>",
            },
            {
              physicalName: "error",
              logicalName: "エラー情報",
              required: false,
              type: null,
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "　message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "health_info_reg_graph_list",
              logicalName: "健康情報登録グラフ情報リスト",
              required: false,
              type: "配列",
              byte: null,
              description: "result='0' かつ データが存在する場合、必須",
            },
            {
              physicalName: "　count",
              logicalName: "登録件数",
              required: false,
              type: "半角数字",
              byte: null,
              description: "該当年月の登録件数",
            },
            {
              physicalName: "　date",
              logicalName: "登録日",
              required: false,
              type: "YYYY/MM/DD",
              byte: null,
              description: "該当年月日",
            },
            {
              physicalName: "account_reg_graph_list",
              logicalName: "アカウント登録グラフ情報リスト",
              required: false,
              type: "配列",
              byte: null,
              description: "result='0' かつ データが存在する場合、必須",
            },
            {
              physicalName: "　count",
              logicalName: "登録件数",
              required: false,
              type: "半角数字",
              byte: null,
              description: "該当年月の登録件数",
            },
            {
              physicalName: "　date",
              logicalName: "登録日",
              required: false,
              type: "YYYY/MM/DD",
              byte: null,
              description: "該当年月日",
            },
          ],
        },

        {
          name: "Top情報(健康情報)取得API",
          endpoint: "/api/root/top/healthinfo",
          httpMethod: "GET",
          description: "管理サイトTop画面情報の取得を行うAPI",
          request: [
            {
              physicalName: "date",
              logicalName: "対象年月",
              required: false,
              type: "YYYYMM",
              byte: null,
              description: "取得対象のTOP情報の年月を指定",
            },
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "半角数字",
              byte: 1,
              description:
                "処理結果<ul><li>0:正常終了</li><li>1:異常終了</li></ul>",
            },
            {
              physicalName: "error",
              logicalName: "エラー情報",
              required: false,
              type: null,
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "　message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "health_info_reg_graph_list",
              logicalName: "健康情報登録グラフ情報リスト",
              required: false,
              type: "配列",
              byte: null,
              description: "result='0' かつ データが存在する場合、必須",
            },
            {
              physicalName: "　count",
              logicalName: "登録件数",
              required: false,
              type: "半角数字",
              byte: null,
              description: "該当年月の登録件数",
            },
            {
              physicalName: "　date",
              logicalName: "登録日",
              required: false,
              type: "YYYY/MM/DD",
              byte: null,
              description: "該当年月日",
            },
          ],
        },

        {
          name: "Top情報(アカウント情報)取得API",
          endpoint: "/api/root/top/acount",
          httpMethod: "GET",
          description: "管理サイトTop画面情報の取得を行うAPI",
          request: [
            {
              physicalName: "date",
              logicalName: "対象年月",
              required: false,
              type: "YYYYMM",
              byte: null,
              description: "取得対象のTOP情報の年月を指定",
            },
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "半角数字",
              byte: 1,
              description:
                "処理結果<ul><li>0:正常終了</li><li>1:異常終了</li></ul>",
            },
            {
              physicalName: "error",
              logicalName: "エラー情報",
              required: false,
              type: null,
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "　message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "account_reg_graph_list",
              logicalName: "アカウント登録グラフ情報リスト",
              required: false,
              type: "配列",
              byte: null,
              description: "result='0' かつ データが存在する場合、必須",
            },
            {
              physicalName: "　count",
              logicalName: "登録件数",
              required: false,
              type: "半角数字",
              byte: null,
              description: "該当年月の登録件数",
            },
            {
              physicalName: "　date",
              logicalName: "登録日",
              required: false,
              type: "YYYY/MM/DD",
              byte: null,
              description: "該当年月日",
            },
          ],
        },

        {
          name: "管理ユーザ取得API",
          endpoint: "/api/root/user/{seq_login_id}",
          httpMethod: "GET",
          description: "管理サイトユーザ情報の取得を行うAPI",
          request: [],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "半角数字",
              byte: 1,
              description:
                "処理結果<ul><li>0:正常終了</li><li>1:異常終了</li></ul>",
            },
            {
              physicalName: "error",
              logicalName: "エラー情報",
              required: false,
              type: null,
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "　message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "seq_login_id",
              logicalName: "ログインID",
              required: false,
              type: "半角数字",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "roles",
              logicalName: "権限リスト",
              required: false,
              type: "配列",
              byte: null,
              description: "ユーザに紐づく権限のリスト",
            },
            {
              physicalName: "　label",
              logicalName: "権限名",
              required: false,
              type: null,
              byte: null,
              description: null,
            },
            {
              physicalName: "　value",
              logicalName: "値",
              required: false,
              type: null,
              byte: null,
              description: null,
            },
            {
              physicalName: "delete_flag",
              logicalName: "削除フラグ",
              required: false,
              type: "半角数字",
              byte: null,
              description:
                "result='0'の場合、必須<br><ul><li>0:未削除</li><li>1:削除済</li></ul>",
            },
            {
              physicalName: "password_expire",
              logicalName: "パスワード有効期限",
              required: false,
              type: "YYYY/MM/DD",
              byte: null,
              description:
                "result='0'の場合、必須<br>該当ユーザのパスワード有効期限",
            },
            {
              physicalName: "remarks",
              logicalName: "備考",
              required: false,
              type: null,
              byte: null,
              description:
                "result='0' かつ データが存在する場合、必須<br>該当ユーザの備考",
            },
          ],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>