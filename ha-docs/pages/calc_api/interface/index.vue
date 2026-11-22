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
  // calc_apiのレイアウトを適用
  layout: "calcApiLayout",
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
          text: "健康情報計算API",
          disabled: false,
          href: "/calc_api",
        },
        {
          text: "IF一覧",
          disabled: true,
          href: "/calc_api/interface",
        },
      ],
      ifs: [
        {
          name: "基礎健康情報計算API",
          endpoint: "/basic",
          httpMethod: "GET",
          description: "GETクエリの身長・体重からBMIと標準体重を計算する。独自の必須ヘッダーとJSONボディは設けない。",
          url: "/calc_api/basic",
          headers: [],
          request: [
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。身長（cm）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。体重（kg）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            }
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "整数（JSON数値）",
              byte: null,
              description: "正常応答はHTTP 200・数値0、入力エラーはHTTP 400・数値1。"
            },
            {
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: "文字列",
              byte: null,
              description: "入力エラー時に必須。検出順で最初のエラーメッセージを返す。正常時は出力しない。"
            },
            {
              physicalName: "basic_health_info",
              logicalName: "基礎健康情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "正常時に必須。入力エラー時は出力しない。"
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。float()で変換した身長（cm）。"
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。float()で変換した体重（kg）。"
            },
            {
              physicalName: "bmi",
              logicalName: "BMI",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。体重 / 身長（m）の2乗。math.floor(value * 1000) / 1000で丸める。"
            },
            {
              physicalName: "standard_weight",
              logicalName: "標準体重",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。22 × 身長（m）の2乗（kg）。math.floor(value * 1000) / 1000で丸める。"
            }
          ]
        },
        {
          name: "カロリー計算API",
          endpoint: "/calorie",
          httpMethod: "GET",
          description: "GETクエリの性別・年齢・身長・体重から基礎代謝量を計算し、生活活動代謝を加えて1日の消費カロリーを求める。独自の必須ヘッダーとJSONボディは設けない。",
          url: "/calc_api/calorie",
          headers: [],
          request: [
            {
              physicalName: "gender",
              logicalName: "性別",
              required: true,
              type: "整数",
              byte: null,
              description: "クエリパラメータ。int()で変換する。0:男性、1:女性。"
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: true,
              type: "整数",
              byte: null,
              description: "クエリパラメータ。年齢（歳）をint()で変換し、0以上とする。API独自の上限は設けない。"
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。身長（cm）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。体重（kg）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            },
            {
              physicalName: "life_work_metabolism",
              logicalName: "生活活動代謝",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。生活活動代謝（kcal）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            }
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "整数（JSON数値）",
              byte: null,
              description: "正常応答はHTTP 200・数値0、入力エラーはHTTP 400・数値1。"
            },
            {
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: "文字列",
              byte: null,
              description: "入力エラー時に必須。検出順で最初のエラーメッセージを返す。正常時は出力しない。"
            },
            {
              physicalName: "calorie_calc_result",
              logicalName: "カロリー計算結果",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "正常時に必須。入力エラー時は出力しない。"
            },
            {
              physicalName: "base_metabolism",
              logicalName: "基礎代謝量",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。性別ごとの係数を使用する（kcal）。体重項・身長項・年齢項・合算の各段階を小数第3位までfloorする。"
            },
            {
              physicalName: "lost_calorie_per_day",
              logicalName: "1日の消費カロリー",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。基礎代謝量 + 生活活動代謝（kcal）を小数第3位までfloorする。"
            },
            {
              physicalName: "user_data",
              logicalName: "入力情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "正常時に必須。入力エラー時は出力しない。"
            },
            {
              physicalName: "gender",
              logicalName: "性別",
              required: false,
              type: "整数",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: false,
              type: "整数",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "weight",
              logicalName: "体重",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "life_work_metabolism",
              logicalName: "生活活動代謝",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            }
          ]
        },
        {
          name: "肺活量計算API",
          endpoint: "/breathing_capacity",
          httpMethod: "GET",
          description: "GETクエリの性別・年齢・身長から予測肺活量と、基準肺活量の予測肺活量に対する割合を求める。独自の必須ヘッダーとJSONボディは設けない。",
          url: "/calc_api/breath_capacity",
          headers: [],
          request: [
            {
              physicalName: "gender",
              logicalName: "性別",
              required: true,
              type: "整数",
              byte: null,
              description: "クエリパラメータ。int()で変換する。0:男性、1:女性。"
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: true,
              type: "整数",
              byte: null,
              description: "クエリパラメータ。年齢（歳）をint()で変換し、0以上とする。API独自の上限は設けない。"
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: true,
              type: "数値",
              byte: null,
              description: "クエリパラメータ。身長（cm）をfloat()で変換し、NaNを拒否して0より大きい値を要求する。"
            }
          ],
          response: [
            {
              physicalName: "result",
              logicalName: "処理結果",
              required: true,
              type: "整数（JSON数値）",
              byte: null,
              description: "正常応答はHTTP 200・数値0、入力エラーはHTTP 400・数値1。"
            },
            {
              physicalName: "detail",
              logicalName: "エラー詳細",
              required: false,
              type: "文字列",
              byte: null,
              description: "入力エラー時に必須。検出順で最初のエラーメッセージを返す。正常時は出力しない。"
            },
            {
              physicalName: "breathing_capacity_calc_result",
              logicalName: "肺活量計算結果",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "正常時に必須。入力エラー時は出力しない。"
            },
            {
              physicalName: "predict_breathing_capacity",
              logicalName: "予測肺活量",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。性別ごとの係数から求める（mL）。年齢減算分と予測値を小数第3位までfloorする。"
            },
            {
              physicalName: "breathing_capacity_percentage",
              logicalName: "肺活量の割合",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。基準肺活量（男性3500mL・女性2500mL）/ 予測肺活量 × 100を小数第3位までfloorする。"
            },
            {
              physicalName: "user_data",
              logicalName: "入力情報",
              required: false,
              type: "オブジェクト",
              byte: null,
              description: "正常時に必須。入力エラー時は出力しない。"
            },
            {
              physicalName: "gender",
              logicalName: "性別",
              required: false,
              type: "整数",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "age",
              logicalName: "年齢",
              required: false,
              type: "整数",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            },
            {
              physicalName: "height",
              logicalName: "身長",
              required: false,
              type: "数値",
              layer: 1,
              byte: null,
              description: "正常時に必須。型変換後の入力値。"
            }
          ]
        }
      ],
    };
  },
};
</script>

<style scoped></style>
