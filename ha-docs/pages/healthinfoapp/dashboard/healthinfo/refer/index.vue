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
          text: "健康情報照会",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/refer",
        },
      ],
      flow: [
        {
          id: "0",
          text: "照会前画面",
          edgeType: "circle",
          next: ["a0", "b0"],
        },
        {
          id: "a0",
          text: "画面表示",
          edgeType: "round",
          next: [""],
        },
        {
          id: "b0",
          text: "検索ボタン押下",
          edgeType: "round",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "設定ファイルより<br>ページング情報を取得",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "健康情報を検索",
          edgeType: "round",
          next: ["b4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b4",
          text: "健康情報件数を検索",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },

        {
          id: "1",
          text: "照会後画面",
          edgeType: "circle",
          next: ["c0", "d0"],
        },

        {
          id: "c0",
          text: "CSVアイコン押下",
          edgeType: "round",
          next: ["c1"],
        },
        {
          id: "c1",
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["c2"],
        },
        {
          id: "c2",
          text: "セッションより<br>検索条件を取得",
          edgeType: "round",
          next: ["c3"],
        },
        {
          id: "c3",
          text: "健康情報を検索",
          edgeType: "round",
          next: ["c4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c4",
          text: "健康情報ファイル設定情報を検索",
          edgeType: "round",
          next: ["c5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c5",
          text: "CSV出力",
          edgeType: "round",
          next: ["c6"],
        },

        {
          id: "d0",
          text: "Excelアイコン押下",
          edgeType: "round",
          next: ["d1"],
        },
        {
          id: "d1",
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["d2"],
        },
        {
          id: "d2",
          text: "セッションより<br>検索条件を取得",
          edgeType: "round",
          next: ["d3"],
        },
        {
          id: "d3",
          text: "健康情報を検索",
          edgeType: "round",
          next: ["d4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "d4",
          text: "健康情報ファイル設定情報を検索",
          edgeType: "round",
          next: ["d5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "d5",
          text: "Excel出力",
          edgeType: "round",
          next: ["d6"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>