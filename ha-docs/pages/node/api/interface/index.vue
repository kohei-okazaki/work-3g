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
    <v-row justify="center" align="center">
      <v-col sm="12">
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
  // NodeAPIのレイアウトを適用
  layout: "nodeApiLayout",
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
          text: "Node API",
          disabled: false,
          href: "/node/api",
        },
        {
          text: "IF一覧",
          disabled: true,
          href: "/node/api/interface",
        },
      ],
      ifs: [
        {
          name: "トークン発行API",
          endpoint: "/token",
          httpMethod: "POST",
          description:
            "Node APIに必要なトークン認証情報を取得するためのAPI",
          url: "/node/api/token",
          headers: [
            {
              key: "Content-Type",
              value: "application/json",
            },
          ],
          request: [
            {
              physicalName: "seq_user_id",
              logicalName: "ユーザID",
              required: true,
              type: "半角数字",
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
              physicalName: "detail",
              logicalName: "エラー詳細",
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
          name: "基礎健康情報計算API",
          endpoint: "/basic",
          httpMethod: "GET",
          description: "BMI・標準体重を計算するAPI",
          url: "/node/api/basic",
          headers: [
            {
              key: "Content-Type",
              value: "application/json",
            },
            {
              key: "X-NODE-TOKEN",
              value: "トークン発行API.レスポンスIF.tokenを設定",
            },
          ],
          request: [
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "少数数字",
              byte: null,
              description: null,
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: true,
              type: "少数数字",
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
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "basic_health_info",
              logicalName: "基礎健康情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "bmi",
              logicalName: "BMI",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト内容より計算",
            },
            {
              physicalName: "standard_weight",
              logicalName: "標準体重",
              required: false,
              layer: 1,
              type: "少数値",
              byte: null,
              description: "リクエスト内容より計算",
            },
          ],
        },

        {
          name: "カロリー計算API",
          endpoint: "/calorie",
          httpMethod: "GET",
          description: "消費カロリーを計算するAPI",
          url: "/node/api/calorie",
          headers: [
            {
              key: "Content-Type",
              value: "application/json",
            },
            {
              key: "X-NODE-TOKEN",
              value: "トークン発行API.レスポンスIF.tokenを設定",
            },
          ],
          request: [
            {
              physicalName: "gender",
              logicalName: "性別",
              required: true,
              type: "半角数字",
              byte: "1",
              description: "<ul><li>0:男性</li><li>1:女性</li></ul>",
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: true,
              type: "半角数字",
              byte: "3",
              description: null,
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "少数値",
              byte: null,
              description: null,
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: true,
              type: "少数値",
              byte: null,
              description: null,
            },
            {
              physicalName: "life_work_metabolism",
              logicalName: "生活活動代謝",
              required: true,
              type: "少数値",
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
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "calorie_calc_result",
              logicalName: "カロリー計算結果",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "base_metabolism",
              logicalName: "基礎代謝量",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト内容より計算",
            },
            {
              physicalName: "lost_calorie_per_day",
              logicalName: "1日の消費カロリー",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト内容より計算",
            },
            {
              physicalName: "user_data",
              logicalName: "ユーザ情報",
              required: false,
              type: "少数値",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "gender",
              logicalName: "性別",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: "1",
              description: "リクエスト値",
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: false,
              type: "半角数字",
              layer: 1,
              byte: "3",
              description: "リクエスト値",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "life_work_metabolism",
              logicalName: "生活活動代謝",
              required: false,
              type: "少数値",
              layer: 1,
              byte: null,
              description: "リクエスト値",
            },
          ],
        },

        {
          name: "肺活量計算API",
          endpoint: "/breath_capacity",
          httpMethod: "GET",
          description: "肺活量を計算するAPI",
          url: "/node/api/breath_capacity",
          headers: [
            {
              key: "Content-Type",
              value: "application/json",
            },
            {
              key: "X-NODE-TOKEN",
              value: "トークン発行API.レスポンスIF.tokenを設定",
            },
          ],
          request: [
            {
              physicalName: "age",
              logicalName: "年齢",
              required: true,
              type: "半角数字",
              byte: null,
              description: "ユーザの年齢",
            },
            {
              physicalName: "gender",
              logicalName: "性別",
              required: true,
              type: "半角数字",
              byte: null,
              description: "ユーザの性別",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "小数値",
              byte: null,
              description: "ユーザの身長",
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
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: null,
              byte: 256,
              description: "result='1'の場合、必須",
            },
            {
              physicalName: "breathing_capacity_calc_result",
              logicalName: "肺活量計算結果情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "predict_breathing_capacity",
              logicalName: "予測肺活量",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "計算した肺活量",
            },
            {
              physicalName: "breathing_capacity_percentage",
              logicalName: "肺活量%",
              required: false,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "計算した肺活量%",
            },
            {
              physicalName: "user_data",
              logicalName: "ユーザ情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "result='0'の場合、必須",
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: true,
              type: "半角数字",
              layer: 1,
              byte: null,
              description: "ユーザの年齢",
            },
            {
              physicalName: "gender",
              logicalName: "性別",
              required: true,
              type: "半角数字",
              layer: 1,
              byte: null,
              description: "ユーザの性別",
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "小数値",
              layer: 1,
              byte: null,
              description: "ユーザの身長",
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