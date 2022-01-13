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
          text: "ファイル<br>アップロード<br>画面",
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
          text: "健康情報CSV読込",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "CSVをAWS-S3へ配置",
          edgeType: "round",
          next: ["a5"],
        },
        {
          id: "a5",
          text: "フォーマットチェック",
          edgeType: "round",
          next: ["a6"],
        },
        {
          id: "a6",
          text: "セッションに<br>アップロードファイル名保存",
          edgeType: "round",
          next: ["a7"],
        },

        {
          id: "a2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "入力チェック",
          next: ["a2.2"],
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
          next: ["a5.4"],
        },

        {
          id: "b0",
          text: "登録確認画面",
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
          text: "AWS-S3より健康情報CSVを取得",
          edgeType: "round",
          next: ["b5"],
        },
        {
          id: "b5",
          text: "アカウント情報を検索",
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
          style: "fill:#c6ffc6",
        },
        {
          id: "b7",
          text: "API通信情報を登録<br>健康情報登録API",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          next: ["b8"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b8",
          text: "健康情報登録API 実施",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          next: ["b9"],
          style: "fill:#ffce9e",
        },
        {
          id: "b9",
          text: "API通信情報を更新<br>健康情報登録API",
          edgeType: "round",
          group: "CSV1レコードごとに実施",
          link: ["-- CSV全レコード登録成功の場合 ---"],
          next: ["b10-a"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b10-a",
          text: "セッションのファイル名を削除",
          edgeType: "round",
          next: ["b11-a"],
        },
        {
          id: "b11-a",
          text: "AWS-S3の健康情報CSVを削除",
          edgeType: "round",
          next: ["b12"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>