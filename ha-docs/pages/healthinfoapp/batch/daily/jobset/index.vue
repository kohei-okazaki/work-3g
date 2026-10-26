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
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
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
          text: "健康管理バッチ",
          disabled: false,
          href: "/healthinfoapp/batch",
        },
        {
          text: "日次データ分析連携JobSet",
          disabled: true,
          href: "/healthinfoapp/batch/daily/jobset",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理：日次データ連携開始",
          edgeType: "circle",
          next: ["sh_prepare"],
        },
        {
          id: "sh_prepare",
          text: "共通設定を読み込み<br/>引数から対象日を取得",
          edgeType: "diamond",
          next: ["sh_prepare_error", "sh_health"],
        },
        {
          id: "sh_prepare_error",
          text: "設定読込失敗・引数未指定：異常終了",
          edgeType: "circle",
        },
        {
          id: "sh_health",
          text: "取得成功：対象日を渡して<br/>日次健康情報連携の子シェルを実行",
          edgeType: "round",
          next: ["sh_health_result"],
        },
        {
          id: "sh_health_result",
          text: "子シェルの終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_user"],
        },
        {
          id: "sh_user",
          text: "終了コード0：対象日を渡して<br/>日次ユーザ情報連携の子シェルを実行",
          edgeType: "round",
          next: ["sh_user_result"],
        },
        {
          id: "sh_user_result",
          text: "子シェルの終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_api"],
        },
        {
          id: "sh_api",
          text: "終了コード0：対象日を渡して<br/>日次API通信ログ連携の子シェルを実行",
          edgeType: "round",
          next: ["sh_api_result"],
        },
        {
          id: "sh_api_result",
          text: "子シェルの終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_batch"],
        },
        {
          id: "sh_batch",
          text: "終了コード0：対象日を渡して<br/>日次バッチ実行ログ連携の子シェルを実行",
          edgeType: "round",
          next: ["sh_batch_result"],
        },
        {
          id: "sh_batch_result",
          text: "子シェルの終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_complete"],
        },
        {
          id: "sh_complete",
          text: "全て終了コード0：終了ログを出力して正常終了",
          edgeType: "circle",
        },
        {
          id: "sh_error",
          text: "終了コードが非0：以降の子シェルを実行せず<br/>その終了コードで異常終了",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
