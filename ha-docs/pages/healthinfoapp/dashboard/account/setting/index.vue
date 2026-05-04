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
          text: "アカウント設定",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/setting",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "アカウント<br>設定画面",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "画面表示",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "セッションから<br>ユーザIDを取得",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "USER + HEALTH_INFO_FILE_SETTING +<br>USER_HEALTH_GOAL 検索",
          edgeType: "round",
          next: ["a4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "a4",
          text: "ユーザ設定Formを設定",
          edgeType: "round",
        },

        {
          id: "b0",
          text: "アカウント<br>設定画面",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "確認ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "入力チェック",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "セッションに<br>ユーザ設定Formを設定",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "アカウント設定<br>確認画面に遷移",
          edgeType: "round",
        },

        {
          id: "b2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.2"],
        },
        {
          id: "b2.2",
          text: "型チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.3"],
        },
        {
          id: "b2.3",
          text: "桁数チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.4"],
        },
        {
          id: "b2.4",
          text: "形式チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.5"],
        },
        {
          id: "b2.5",
          text: "過去日チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.6"],
        },
        {
          id: "b2.6",
          text: "未来日チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.7"],
        },
        {
          id: "b2.7",
          text: "範囲チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["b2.8"],
        },
        {
          id: "b2.8",
          text: "フラグチェック",
          edgeType: "round",
          group: "入力チェック",
        },

        {
          id: "c0",
          text: "アカウント設定<br>確認画面",
          edgeType: "circle",
          next: ["c1"],
        },
        {
          id: "c1",
          text: "更新ボタン押下",
          edgeType: "round",
          next: ["c2"],
        },
        {
          id: "c2",
          text: "セッションから<br>ユーザ設定Formを取得",
          edgeType: "round",
          next: ["c3"],
        },
        {
          id: "c3",
          text: "USER 検索",
          edgeType: "round",
          next: ["c4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c4",
          text: "USER 更新",
          edgeType: "round",
          next: ["c5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c5",
          text: "HEALTH_INFO_FILE_SETTING<br>検索",
          edgeType: "round",
          next: ["c6"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c6",
          text: "HEALTH_INFO_FILE_SETTING<br>更新",
          edgeType: "round",
          next: ["c7"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c7",
          text: "有効なUSER_HEALTH_GOAL 検索",
          edgeType: "round",
          next: ["c8"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c8",
          text: "USER_HEALTH_GOAL 論理削除",
          edgeType: "round",
          link: ["-- ユーザ削除時 -->", "-- ユーザ更新時 -->"],
          next: ["c10", "c9"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c9",
          text: "USER_HEALTH_GOAL 登録",
          edgeType: "round",
          next: ["c10"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c10",
          text: "セッションの<br>ユーザ設定Formを削除",
          edgeType: "round",
          next: ["c11"],
        },
        {
          id: "c11",
          text: "削除時はセッションの<br>ユーザIDを削除",
          edgeType: "round",
          next: ["c12"],
        },
        {
          id: "c12",
          text: "アカウント設定<br>完了画面",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>