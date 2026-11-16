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
  // calc_apiのレイアウトを適用
  layout: "calcApiLayout",
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
          text: "健康情報計算API",
          disabled: false,
          href: "/calc_api",
        },
        {
          text: "基礎健康情報計算API",
          disabled: true,
          href: "/calc_api/basic",
        },
      ],
      flow: [
        {
          id: "1",
          text: "クエリパラメータ取得・型変換",
          edgeType: "round",
          next: [
            "2"
          ]
        },
        {
          id: "2",
          text: "必須・型変換結果・値域チェック",
          edgeType: "diamond",
          next: [
            "3",
            "100"
          ]
        },
        {
          id: "3",
          text: "身長をmへ変換してBMI計算<br/>小数第3位までfloor",
          edgeType: "round",
          next: [
            "4"
          ]
        },
        {
          id: "4",
          text: "標準体重計算<br/>小数第3位までfloor",
          edgeType: "round",
          next: [
            "5"
          ]
        },
        {
          id: "5",
          text: "正常レスポンスJSON生成<br/>result=0（数値）",
          edgeType: "round",
          next: [
            "6"
          ]
        },
        {
          id: "6",
          text: "HTTP 200で返却",
          edgeType: "round"
        },
        {
          id: "100",
          text: "入力エラー時のJSON返却<br/>HTTP 400・result=1（数値）・detail",
          edgeType: "round"
        }
      ],
    };
  },
};
</script>

<style scoped>
</style>
