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
  // 健康管理APIのレイアウトを適用
  layout: "healthinfoappApiLayout",
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
          text: "健康管理API",
          disabled: false,
          href: "/healthinfoapp/api",
        },
        {
          text: "健康情報登録API",
          disabled: true,
          href: "/healthinfoapp/api/healthinfo_regist",
        },
      ],
      flow: [
        {
          id: "1",
          text: "リクエスト受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "ヘッダ情報チェック",
          edgeType: "round",
          link: ["-- ヘッダ.Api-Keyが存在しない -->", "-- それ以外場合 -->"],
          next: ["101", "3"],
        },
        {
          id: "3",
          text: "アカウント情報 検索",
          edgeType: "round",
          link: [
            "-- 検索結果 == 0 -->",
            "-- 検索結果.API_KEY <br><> ヘッダ.Api-Key -->",
            "-- それ以外場合 -->",
          ],
          next: ["101", "101", "4"],
        },
        {
          id: "4",
          text: "API通信情報 登録<br>Token発行API",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "Token発行API 実施",
          edgeType: "round",
          url: "/node/api/token",
          next: ["6"],
        },
        {
          id: "6",
          text: "API通信情報 更新<br>Token発行API",
          edgeType: "round",
          link: ["-- API処理結果 <> 0 -->", "-- API処理結果 == 0 -->"],
          next: ["101", "7.2"],
        },
        {
          id: "7.2",
          text: "API通信情報 登録<br>基礎健康情報計算API",
          edgeType: "round",
          next: ["8"],
        },
        {
          id: "8",
          text: "基礎健康情報計算API 実施",
          edgeType: "round",
          url: "/node/api/basic",
          next: ["9"],
        },
        {
          id: "9",
          text: "API通信情報 更新<br>基礎健康情報計算API",
          edgeType: "round",
          link: ["-- API処理結果 <> 0 -->", "-- API処理結果 == 0 -->"],
          next: ["101", "10"],
        },
        {
          id: "10",
          text: "健康情報 検索",
          edgeType: "round",
          next: ["11"],
        },
        {
          id: "11",
          text: "BMI範囲マスタ 検索",
          edgeType: "round",
          next: ["12"],
        },
        {
          id: "12",
          text: "健康情報 登録",
          edgeType: "round",
          next: ["100"],
        },
        {
          id: "100",
          text: "正常系レスポンスJSON生成",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "101",
          text: "異常系レスポンスJSON生成",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "999",
          text: "レスポンスJSON返却",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>