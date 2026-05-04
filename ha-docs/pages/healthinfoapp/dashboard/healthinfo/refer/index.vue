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
          text: "健康情報<br>照会画面",
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
          next: ["b0.1"],
        },
        {
          id: "b0.1",
          text: "入力チェック",
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
          text: "HEALTH_INFO 検索",
          edgeType: "round",
          next: ["b4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b4",
          text: "HEALTH_INFO 件数取得",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b5",
          text: "検索結果・ページング情報設定",
          edgeType: "round",
          next: ["b6"],
        },
        {
          id: "b6",
          text: "健康情報グラフ作成",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "セッションに検索条件を設定",
          edgeType: "round",
          next: ["b8"],
        },
        {
          id: "b8",
          text: "健康情報照会<br>結果画面に遷移",
          edgeType: "round",
        },

        {
          id: "b0.1.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b0.1.2"],
        },
        {
          id: "b0.1.2",
          text: "型チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b0.1.3"],
        },
        {
          id: "b0.1.3",
          text: "桁数チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b0.1.4"],
        },
        {
          id: "b0.1.4",
          text: "フラグチェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b0.1.5"],
        },
        {
          id: "b0.1.5",
          text: "相関チェック",
          edgeType: "round",
          group: "入力チェック",
        },

        {
          id: "1",
          text: "健康情報照会<br>結果画面",
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
          text: "HEALTH_INFO 検索",
          edgeType: "round",
          next: ["c4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c4",
          text: "HEALTH_INFO_FILE_SETTING<br>検索",
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
          id: "c6",
          text: "S3.健康情報照会CSV<br>アップロード",
          edgeType: "round",
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
          text: "HEALTH_INFO 検索",
          edgeType: "round",
          next: ["d4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "d4",
          text: "HEALTH_INFO_FILE_SETTING<br>検索",
          edgeType: "round",
          next: ["d5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "d5",
          text: "Excel出力",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>