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
          text: "ヘルスチェックAPI",
          disabled: true,
          href: "/healthinfoapp/api/healthcheck",
        },
      ],
      flow: [
        {
          id: "1",
          text: "GET /api/healthcheck 受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "正常レスポンス生成<br/>処理結果: 成功",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "HTTP 200 OKでJSON返却<br/>result: #quot;0#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
