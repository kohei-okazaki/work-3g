<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />

    <v-row justify="center" align="center">
      <v-col cols="12" sm="10" md="10">
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(ifItem, i) in ifs" :key="i">
            <v-expansion-panel-header class="text-subtitle-1"
              >{{ i + 1 }}.{{ ifItem.name }}
              <template v-slot:actions>
                <v-chip :color="getHttpMethodColor(ifItem.httpMethod)">
                  {{ ifItem.httpMethod }}
                </v-chip>
                <v-chip color="gray">
                  {{ ifItem.endpoint }}
                </v-chip>
              </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <div>{{ ifItem.description }}</div>
              <br />
              <v-alert border="left" type="info">リクエストIF</v-alert>
              <v-simple-table>
                <thead>
                  <tr>
                    <th>No.</th>
                    <th>項目名(物理名)</th>
                    <th>項目名(論理名)</th>
                    <th>必須</th>
                    <th>型</th>
                    <th>Byte数</th>
                    <th>備考</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(request, i) in ifItem.request" :key="i">
                    <td>{{ i + 1 }}</td>
                    <td>{{ request.physicalName }}</td>
                    <td>{{ request.logicalName }}</td>
                    <td>
                      <v-icon v-if="request.required"
                        >mdi-checkbox-blank-circle-outline</v-icon
                      >
                    </td>
                    <td>{{ request.type }}</td>
                    <td>{{ request.byte }}</td>
                    <td v-html="request.description"></td>
                  </tr>
                </tbody>
              </v-simple-table>
              <v-divider />
              <br />
              <v-alert border="left" type="info">レスポンスIF</v-alert>
              <v-simple-table>
                <thead>
                  <tr>
                    <th>No.</th>
                    <th>項目名(物理名)</th>
                    <th>項目名(論理名)</th>
                    <th>必須</th>
                    <th>型</th>
                    <th>Byte数</th>
                    <th>備考</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(response, i) in ifItem.response" :key="i">
                    <td>{{ i + 1 }}</td>
                    <td>{{ response.physicalName }}</td>
                    <td>{{ response.logicalName }}</td>
                    <td>
                      <v-icon v-if="response.required"
                        >mdi-checkbox-blank-circle-outline</v-icon
                      >
                    </td>
                    <td>{{ response.type }}</td>
                    <td>{{ response.byte }}</td>
                    <td v-html="response.description"></td>
                  </tr>
                </tbody>
              </v-simple-table>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // NodeAPIのレイアウトを適用
  layout: "nodeApiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
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
          href: "/node/api/if",
        },
      ],
      ifs: [
        {
          name: "トークン発行API",
          endpoint: "/token",
          httpMethod: "GET",
          description:
            "健康情報計算APIに必要なトークン認証情報を取得するためのAPI",
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
              physicalName: "　height",
              logicalName: "身長",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "　weight",
              logicalName: "体重",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "　bmi",
              logicalName: "BMI",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト内容より計算",
            },
            {
              physicalName: "　standard_weight",
              logicalName: "標準体重",
              required: false,
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
              physicalName: "　base_metabolism",
              logicalName: "基礎代謝量",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト内容より計算",
            },
            {
              physicalName: "　lost_calorie_per_day",
              logicalName: "1日の消費カロリー",
              required: false,
              type: "少数値",
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
              physicalName: "　gender",
              logicalName: "性別",
              required: false,
              type: "半角数字",
              byte: "1",
              description: "リクエスト値",
            },
            {
              physicalName: "　age",
              logicalName: "年齢",
              required: false,
              type: "半角数字",
              byte: "3",
              description: "リクエスト値",
            },
            {
              physicalName: "　height",
              logicalName: "身長",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "　weight",
              logicalName: "体重",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト値",
            },
            {
              physicalName: "　life_work_metabolism",
              logicalName: "生活活動代謝",
              required: false,
              type: "少数値",
              byte: null,
              description: "リクエスト値",
            },
          ],
        },
      ],
    };
  },
  methods: {
    getHttpMethodColor: function (httpMethod) {
      if (httpMethod == "GET") {
        return "orange";
      } else if (httpMethod == "POST") {
        return "blue";
      } else if (httpMethod == "PUT") {
        return "yellow";
      } else if (httpMethod == "DELETE") {
        return "red";
      }
      return "gray";
    },
  },
};
</script>

<style scoped>
</style>