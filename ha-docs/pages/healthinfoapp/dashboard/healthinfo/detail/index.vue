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
          text: "健康情報詳細",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/detail",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "照会結果の詳細リンクを選択",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "健康情報IDの指定・数値を確認",
          edgeType: "diamond",
          next: ["a_error", "a2"],
        },
        {
          id: "a2",
          text: "セッションからユーザIDを取得",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "HEALTH_INFO検索・BMI情報取得<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>URLクエリ.健康情報ID<br/>LEFT JOIN BMI_RANGE_MT<br/>ON HEALTH_INFO.SEQ_BMI_RANGE_MT_ID =<br/>BMI_RANGE_MT.SEQ_BMI_RANGE_MT_ID",
          edgeType: "round",
          next: ["a_error", "a4"],
        },
        {
          id: "a4",
          text: "対象の健康情報があるか",
          edgeType: "diamond",
          next: ["a5", "a6"],
        },
        {
          id: "a5",
          text: "該当あり: 作成日時を整形<br/>健康情報・肥満度を詳細画面に表示",
          edgeType: "circle",
        },
        {
          id: "a6",
          text: "該当なし: 詳細画面に<br/>健康情報なしの案内を表示",
          edgeType: "circle",
        },
        {
          id: "a_error",
          text: "不正なID・検索処理等の異常<br/>共通エラー処理へ",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>