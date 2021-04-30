<template>
  <div>
    <v-row justify="center">
      <v-col class="text-center" sm="12" md="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col class="text-left" sm="12" md="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center" align="center">
      <v-col sm="12" md="12">
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
  // 健康管理APIのレイアウトを適用
  layout: "healthinfoappApiLayout",
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
          text: "健康管理API",
          disabled: false,
          href: "/healthinfoapp/api",
        },
        {
          text: "IF一覧",
          disabled: true,
          href: "/healthinfoapp/api/interface",
        },
      ],
      ifs: [
        {
          name: "健康情報登録API",
          endpoint: "/api/{seqUserId}/healthinfo",
          httpMethod: "POST",
          description: "ユーザの健康情報を登録するAPI",
          url: "/healthinfoapp/api/healthinfo/regist",
          headers: [
            {
              key: "Accept-Charset",
              value: "utf-8",
            },
            {
              key: "Content-Type",
              value: "application/json",
            },
            {
              key: "Api-Key",
              value: "{seqUserId}のアカウント情報.APIキーを指定",
            },
          ],
          request: [
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "小数値",
              byte: null,
              description: "ユーザの登録したい身長",
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: true,
              type: "小数値",
              byte: null,
              description: "ユーザの登録したい体重",
            },
            {
              physicalName: "testMode",
              logicalName: "テストモード種別",
              required: true,
              type: "半角数字",
              byte: null,
              description:
                "<ul><li>0:DB登録を行うモード</li><li>1:テストモード(DB登録を行わない)</li></ul>",
            },
            {
              physicalName: "transactionId",
              logicalName: "トランザクションID",
              required: false,
              type: "半角数字",
              byte: null,
              description:
                "内部でAPIを一意に識別するためのID(未指定で送信してください)",
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
              type: "オブジェクト",
              byte: null,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "code",
              logicalName: "エラーコード",
              required: false,
              type: null,
              layer: 1,
              byte: null,
              description: null,
            },
            {
              physicalName: "detail",
              logicalName: "詳細",
              required: false,
              type: null,
              layer: 1,
              byte: null,
              description: null,
            },
            {
              physicalName: "account",
              logicalName: "アカウント情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "seqUserId",
              logicalName: "ユーザID",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: null,
              description: "健康管理アプリでユーザを一意に識別するためのID",
            },
            {
              physicalName: "healthInfo",
              logicalName: "健康情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "seqHealthInfoId",
              logicalName: "健康情報ID",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: null,
              description: "健康管理アプリで健康情報を一意に識別するためのID",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した身長",
            },
            {
              physicalName: "height",
              logicalName: "体重",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した体重",
            },
            {
              physicalName: "bmi",
              logicalName: "BMI",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録したBMI",
            },
            {
              physicalName: "standardWeight",
              logicalName: "標準体重",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した標準体重",
            },
            {
              physicalName: "status",
              logicalName: "健康情報ステータス",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: null,
              description:
                "登録した健康情報のステータス<ul><li>10:減少</li><li>20:変化なし</li><li>30:増加</li></ul>",
            },
            {
              physicalName: "regDate",
              logicalName: "健康情報作成日時",
              required: false,
              type: "YYYYMMDDHHMMSS",
              layer: 1,
              byte: null,
              description: "健康情報を登録した日時",
            },
          ],
        },

        {
          name: "健康情報照会API",
          endpoint: "/api/{seqUserId}/healthinfo/{seqHealthInfoId}",
          httpMethod: "GET",
          description: "ユーザの健康情報を参照するAPI",
          url: "/healthinfoapp/api/healthinfo/refer",
          headers: [
            {
              key: "Accept-Charset",
              value: "utf-8",
            },
            {
              key: "Content-Type",
              value: "application/json",
            },
            {
              key: "Api-Key",
              value: "{seqUserId}のアカウント情報.APIキーを指定",
            },
          ],
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
              physicalName: "message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              layer: 1,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "healthInfo",
              logicalName: "健康情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "seqHealthInfoId",
              logicalName: "健康情報ID",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: null,
              description: "健康管理アプリで健康情報を一意に識別するためのID",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した身長",
            },
            {
              physicalName: "height",
              logicalName: "体重",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した体重",
            },
            {
              physicalName: "bmi",
              logicalName: "BMI",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録したBMI",
            },
            {
              physicalName: "standardWeight",
              logicalName: "標準体重",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "登録した標準体重",
            },
            {
              physicalName: "status",
              logicalName: "健康情報ステータス",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: null,
              description:
                "登録した健康情報のステータス<ul><li>10:減少</li><li>20:変化なし</li><li>30:増加</li></ul>",
            },
            {
              physicalName: "regDate",
              logicalName: "健康情報作成日時",
              required: false,
              type: "YYYYMMDDHHMMSS",
              layer: 1,
              byte: null,
              description: "健康情報を登録した日時",
            },
          ],
        },

        {
          name: "ヘルスチェックAPI",
          endpoint: "/api/healthcheck",
          httpMethod: "GET",
          description: "APIサーバ起動しているかをチェックするAPI",
          url: "/healthinfoapp/api/healthcheck",
          headers: [
            {
              key: "Accept-Charset",
              value: "utf-8",
              description: "UTF-8を固定で指定",
            },
          ],
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
              physicalName: "message",
              logicalName: "エラーメッセージ",
              required: false,
              type: null,
              layer: 1,
              byte: 256,
              description: "result='1'の場合、必須",
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