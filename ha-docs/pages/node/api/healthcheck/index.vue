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
  // NodeAPIのレイアウトを適用
  layout: "nodeApiLayout",
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
          text: "Node API",
          disabled: false,
          href: "/node/api",
        },
        {
          text: "ヘルスチェックAPI",
          disabled: true,
          href: "/node/api/healthcheck",
        },
      ],
      flow: [
        {
          id: "1",
          text: "リクエストログ出力",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "レスポンスJSON生成",
          edgeType: "round",
          next: ["3"],
        },
        {
          id: "3",
          text: "レスポンスログ出力",
          edgeType: "round",
          next: ["4"],
        },
        {
          id: "4",
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