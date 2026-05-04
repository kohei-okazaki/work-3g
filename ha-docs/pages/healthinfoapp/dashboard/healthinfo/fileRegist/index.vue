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
          text: "健康情報一括登録",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/fileRegist",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "健康情報<br>一括登録画面",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "アップロードボタン押下",
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
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "健康情報CSV 読込",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "S3.健康情報一括登録CSV<br>アップロード",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "フォーマットチェック",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a7",
          text: "セッションに<br>アップロードファイル名保存",
          edgeType: "round",
          next: ["a8"],
        },
        {
          id: "a8",
          text: "健康情報一括登録<br>確認画面に遷移",
          edgeType: "round",
        },

        {
          id: "a2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
        },
        {
          id: "a5.1",
          text: "必須チェック",
          edgeType: "round",
          group: "フォーマットチェック",
          next: ["a5.2"],
        },
        {
          id: "a5.2",
          text: "型チェック",
          edgeType: "round",
          group: "フォーマットチェック",
          next: ["a5.3"],
        },
        {
          id: "a5.3",
          text: "相関チェック",
          edgeType: "round",
          group: "フォーマットチェック",
        },

        {
          id: "b0",
          text: "健康情報一括登録<br>確認画面",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "アップロードボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "セッションより<br>ユーザIDを取得",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "セッションより<br>ファイル名を取得",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "S3.健康情報一括登録CSV<br>ダウンロード",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "USER 検索",
          edgeType: "round",
          next: ["b6"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b6",
          text: "トランザクションIDを採番",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          next: ["b7"],
        },
        {
          id: "b7",
          text: "健康情報登録API 実施",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          next: ["b8"],
          style: "fill:#ffce9e",
          url: "/healthinfoapp/api/healthinfo/regist",
        },
        {
          id: "b8",
          text: "SQS.api_log 登録",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          next: ["b9"],
        },
        {
          id: "b9",
          text: "API処理結果集約",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          link: ["-- 全件成功 -->", "-- 失敗あり -->"],
          next: ["b10-a", "b12"],
        },
        {
          id: "b10-a",
          text: "セッションのファイル名を削除",
          edgeType: "round",
          next: ["b11-a"],
        },
        {
          id: "b11-a",
          text: "S3.健康情報一括登録CSV 削除",
          edgeType: "round",
          next: ["b12"],
        },
        {
          id: "b12",
          text: "健康情報一括登録<br>完了画面",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>