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
          text: "基礎健康情報計算API 実施",
          edgeType: "round",
          url: "/node/api/basic",
          next: ["4"],
          style: "fill:#ffce9e",
        },
        {
          id: "4",
          text: "SQS 登録<br>'基礎健康情報計算API'",
          edgeType: "round",
          style: "fill:#c6ffc6",
          link: ["-- API処理結果 == 0 -->", "-- API処理結果 <> 0 -->"],
          next: ["10", "101"],
        },
        {
          id: "10",
          text: "BMI_RANGE_MT 検索",
          edgeType: "round",
          next: ["11", "101"],
          style: "fill:#c6ffc6",
        },
        {
          id: "11",
          text: "HEALTH_INFO 検索",
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
          text: "404エラー",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>