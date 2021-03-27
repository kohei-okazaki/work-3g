<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
    <AppBatchList :batchArray="batchArray" />
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppBatchList from "~/components/AppBatchList.vue";

export default {
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppBatchList,
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
          text: "健康管理バッチ",
          disabled: false,
          href: "/healthinfoapp/batch",
        },
        {
          text: "バッチ一覧",
          disabled: true,
          href: "/healthinfoapp/batch/list",
        },
      ],
      batchArray: [
        {
          name: "健康情報一括登録バッチ",
          description:
            "健康情報のJSONファイルをもとに健康情報を一括登録するバッチ",
          crons: ["毎日 10:00:00"],
          args: [],
        },
        {
          name: "月次健康情報集計バッチ",
          description:
            "健康情報テーブルを月末に集計しS3にアップロードするバッチ",
          crons: ["毎月 1日 00:00:00"],
          args: [
            {
              name: "-m",
              value: "YYYYMM",
              description:
                "処理対象年月を指定<br>未指定の場合、システム日時の年月を使用",
            },
          ],
        },
        {
          name: "ヘルスチェックバッチ",
          description: "健康管理APIサーバの起動状態を確認するバッチ",
          crons: ["毎時 00:00:00"],
          args: [],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>