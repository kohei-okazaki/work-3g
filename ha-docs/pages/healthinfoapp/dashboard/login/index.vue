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
          text: "アカウント情報を検索",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "アカウント情報チェック",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "sessionにユーザIDを登録",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "最新健康情報検索",
          edgeType: "round",
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
          text: "アカウント作成ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "アカウント作成画面に遷移する",
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
          text: "アカウント回復用メールアドレス入力画面に遷移する",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>