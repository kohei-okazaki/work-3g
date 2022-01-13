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
          text: "健康情報登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/regist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "入力画面",
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
          text: "セッションに健康情報設定",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "健康情報入力確認画面に遷移",
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
          text: "上限値チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.3"],
        },
        {
          id: "a2.3",
          text: "下限値チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.4"],
        },
        {
          id: "a2.4",
          text: "型チェック",
          edgeType: "round",
          group: "入力チェック",
        },

        {
          id: "b0",
          text: "確認画面",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "確定ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "セッションから健康情報Formを取得",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "セッションからユーザIDを取得",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "ユーザの健康情報件数を取得",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b5",
          text: "最後に登録した健康情報を検索",
          edgeType: "round",
          next: ["b6"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b6",
          text: "アカウント情報を検索",
          edgeType: "round",
          next: ["b7"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b7",
          text: "トランザクションIDを採番",
          edgeType: "round",
          next: ["b8"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b8",
          text: "API通信情報を登録<br>健康情報登録API",
          edgeType: "round",
          next: ["b9"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b9",
          text: "健康情報登録API 実施",
          edgeType: "round",
          next: ["b10"],
          url: "/healthinfoapp/api/healthinfo/regist",
          style: "fill:#ffce9e",
        },
        {
          id: "b10",
          text: "API通信情報を更新<br>健康情報登録API",
          edgeType: "round",
          next: ["b11"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b11",
          text: "健康情報メールを送信する",
          edgeType: "round",
          next: ["b12"],
        },
        {
          id: "b12",
          text: "セッションに健康情報Formを設定",
          edgeType: "round",
          next: ["b13"],
        },

        {
          id: "c0",
          text: "完了画面",
          edgeType: "circle",
          next: ["c1.1", "c2.1"],
        },
        {
          id: "c1.1",
          text: "Excelダウンロードリンク押下",
          edgeType: "round",
          next: ["c1.2"],
        },
        {
          id: "c1.2",
          text: "セッションから健康情報Formを取得",
          edgeType: "round",
          next: ["c1.3"],
        },
        {
          id: "c1.3",
          text: "セッションからユーザIDを取得",
          edgeType: "round",
          next: ["c1.4"],
        },
        {
          id: "c1.4",
          text: "健康情報を検索",
          edgeType: "round",
          next: ["c1.5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c1.5",
          text: "Excel出力",
          edgeType: "round",
        },

        {
          id: "c2.1",
          text: "CSVダウンロードリンク押下",
          edgeType: "round",
          next: ["c2.2"],
        },
        {
          id: "c2.2",
          text: "セッションから健康情報Formを取得",
          edgeType: "round",
          next: ["c2.3"],
        },
        {
          id: "c2.3",
          text: "セッションからユーザIDを取得",
          edgeType: "round",
          next: ["c2.4"],
        },
        {
          id: "c2.4",
          text: "健康情報を検索",
          edgeType: "round",
          next: ["c2.5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c2.5",
          text: "CSV出力",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>