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
          text: "アカウント設定",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/setting",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "設定画面を開く",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "ログイン済みか",
          edgeType: "diamond",
          next: ["a2", "a3"],
        },
        {
          id: "a2",
          text: "未ログイン：ログイン画面へ",
          edgeType: "circle",
        },
        {
          id: "a3",
          text: "初期表示・確認・確定の共通取得<br/>ログイン済み: USER検索・関連設定を取得<br/>USER.SEQ_USER_ID = セッション.ユーザID<br/>USER.DELETE_FLAG = false<br/>LEFT JOIN HEALTH_INFO_FILE_SETTING<br/>ON USER.SEQ_USER_ID =<br/>HEALTH_INFO_FILE_SETTING.SEQ_USER_ID<br/>LEFT JOIN USER_HEALTH_GOAL<br/>ON USER_HEALTH_GOAL.SEQ_USER_ID =<br/>USER.SEQ_USER_ID<br/>AND USER_HEALTH_GOAL.DELETE_FLAG = false",
          edgeType: "round",
          next: ["a4", "a5"],
        },
        {
          id: "a4",
          text: "取得成功：現在の設定を入力画面に表示",
          edgeType: "circle",
        },
        {
          id: "a5",
          text: "取得失敗：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "b0",
          text: "設定入力画面で変更し確認を押下",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "必須・形式・日付・範囲・設定値を検証",
          edgeType: "diamond",
          next: ["b2", "b3"],
        },
        {
          id: "b2",
          text: "入力不正：エラー付きで入力画面を再表示",
          edgeType: "circle",
        },
        {
          id: "b3",
          text: "入力正常：変更内容をセッションに保存<br/>二重送信防止情報を発行",
          edgeType: "round",
          next: ["b4"],
        },
        {
          id: "b4",
          text: "設定確認画面を表示",
          edgeType: "circle",
        },
        {
          id: "c0",
          text: "設定確認画面",
          edgeType: "circle",
          next: ["c1", "c2"],
        },
        {
          id: "c1",
          text: "戻る：ブラウザ履歴で入力画面へ",
          edgeType: "circle",
        },
        {
          id: "c2",
          text: "確定：二重送信防止情報と<br/>セッションの変更内容を確認",
          edgeType: "diamond",
          next: ["c3", "c4"],
        },
        {
          id: "c3",
          text: "手順不正・変更内容なし：共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "c4",
          text: "保持あり: USER検索後にハッシュ化して更新<br/>USER.SEQ_USER_ID = セッション.変更内容.ユーザID<br/>HEALTH_INFO_FILE_SETTING検索・更新<br/>HEALTH_INFO_FILE_SETTING.SEQ_USER_ID =<br/>セッション.変更内容.ユーザID",
          edgeType: "round",
          next: ["c5"],
        },
        {
          id: "c5",
          text: "USER_HEALTH_GOAL検索後に論理削除<br/>USER_HEALTH_GOAL.SEQ_USER_ID =<br/>セッション.変更内容.ユーザID<br/>USER_HEALTH_GOAL.DELETE_FLAG = false",
          edgeType: "round",
          next: ["c6"],
        },
        {
          id: "c6",
          text: "アカウントを削除するか",
          edgeType: "diamond",
          next: ["c7", "c8"],
        },
        {
          id: "c7",
          text: "削除する：目標体重は再登録しない",
          edgeType: "round",
          next: ["c9"],
        },
        {
          id: "c8",
          text: "削除しない: 新しい目標体重を<br/>USER_HEALTH_GOALに登録",
          edgeType: "round",
          next: ["c9"],
        },
        {
          id: "c9",
          text: "同じトランザクションでの更新結果",
          edgeType: "diamond",
          next: ["c10", "c11"],
        },
        {
          id: "c10",
          text: "更新例外：全件を取り消して<br/>共通エラー画面",
          edgeType: "circle",
        },
        {
          id: "c11",
          text: "更新成功：確定して<br/>セッションの変更内容を削除",
          edgeType: "round",
          next: ["c12"],
        },
        {
          id: "c12",
          text: "アカウント削除時は<br/>セッションのユーザIDも削除",
          edgeType: "round",
          next: ["c13"],
        },
        {
          id: "c13",
          text: "設定完了画面を表示",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>