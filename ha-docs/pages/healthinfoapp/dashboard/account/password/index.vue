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
          text: "パスワード再設定",
          disabled: true,
          href: "/healthinfoapp/dashboard/account/password",
        },
      ],
      flow: [
        {
          id: "a0",
          text: "パスワード再設定<br>メール入力画面",
          edgeType: "circle",
          next: ["a1"],
        },
        {
          id: "a1",
          text: "画面表示",
          edgeType: "round",
          next: ["a2"],
        },
        {
          id: "a2",
          text: "メールアドレス入力フラグを設定",
          edgeType: "round",
        },

        {
          id: "b0",
          text: "パスワード再設定<br>メール入力画面",
          edgeType: "circle",
          next: ["b1"],
        },
        {
          id: "b1",
          text: "送信ボタン押下",
          edgeType: "round",
          next: ["b2"],
        },
        {
          id: "b2",
          text: "入力チェック",
          edgeType: "round",
          next: ["b3"],
        },
        {
          id: "b3",
          text: "USER 検索",
          edgeType: "round",
          next: ["b4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b4",
          text: "USER_RECOVERY_TOKEN 登録",
          edgeType: "round",
          next: ["b5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "b5",
          text: "SES.パスワード再設定メール 送信",
          edgeType: "round",
          next: ["b6"],
        },
        {
          id: "b6",
          text: "送信完了メッセージ表示",
          edgeType: "round",
        },

        {
          id: "b2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "メール入力チェック",
          next: ["b2.2"],
        },
        {
          id: "b2.2",
          text: "桁数チェック",
          edgeType: "round",
          group: "メール入力チェック",
          next: ["b2.3"],
        },
        {
          id: "b2.3",
          text: "形式チェック",
          edgeType: "round",
          group: "メール入力チェック",
        },

        {
          id: "c0",
          text: "パスワード再設定<br>メールリンク",
          edgeType: "circle",
          next: ["c1"],
        },
        {
          id: "c1",
          text: "リクエストチェック",
          edgeType: "round",
          next: ["c2"],
        },
        {
          id: "c2",
          text: "USER 検索",
          edgeType: "round",
          next: ["c3"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c3",
          text: "有効なUSER_RECOVERY_TOKEN 検索",
          edgeType: "round",
          next: ["c4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "c4",
          text: "パスワード再設定<br>入力画面",
          edgeType: "circle",
        },

        {
          id: "d0",
          text: "パスワード再設定<br>入力画面",
          edgeType: "circle",
          next: ["d1"],
        },
        {
          id: "d1",
          text: "確認ボタン押下",
          edgeType: "round",
          next: ["d2"],
        },
        {
          id: "d2",
          text: "入力チェック",
          edgeType: "round",
          next: ["d3"],
        },
        {
          id: "d3",
          text: "パスワード一致チェック",
          edgeType: "round",
          next: ["d4"],
        },
        {
          id: "d4",
          text: "USER 検索",
          edgeType: "round",
          next: ["d5"],
          style: "fill:#c6ffc6",
        },
        {
          id: "d5",
          text: "パスワード再設定<br>確認画面",
          edgeType: "circle",
        },

        {
          id: "d2.1",
          text: "必須チェック",
          edgeType: "round",
          group: "パスワード入力チェック",
          next: ["d2.2"],
        },
        {
          id: "d2.2",
          text: "型チェック",
          edgeType: "round",
          group: "パスワード入力チェック",
          next: ["d2.3"],
        },
        {
          id: "d2.3",
          text: "桁数チェック",
          edgeType: "round",
          group: "パスワード入力チェック",
          next: ["d2.4"],
        },
        {
          id: "d2.4",
          text: "形式チェック",
          edgeType: "round",
          group: "パスワード入力チェック",
        },

        {
          id: "e0",
          text: "パスワード再設定<br>確認画面",
          edgeType: "circle",
          next: ["e1"],
        },
        {
          id: "e1",
          text: "更新ボタン押下",
          edgeType: "round",
          next: ["e2"],
        },
        {
          id: "e2",
          text: "USER 検索",
          edgeType: "round",
          next: ["e3"],
          style: "fill:#c6ffc6",
        },
        {
          id: "e3",
          text: "USER 更新",
          edgeType: "round",
          next: ["e4"],
          style: "fill:#c6ffc6",
        },
        {
          id: "e4",
          text: "ログイン画面にリダイレクト",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>