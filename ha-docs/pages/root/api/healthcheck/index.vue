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
          text: "ヘルスチェックAPI",
          disabled: true,
          href: "/root/api/healthcheck",
        },
      ],
      flow: [
        {
          id: "start",
          text: "ヘルスチェックリクエスト受付",
          edgeType: "round",
          next: ["response"],
        },
        {
          id: "response",
          text: "正常レスポンス生成",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "正常レスポンス返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
