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
          text: "健康情報照会",
          disabled: true,
          href: "/healthinfoapp/dashboard/healthinfoi/refer",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "照会画面を初期表示<br/>日付は範囲指定を初期選択",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "検索またはページ切替",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "IDの数値・桁数、日付と<br/>指定方法・前後関係を検証",
          edgeType: "diamond",
          next: ["a_error", "a3"],
        },
        {
          id: "a_error",
          text: "不備あり: 照会画面にエラー表示",
          edgeType: "round",
        },
        {
          id: "a3",
          text: "ユーザID・ページング条件を取得",
          edgeType: "round",
          next: ["a4"],
        },
        {
          id: "a4",
          text: "健康情報IDを指定しているか",
          edgeType: "diamond",
          next: ["a5", "a6"],
        },
        {
          id: "a5",
          text: "ID指定: HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>画面入力.健康情報ID",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a6",
          text: "日付指定: HEALTH_INFO検索<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>画面入力.開始日00時00分00秒 AND<br/>画面入力.終了日23時59分59秒<br/>単日指定時の終了日は画面入力.開始日<br/>ORDER BY HEALTH_INFO.HEALTH_INFO_REG_DATE DESC<br/>LIMIT 画面指定ページの開始位置, 設定.表示件数",
          edgeType: "round",
          next: ["a7"],
        },
        {
          id: "a7",
          text: "HEALTH_INFO件数取得・ページング情報設定<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>ID指定時: HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>画面入力.健康情報ID<br/>ID未指定時: HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>画面入力.開始日00時00分00秒 AND<br/>画面入力.終了日23時59分59秒<br/>単日指定時の終了日は画面入力.開始日",
          edgeType: "round",
          next: ["a8"],
        },
        {
          id: "a8",
          text: "表示ページの体重・BMI・標準体重を<br/>作成日時順でグラフ化",
          edgeType: "round",
          next: ["a9"],
        },
        {
          id: "a9",
          text: "検索条件をセッションに保存",
          edgeType: "round",
          next: ["a10"],
        },
        {
          id: "a10",
          text: "照会画面に検索結果を表示<br/>0件は登録なしの案内を表示",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "結果画面でCSVまたはExcelを選択",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "ユーザIDと保存した検索条件を取得",
          edgeType: "round",
          next: ["b_error", "b2"],
        },
        {
          id: "b2",
          text: "HEALTH_INFO再検索・ページ制限なし<br/>HEALTH_INFO.SEQ_USER_ID = セッション.ユーザID<br/>ID指定時: HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>セッション.検索条件.健康情報ID<br/>ID未指定時: HEALTH_INFO.HEALTH_INFO_REG_DATE BETWEEN<br/>セッション.検索条件.開始日00時00分00秒 AND<br/>セッション.検索条件.終了日23時59分59秒<br/>単日指定時の終了日は保存した開始日<br/>日付検索はORDER BY<br/>HEALTH_INFO.HEALTH_INFO_REG_DATE DESC",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "HEALTH_INFO_FILE_SETTING検索<br/>HEALTH_INFO_FILE_SETTING.SEQ_USER_ID =<br/>セッション.ユーザID",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "選択した形式で出力",
          edgeType: "diamond",
          next: ["b5", "b7"],
        },
        {
          id: "b5",
          text: "CSVを応答に出力<br/>同内容を一時ファイルにも保存",
          edgeType: "round",
          next: ["b_error", "b6"],
        },
        {
          id: "b6",
          text: "出力したCSVをS3に保管",
          edgeType: "round",
          next: ["b_error", "b8"],
        },
        {
          id: "b7",
          text: "Excelを生成してダウンロード",
          edgeType: "round",
        },
        {
          id: "b8",
          text: "CSVダウンロード処理を完了",
          edgeType: "round",
        },
        {
          id: "b_error",
          text: "保存条件なし・ファイル出力等の異常<br/>共通エラー処理へ",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>