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
  // 健康管理Dashboardのレイアウトを適用
  layout: "healthinfoappDashboardLayout",
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
          text: "健康管理ダッシュボード",
          disabled: false,
          href: "/healthinfoapp/dashboard",
        },
        {
          text: "お知らせ情報一覧",
          disabled: true,
          href: "/healthinfoapp/dashboard/news",
        },
      ],
      flow: [
        {
          id: "a1",
          text: "初期表示またはページ切替",
          edgeType: "circle",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "表示ページと<br/>1ページの表示件数を決定",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "NEWS_INFO検索<br/>NEWS_INFO.DELETE_FLAG = false<br/>ORDER BY NEWS_INFO.SEQ_NEWS_INFO_ID DESC<br/>LIMIT 画面指定ページの開始位置, 設定.表示件数",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "取得NEWS_INFO.S3_KEYを使用して<br/>お知らせ本文を1件ずつS3から取得",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "本文取得の結果",
          edgeType: "diamond",
          next: ["a6", "a7"],
        },
        {
          id: "a6",
          text: "正常終了: NEWS_INFO件数取得<br/>NEWS_INFO.DELETE_FLAG = false",
          edgeType: "round",
          next: ["a8"],
        },
        {
          id: "a7",
          text: "例外発生<br/>エラー画面を表示",
          edgeType: "round",
        },
        {
          id: "a8",
          text: "ページ切替リンクと<br/>表示件数の情報を作成",
          edgeType: "round",
          next: ["a9"],
        },
        {
          id: "a9",
          text: "お知らせ一覧とページ情報を表示",
          edgeType: "round",
          next: ["a10"],
        },
        {
          id: "a10",
          text: "一覧での操作",
          edgeType: "diamond",
          next: ["a11", "a12"],
        },
        {
          id: "a11",
          text: "タイトル選択<br/>本文を開閉",
          edgeType: "round",
        },
        {
          id: "a12",
          text: "ページ切替リンク選択<br/>指定ページの一覧を再取得",
          edgeType: "round",
          next: ["a2"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>