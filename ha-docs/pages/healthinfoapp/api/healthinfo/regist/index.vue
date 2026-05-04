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
          text: "リクエスト妥当性チェック",
          edgeType: "round",
          next: ["101", "3"],
        },
        {
          id: "3",
          text: "USER 検索",
          edgeType: "round",
          next: ["101", "4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "4",
          text: "トランザクションID採番",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "Node API移行設定判定",
          edgeType: "round",
          next: ["6", "8"],
        },
        {
          id: "6",
          text: "トークン発行API 実行",
          edgeType: "round",
          next: ["7"],
          style: "fill:#ffce9e",
        },
        {
          id: "7",
          text: "SQS.api_log 登録",
          edgeType: "round",
          next: ["101", "8"],
          style: "fill:#ffce9e",
        },
        {
          id: "8",
          text: "基礎健康情報計算API 実行",
          edgeType: "round",
          url: "/calc_api/basic",
          next: ["9"],
          style: "fill:#ffce9e",
        },
        {
          id: "9",
          text: "SQS.api_log 登録",
          edgeType: "round",
          next: ["101", "10"],
          style: "fill:#ffce9e",
        },
        {
          id: "10",
          text: "BMI_RANGE_MT 検索",
          edgeType: "round",
          next: ["101", "11"],
          style: "fill:#c6ffc6",
        },
        {
          id: "11",
          text: "HEALTH_INFO 登録",
          edgeType: "round",
          next: ["100"],
          style: "fill:#c6ffc6",
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
