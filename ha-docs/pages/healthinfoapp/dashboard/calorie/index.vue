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
          id: "a1",
          text: "初期表示",
          edgeType: "circle",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "初期表示・計算送信時の共通取得<br/>USER検索<br/>USER.SEQ_USER_ID = セッション.ユーザID",
          edgeType: "round",
          next: ["a3"],
        },
        {
          id: "a3",
          text: "生年月日から年齢を算出し<br/>登録済みの性別とともに初期設定",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "カロリー計算画面を表示",
          edgeType: "round",
        },
        {
          id: "b1",
          text: "計算ボタン押下",
          edgeType: "circle",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "入力内容は正しいか",
          edgeType: "diamond",
          next: ["b3", "b4"],
        },
        {
          id: "b3",
          text: "不備あり<br/>同じ画面で入力エラーを表示",
          edgeType: "round",
        },
        {
          id: "b4",
          text: "不備なし: USER検索<br/>USER.SEQ_USER_ID = セッション.ユーザID",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "登録済みの性別と入力値で<br/>計算条件を準備",
          edgeType: "round",
          next: ["b6"],
        },
        {
          id: "b6",
          text: "外部のカロリー計算を実行",
          edgeType: "round",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "通信履歴を記録",
          edgeType: "round",
          next: ["b8"],
        },
        {
          id: "b8",
          text: "計算結果は成功か",
          edgeType: "diamond",
          next: ["b9", "b10"],
        },
        {
          id: "b9",
          text: "成功<br/>同じ画面で基礎代謝量と生活活動代謝<br/>1日の消費カロリーを表示",
          edgeType: "round",
        },
        {
          id: "b10",
          text: "失敗<br/>エラー画面を表示",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>