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
          text: "Job履歴情報一覧取得API",
          disabled: true,
          href: "/root/api/job",
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
          text: "ページング情報 取得",
          edgeType: "round",
          next: ["4"],
        },
        {
          id: "4",
          text: "BATCH_JOB_INSTANCE + BATCH_JOB_EXECUTION + BATCH_JOB_EXECUTION_PARAMS <br>検索",
          edgeType: "round",
          next: ["5", "101"],
          style: "fill:#c6ffc6",
        },
        {
          id: "5",
          text: "BATCH_JOB_EXECUTION<br>件数検索",
          edgeType: "round",
          next: ["100", "101"],
          style: "fill:#c6ffc6",
        },
        {
          id: "100",
          text: "正常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "101",
          text: "異常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "110",
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