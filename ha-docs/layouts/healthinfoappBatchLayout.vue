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
          title: "バッチ一覧",
          to: "/healthinfoapp/batch/list",
        },
        {
          icon: "mdi-server",
          title: "健康情報一括登録バッチ",
          to: "/healthinfoapp/batch/healthinfo/regist",
        },
        {
          icon: "mdi-server",
          title: "月次健康情報集計バッチ",
          to: "/healthinfoapp/batch/healthinfo/monthlySummary",
        },
        {
          icon: "mdi-server",
          title: "ヘルスチェックバッチ",
          to: "/healthinfoapp/batch/healthcheck",
        },
        {
          icon: "mdi-server",
          title: "健康情報連携バッチ",
          to: "/healthinfoapp/batch/healthinfo/migrate",
        },
        {
          icon: "mdi-server",
          title: "AWS SQS取込バッチ",
          to: "/healthinfoapp/batch/sqs/import",
        },
        {
          icon: "mdi-server",
          title: "日次データ分析連携JobSet",
          to: "/healthinfoapp/batch/daily/jobset",
        },
        {
          icon: "mdi-server",
          title: "日次健康情報データ分析連携バッチ",
          to: "/healthinfoapp/batch/daily/healthinfo",
        },
        {
          icon: "mdi-server",
          title: "日次ユーザ情報データ分析連携バッチ",
          to: "/healthinfoapp/batch/daily/user",
        },
        {
          icon: "mdi-server",
          title: "日次API通信ログデータ分析連携バッチ",
          to: "/healthinfoapp/batch/daily/api_log",
        },
        {
          icon: "mdi-server",
          title: "日次API通信ログデータ分析連携バッチ",
          to: "/healthinfoapp/batch/daily/batch_log",
        },
      ],
      miniVariant: false,
      right: true,
      title: "健康管理ドキュメント",
    };
  },
};
</script>

<style></style>
