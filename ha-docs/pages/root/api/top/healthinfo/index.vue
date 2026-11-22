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
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
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
          text: "管理者サイト用API",
          disabled: false,
          href: "/root/api",
        },
        {
          text: "Top情報(健康情報)取得API",
          disabled: true,
          href: "/root/api/top/healthinfo",
        },
      ],
      flow: [
        {
          id: "start",
          text: "Top情報取得リクエスト受付",
          edgeType: "round",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "JWTの署名・有効期限を確認",
          edgeType: "diamond",
          next: ["authenticated","auth_error"],
        },
        {
          id: "authenticated",
          text: "認証成功：クエリ.dateの対象年月から月初・月末を設定<br/>未指定・空文字は当月",
          edgeType: "round",
          next: ["aggregate","date_error"],
        },
        {
          id: "aggregate",
          text: "HEALTH_INFO検索／日別件数を集計<br/>集計元はHEALTH_INFO全体<br/>GROUP BY HEALTH_INFO.HEALTH_INFO_REG_DATEの日付部分<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>対象年月の月初00:00:00 AND 月末23:59:59<br/>同日のHEALTH_INFO.HEALTH_INFO_REG_DATE集計結果と結合<br/>ORDER BY HEALTH_INFO.HEALTH_INFO_REG_DATEの日付部分 ASC<br/>HEALTH_INFO.DELETE_FLAG条件なし",
          edgeType: "round",
          next: ["response","server_error"],
        },
        {
          id: "response",
          text: "集計成功：グラフ用の日付・件数を設定",
          edgeType: "round",
          next: ["success"],
        },
        {
          id: "success",
          text: "正常レスポンス返却<br/>HTTP 200・result: #quot;0#quot;",
          edgeType: "round",
        },
        {
          id: "date_error",
          text: "年月不正：Slack通知<br/>HTTP 400・result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "auth_error",
          text: "認証不成立：認証エラーで終了",
          edgeType: "round",
        },
        {
          id: "server_error",
          text: "処理例外：Slack通知<br/>HTTP 500・result: #quot;1#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
