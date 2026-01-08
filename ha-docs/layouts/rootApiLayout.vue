<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title class="text-subtitle-2" v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar :clipped-left="clipped" fixed app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon>mdi-{{ `chevron-${miniVariant ? "right" : "left"}` }}</v-icon>
      </v-btn>
      <v-toolbar-title v-text="title" />
      <v-spacer />
    </v-app-bar>
    <v-main class="base">
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <AppScroll />
    <v-footer :absolute="fixed" app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
import AppScroll from "~/components/AppScroll.vue";

export default {
  components: {
    AppScroll,
  },
  data: function () {
    return {
      clipped: true,
      drawer: true,
      fixed: true,
      items: [
        {
          icon: "mdi-apps",
          title: "TOPページ",
          to: "/",
        },
        {
          icon: "mdi-format-list-bulleted",
          title: "IF一覧",
          to: "/root/api/interface",
        },
        {
          icon: "mdi-api",
          title: "ヘルスチェックAPI",
          to: "/root/api/healthcheck",
        },
        {
          icon: "mdi-api",
          title: "ログインAPI",
          to: "/root/api/login",
        },
        {
          icon: "mdi-api",
          title: "Top情報取得API",
          to: "/root/api/top",
        },
        {
          icon: "mdi-api",
          title: "Top情報(健康情報)取得API",
          to: "/root/api/top/healthinfo",
        },
        {
          icon: "mdi-api",
          title: "Top情報(アカウント情報)取得API",
          to: "/root/api/top/account",
        },
        {
          icon: "mdi-api",
          title: "管理ユーザ取得API",
          to: "/root/api/user",
        },
        {
          icon: "mdi-api",
          title: "管理ユーザ作成API",
          to: "/root/api/user/entry",
        },
        {
          icon: "mdi-api",
          title: "管理ユーザ編集API",
          to: "/root/api/user/edit",
        },
        {
          icon: "mdi-api",
          title: "アカウント情報一覧取得API",
          to: "/root/api/account",
        },
        {
          icon: "mdi-api",
          title: "アカウント情報編集API",
          to: "/root/api/account/edit",
        },
        {
          icon: "mdi-api",
          title: "API通信情報一覧API",
          to: "/root/api/apidata",
        },
        {
          icon: "mdi-api",
          title: "健康情報一覧取得API",
          to: "/root/api/healthinfo",
        },
        {
          icon: "mdi-api",
          title: "健康情報編集API",
          to: "/root/api/healthinfo/edit",
        },
        {
          icon: "mdi-api",
          title: "お知らせ情報一覧取得API",
          to: "/root/api/news",
        },
        {
          icon: "mdi-api",
          title: "お知らせ情報登録API",
          to: "/root/api/news/entry",
        },
        {
          icon: "mdi-api",
          title: "お知らせ情報編集API",
          to: "/root/api/news/edit",
        },
        {
          icon: "mdi-api",
          title: "お知らせ情報削除API",
          to: "/root/api/news/delete",
        },
        {
          icon: "mdi-api",
          title: "メモ情報一覧取得API",
          to: "/root/api/note",
        },
        {
          icon: "mdi-api",
          title: "メモ情報登録API",
          to: "/root/api/note/entry",
        },
        {
          icon: "mdi-api",
          title: "メモ情報編集API",
          to: "/root/api/note/edit",
        },
        {
          icon: "mdi-api",
          title: "メモ情報削除API",
          to: "/root/api/note/delete",
        },
        {
          icon: "mdi-api",
          title: "権限マスタ一覧取得API",
          to: "/root/api/roles",
        },
        {
          icon: "mdi-api",
          title: "問い合わせ情報一覧取得API",
          to: "/root/api/inquiry",
        },
        {
          icon: "mdi-api",
          title: "問い合わせ情報編集API",
          to: "/root/api/inquiry/edit",
        },
        {
          icon: "mdi-api",
          title: "問い合わせステータスマスタ一覧取得API",
          to: "/root/api/inquiry/status",
        },
        {
          icon: "mdi-api",
          title: "問い合わせ情報通知API",
          to: "/root/api/inquiry/notice",
        },
        {
          icon: "mdi-api",
          title: "Job履歴情報一覧取得API",
          to: "/root/api/job",
        },
      ],
      miniVariant: false,
      right: true,
      title: "健康管理ドキュメント",
    };
  },
};
</script>

<style>
</style>