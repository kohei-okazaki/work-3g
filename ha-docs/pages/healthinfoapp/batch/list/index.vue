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
          url: "/healthinfoapp/batch/healthinfo/regist",
          crons: ["毎日 10:00:00"],
          args: [],
        },
        {
          name: "月次健康情報集計バッチ",
          description:
            "健康情報テーブルを月末に集計しS3にアップロードするバッチ",
          url: "/healthinfoapp/batch/healthinfo/monthlySummary",
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
          url: "/healthinfoapp/batch/healthcheck",
          crons: ["毎時 00:00:00"],
          args: [],
        },
        {
          name: "健康情報連携バッチ",
          description:
            "健康情報テーブルをtrack apiに連携するバッチ",
          url: "/healthinfoapp/batch/healthinfo/migrate",
          crons: ["毎月 1日 00:00:00"],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
        {
          name: "AWS SQS取込バッチ",
          description: "AWS SQSから健康情報を取込むバッチ",
          url: "/healthinfoapp/batch/sqs/import",
          crons: ["毎日 30分おき"],
          args: [],
        },
        {
          name: "日次データ分析連携JobSet",
          description: "日次データ分析連携JobSet",
          url: "/healthinfoapp/batch/daily/jobset",
          crons: ["毎日 23:00:00"],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
        {
          name: "日次健康情報データ分析連携バッチ",
          description: "日次健康情報データ分析連携バッチ",
          url: "/healthinfoapp/batch/daily/healthinfo",
          crons: [],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
        {
          name: "日次ユーザ情報データ分析連携バッチ",
          description: "日次ユーザ情報データ分析連携バッチ",
          url: "/healthinfoapp/batch/daily/user",
          crons: [],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
        {
          name: "日次API通信ログデータ分析連携バッチ",
          description: "日次API通信ログデータ分析連携バッチ",
          url: "/healthinfoapp/batch/daily/api_log",
          crons: [],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
        {
          name: "日次バッチ実行ログデータ分析連携バッチ",
          description: "日次バッチ実行ログデータ分析連携バッチ",
          url: "/healthinfoapp/batch/daily/batch_log",
          crons: [],
          args: [
            {
              name: "-d",
              value: "YYYYMMDD",
              description:
                "処理対象年月日を指定<br>未指定の場合、システム日時の年月日を使用",
            },
          ],
        },
      ],
    };
  },
};
</script>

<style scoped></style>
