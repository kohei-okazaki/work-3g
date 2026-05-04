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
          text: "健康情報照会API",
          disabled: true,
          href: "/healthinfoapp/api/healthinfo_refer",
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
          next: ["100", "3"],
        },
        {
          id: "3",
          text: "USER 検索",
          edgeType: "round",
          next: ["100", "4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "4",
          text: "HEALTH_INFO 検索",
          edgeType: "round",
          next: ["100", "5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "5",
          text: "検索結果件数チェック",
          edgeType: "round",
          next: ["100", "101"],
        },
        {
          id: "100",
          text: "異常系レスポンスJSON生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "101",
          text: "正常系レスポンスJSON生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "110",
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
