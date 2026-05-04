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
          text: "健康情報編集API",
          disabled: true,
          href: "/root/api/healthinfo/edit",
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
          text: "トークン認証",
          edgeType: "round",
          link: ["-- 認証エラー -->", "-- それ以外の場合 -->"],
          next: ["404", "3"],
        },
        {
          id: "3",
          text: "リクエスト妥当性チェック",
          edgeType: "round",
          next: ["4", "101"],
        },
        {
          id: "4",
          text: "HEALTH_INFO 存在チェック",
          edgeType: "round",
          next: ["101", "5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "5",
          text: "トランザクションID採番",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "Node API移行設定判定",
          edgeType: "round",
          next: ["7", "9"],
        },
        {
          id: "7",
          text: "トークン発行API 実行",
          edgeType: "round",
          url: "/calc_api/token",
          next: ["8"],
          style: "fill:#ffce9e",
        },
        {
          id: "8",
          text: "SQS.api_log 登録",
          edgeType: "round",
          next: ["9", "101"],
        },
        {
          id: "9",
          text: "基礎健康情報計算API 実行",
          edgeType: "round",
          url: "/calc_api/basic",
          next: ["10"],
          style: "fill:#ffce9e",
        },
        {
          id: "10",
          text: "SQS.api_log 登録",
          edgeType: "round",
          link: ["-- API処理結果 == 0 -->", "-- API処理結果 <> 0 -->"],
          next: ["11", "101"],
        },
        {
          id: "11",
          text: "BMI_RANGE_MT 検索",
          edgeType: "round",
          next: ["12", "101"],
          style: "fill:#c6ffc6",
        },
        {
          id: "12",
          text: "HEALTH_INFO 更新",
          edgeType: "round",
          next: ["100", "101"],
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
          text: "レスポンス返却",
          edgeType: "round",
        },
        {
          id: "404",
          text: "認証エラー",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
