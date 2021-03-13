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
          text: "トークン検証",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "リクエストログ出力",
          edgeType: "round",
          next: ["3"],
        },
        { id: "3", text: "BMI計算", edgeType: "round", next: ["4"] },
        {
          id: "4",
          text: "標準体重計算",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "レスポンスJSON生成",
          edgeType: "round",
          next: ["6"],
        },
        {
          id: "6",
          text: "レスポンスログ出力",
          edgeType: "round",
          next: ["7"],
        },
        {
          id: "7",
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