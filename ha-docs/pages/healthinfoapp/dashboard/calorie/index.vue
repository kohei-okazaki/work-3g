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
          text: "カロリー<br>計算画面",
          edgeType: "circle",
          next: ["a1", "b1"],
        },
        {
          id: "a1",
          text: "画面表示",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "セッションより<br>ユーザIDを取得",
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
          text: "年齢・性別を<br>フォームに設定",
          edgeType: "round",
        },

        {
          id: "b1",
          text: "計算ボタン押下",
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
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "USER 検索",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b5",
          text: "計算パラメータ作成",
          edgeType: "round",
          next: ["b6"],
        },
        {
          id: "b6",
          text: "トランザクションIDを採番",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "カロリー計算API 実施",
          edgeType: "round",
          url: "/calc_api/calorie",
          next: ["b8"],
          style: "fill:#ffce9e",
        },
        {
          id: "b8",
          text: "SQS.api_log 登録",
          edgeType: "round",
          next: ["b9"],
        },
        {
          id: "b9",
          text: "API処理結果チェック",
          edgeType: "round",
          link: ["-- 失敗 -->", "-- 成功 -->"],
          next: ["b101", "b10"],
        },
        {
          id: "b101",
          text: "エラー画面遷移",
          edgeType: "round",
        },
        {
          id: "b10",
          text: "計算結果表示",
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
          text: "範囲チェック",
          edgeType: "round",
          group: "入力チェック",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>