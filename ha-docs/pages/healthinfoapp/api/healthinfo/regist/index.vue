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
  // 健康管理APIのレイアウトを適用
  layout: "healthinfoappApiLayout",
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
          text: "健康管理API",
          disabled: false,
          href: "/healthinfoapp/api",
        },
        {
          text: "健康情報登録API",
          disabled: true,
          href: "/healthinfoapp/api/healthinfo_regist",
        },
      ],
      flow: [
        {
          id: "1",
          text: "健康情報登録リクエスト受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "必須・形式チェック",
          edgeType: "diamond",
          next: ["101","3"],
        },
        {
          id: "3",
          text: "USER検索<br/>USER.SEQ_USER_ID = パス.ユーザID<br/>取得後にUSER.API_KEY = ヘッダー.Api-Keyを照合",
          edgeType: "diamond",
          next: ["101","4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "4",
          text: "トランザクションID補完<br/>未指定時のみ採番",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "計算APIのトークン発行要否を判定",
          edgeType: "diamond",
          next: ["6","8"],
        },
        {
          id: "6",
          text: "発行が必要: トークン発行API呼び出し",
          edgeType: "round",
          next: ["7"],
          style: "fill:#ffce9e",
        },
        {
          id: "7",
          text: "トークン発行の通信ログをSQS送信",
          edgeType: "round",
          next: ["101","71"],
          style: "fill:#ffce9e",
        },
        {
          id: "71",
          text: "トークン発行結果を判定<br/>成功時は取得したトークンを使用",
          edgeType: "diamond",
          next: ["101","8"],
        },
        {
          id: "8",
          text: "基礎健康情報計算API呼び出し<br/>発行が不要ならトークンなし",
          edgeType: "round",
          url: "/calc_api/basic",
          next: ["9"],
          style: "fill:#ffce9e",
        },
        {
          id: "9",
          text: "計算APIの通信ログをSQS送信",
          edgeType: "round",
          next: ["101","91"],
          style: "fill:#ffce9e",
        },
        {
          id: "91",
          text: "基礎健康情報計算結果が成功か",
          edgeType: "diamond",
          next: ["101","10"],
        },
        {
          id: "10",
          text: "BMI_RANGE_MT全件取得・有効キャッシュがあれば再利用<br/>取得後に先頭の該当範囲を選択<br/>BMI_RANGE_MT.RANGE_MINの整数値 ≦ 計算結果.BMIの整数値<br/>計算結果.BMIの整数値 #lt; BMI_RANGE_MT.RANGE_MAXの整数値",
          edgeType: "round",
          next: ["101","11"],
          style: "fill:#c6ffc6",
        },
        {
          id: "11",
          text: "HEALTH_INFO登録<br/>HEALTH_INFO.SEQ_USER_ID = パス.ユーザID<br/>HEALTH_INFO.SEQ_BMI_RANGE_MT_ID =<br/>取得したBMI_RANGE_MT.SEQ_BMI_RANGE_MT_ID<br/>身長・体重・計算結果・登録日時を保存",
          edgeType: "round",
          next: ["101","100"],
          style: "fill:#c6ffc6",
        },
        {
          id: "100",
          text: "正常レスポンス生成<br/>ユーザID・登録した健康情報",
          edgeType: "round",
          next: ["999"],
        },
        {
          id: "101",
          text: "エラー通知",
          edgeType: "round",
          next: ["102"],
        },
        {
          id: "102",
          text: "異常レスポンス生成・ログ出力<br/>エラーコード・詳細を設定",
          edgeType: "round",
          next: ["998"],
        },
        {
          id: "998",
          text: "HTTP 400 Bad RequestでJSON返却<br/>result: #quot;1#quot;",
          edgeType: "round",
        },
        {
          id: "999",
          text: "HTTP 200 OKでJSON返却<br/>result: #quot;0#quot;",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
