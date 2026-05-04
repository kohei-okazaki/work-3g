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
          text: "ログイン",
          disabled: true,
          href: "/healthinfoapp/dashboard/login",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "ログイン画面",
          edgeType: "circle",
          next: ["a1", "b1", "c1"],
        },
        {
          id: "a1",
          text: "ログインボタン押下",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "入力チェック",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "USER 検索",
          edgeType: "round",
          next: ["a4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a4",
          text: "ログイン情報チェック",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "セッションにユーザIDを登録",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "最新のHEALTH_INFO 検索",
          edgeType: "round",
          next: ["a7"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a7",
          text: "有効なUSER_HEALTH_GOAL 検索",
          edgeType: "round",
          next: ["a8"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a8",
          text: "直近のHEALTH_INFO 検索",
          edgeType: "round",
          next: ["a9"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a9",
          text: "当日のHEALTH_INFO 件数取得",
          edgeType: "round",
          next: ["a10"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a10",
          text: "TOP画面表示情報 設定",
          edgeType: "round",
          style: "fill:#c6ffc6",
        },

        {
          id: "a2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.2"],
        },
        {
          id: "a2.2",
          text: "桁数チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.3"],
        },
        {
          id: "a2.3",
          text: "型チェック",
          edgeType: "round",
          group: "入力チェック",
        },

        {
          id: "b1",
          text: "ユーザ作成ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "ユーザ作成画面に遷移",
          edgeType: "round",
        },

        {
          id: "c1",
          text: "パスワードを忘れた場合 リンク押下",
          edgeType: "round",
          next: ["c2"],
        },
        {
          id: "c2",
          text: "パスワード再設定用メールアドレス入力画面に遷移",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>