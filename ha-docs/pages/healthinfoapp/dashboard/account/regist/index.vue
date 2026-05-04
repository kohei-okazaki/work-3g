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
          text: "アカウント登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/regist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "アカウント<br>登録画面",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "確認ボタン押下",
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
          text: "メールアドレス重複チェック",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "セッションに<br>ユーザ登録Formを設定",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "アカウント登録<br>確認画面に遷移",
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
          text: "型チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.3"],
        },
        {
          id: "a2.3",
          text: "桁数チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.4"],
        },
        {
          id: "a2.4",
          text: "形式チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.5"],
        },
        {
          id: "a2.5",
          text: "過去日チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.6"],
        },
        {
          id: "a2.6",
          text: "範囲チェック",
          edgeType: "round",
          group: "入力チェック",
        },

        {
          id: "b0",
          text: "アカウント登録<br>確認画面",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "登録ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "セッションから<br>ユーザ登録Formを取得",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "USER 登録",
          edgeType: "round",
          next: ["b4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b4",
          text: "HEALTH_INFO_FILE_SETTING<br>登録",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b5",
          text: "USER_HEALTH_GOAL 登録",
          edgeType: "round",
          next: ["b6"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b6",
          text: "セッションの<br>ユーザ登録Formを削除",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "アカウント登録<br>完了画面",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>