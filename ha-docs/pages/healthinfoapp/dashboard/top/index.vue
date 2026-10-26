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
          text: "TOP",
          disabled: true,
          href: "/healthinfoapp/dashboard/top",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "TOP画面表示要求",
          edgeType: "round",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "セッションのユーザIDを確認",
          edgeType: "diamond",
          next: ["a2", "a3"],
        },
        {
          id: "a2",
          text: "ユーザIDなし：ログイン画面表示",
          edgeType: "circle",
        },
        {
          id: "a3",
          text: "ユーザIDあり: HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>ORDER BY HEALTH_INFO.HEALTH_INFO_REG_DATE DESC<br/>LIMIT 2",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "登録件数に応じて表示内容を設定<br/>0件：未登録／1件：最新情報／2件：前回との体重差",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "USER_HEALTH_GOAL検索<br/>USER_HEALTH_GOAL.SEQ_USER_ID = セッション.ユーザID<br/>USER_HEALTH_GOAL.DELETE_FLAG = false<br/>最新体重があれば目標との差を算出",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>ORDER BY HEALTH_INFO.HEALTH_INFO_REG_DATE DESC<br/>LIMIT 10<br/>取得後に健康情報ID昇順でグラフ作成",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a7",
          text: "HEALTH_INFO件数取得<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>システム当日00時00分00秒 AND<br/>システム当日23時59分59秒<br/>0件なら未登録通知を設定",
          edgeType: "round",
          next: ["a8"],
        },
        {
          id: "a8",
          text: "TOP画面表示",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "ログアウトリンク押下",
          edgeType: "round",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "セッションを破棄し<br/>セッションCookieを削除",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "ログイン画面へ遷移<br/>ログアウトメッセージを表示",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
