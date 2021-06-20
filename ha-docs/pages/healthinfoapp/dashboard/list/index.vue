<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
    <AppViewList :viewArray="viewArray" />
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppViewList from "~/components/AppViewList.vue";

export default {
  // 健康管理Dashboardのレイアウトを適用
  layout: "healthinfoappDashboardLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppViewList,
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
          text: "画面一覧",
          disabled: true,
          href: "/healthinfoapp/dashboard/list",
        },
      ],
      viewArray: [
        {
          name: "ログイン画面",
          description: "健康管理アプリへのログイン機能",
          loginAuth: false,
          leads: [
            {
              name: "TOP画面",
              loginAuth: true,
              description: "ログインボタン押下時",
            },
            {
              name: "アカウント登録画面",
              loginAuth: false,
              description: "アカウント登録ボタン押下時",
            },
            {
              name: "パスワード再設定メール送信画面",
              loginAuth: false,
              description: "パスワードを忘れた場合リンク押下時",
            },
          ],
        },
        {
          name: "TOP画面",
          description: "健康管理アプリのTOP画面",
          loginAuth: true,
          leads: [
            {
              name: "ログイン画面",
              loginAuth: false,
              description: "ログアウトリンク押下時",
            },
            {
              name: "健康情報登録画面",
              loginAuth: true,
              description: "健康情報登録リンク押下時",
            },
            {
              name: "健康情報照会画面",
              loginAuth: true,
              description: "健康情報照会リンク押下時",
            },
            {
              name: "健康情報詳細画面",
              loginAuth: true,
              description: "対象の健康情報の詳細リンク押下時",
            },
            {
              name: "健康情報一括登録画面",
              loginAuth: true,
              description: "健康情報一括登録リンク押下時",
            },
            {
              name: "アカウント設定画面",
              loginAuth: true,
              description: "アカウント設定リンク押下時",
            },
            {
              name: "カロリー計算画面",
              loginAuth: true,
              description: "カロリー計算リンク押下時",
            },
            {
              name: "肺活量計算画面",
              loginAuth: true,
              description: "肺活量計算リンク押下時",
            },
            {
              name: "お知らせ情報一覧画面",
              loginAuth: true,
              description: "お知らせ情報一覧のアイコン押下時",
            },
          ],
        },
        {
          name: "健康情報登録画面",
          description: "ユーザの健康情報の登録機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "健康情報照会画面",
          description: "ユーザの健康情報の照会機能",
          loginAuth: true,
          leads: [
            {
              name: "健康情報詳細画面",
              loginAuth: true,
              description: "対象の健康情報の詳細リンク押下時",
            },
          ],
        },
        {
          name: "健康情報詳細画面",
          description: "ユーザの健康情報の詳細情報を確認するための機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "健康情報一括登録画面",
          description:
            "ファイルをアップロードし、ユーザの健康情報をまとめて登録する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "アカウント登録画面",
          description: "ユーザの情報を登録する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "アカウント設定画面",
          description: "ユーザの設定情報を変更/確認する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "パスワード再設定画面",
          description: "ユーザのパスワードを変更する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "カロリー計算画面",
          description: "ユーザのカロリー消費情報を確認する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "肺活量計算画面",
          description: "ユーザの肺活量を確認する機能",
          loginAuth: true,
          leads: [],
        },
        {
          name: "お知らせ情報一覧画面",
          description:
            "アプリケーション内で配信されるお知らせ情報を確認する機能",
          loginAuth: true,
          leads: [],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>