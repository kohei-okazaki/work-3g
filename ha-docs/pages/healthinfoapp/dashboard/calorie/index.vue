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
          text: "カロリー計算",
          disabled: true,
          href: "/healthinfoapp/dashboard/calorie",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "計算前画面",
          edgeType: "circle",
          next: ["a1", "b1"],
        },
        {
          id: "a1",
          text: "画面表示",
          edgeType: "round",
          next: [""],
        },
        {
          id: "b1",
          text: "計算ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "トランザクションIDを採番",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "API通信情報 登録<br>Token発行API",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "Token発行API 実施",
          edgeType: "round",
          url: "/node/api/token",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "API通信情報 更新<br>Token発行API",
          edgeType: "round",
          link: ["-- API処理結果 <> 0 -->", "-- API処理結果 == 0 -->"],
          next: ["b101", "b6"],
        },
        {
          id: "b6",
          text: "API通信情報 登録<br>カロリー計算API",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "カロリー計算API 実施",
          edgeType: "round",
          url: "/node/api/calorie",
          next: ["b8"],
        },
        {
          id: "b8",
          text: "API通信情報 更新<br>カロリー計算API",
          edgeType: "round",
          link: ["-- API処理結果 <> 0 -->", "-- API処理結果 == 0 -->"],
          next: ["b101", "b9"],
        },
        {
          id: "b101",
          text: "エラー画面遷移",
          edgeType: "round",
          next: [""],
        },
        {
          id: "b9",
          text: "計算結果表示",
          edgeType: "round",
          next: [""],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>