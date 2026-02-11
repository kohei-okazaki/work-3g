<template>
  <div>
    <v-row>
      <v-col class="text-center" sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row>
      <v-col class="text-left" sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">プロジェクト一覧</v-card-title>
          <v-card-text>
            <v-simple-table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>概要</th>
                  <th>詳細</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(project, i) in projects" :key="i">
                  <td>
                    <div>{{ i + 1 }}</div>
                  </td>
                  <td>
                    <div>{{ project.name }}</div>
                  </td>
                  <td>
                    <div>{{ project.description }}</div>
                  </td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">プロジェクト依存関係図</v-card-title>
          <v-card-text align="center">
            <v-img src="/wiki/project.png" width="75%" />
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">ローカル環境構成図</v-card-title>
          <v-card-text align="center">
            <v-img src="/wiki/project-config-local.png" width="70%" />
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">dev1環境構成図</v-card-title>
          <v-card-text align="center">
            <v-img src="/wiki/project-config-ec2.png" width="70%" />
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">健康情報登録フロー</v-card-title>
          <v-card-text align="center">
            <v-img src="/wiki/health-info-regist-flow.png" width="70%" />
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">使用言語一覧</v-card-title>
          <v-card-subtitle class="text-subtitle-2">本アプリで使用しているプログラミング言語の一覧<br />FWやライブラリのバージョンについては<nuxt-link
              to="/wiki/libraries">ライブラリ一覧</nuxt-link>を参照</v-card-subtitle>
          <v-card-text>
            <v-simple-table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>言語名</th>
                  <th>バージョン</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(language, i) in languages" :key="i">
                  <td>
                    <div>{{ i + 1 }}</div>
                  </td>
                  <td>
                    <div>{{ language.name }}</div>
                  </td>
                  <td>
                    <div>{{ language.version }}</div>
                  </td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // Wikiのレイアウトを適用
  layout: "wikiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
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
          text: "Wiki",
          disabled: false,
          href: "/wiki",
        },
        {
          text: "プロジェクト構成",
          disabled: true,
          href: "/wiki/project",
        },
      ],
      projects: [
        {
          name: "ha-api",
          description: "アプリが提供するAPI機能を管理するmavenプロジェクト",
        },
        {
          name: "ha-asset",
          description:
            "DDLなどアプリで必要なファイルを管理するドキュメントプロジェクト",
        },
        {
          name: "ha-batch",
          description: "アプリが提供するバッチ機能を管理するmavenプロジェクト",
        },
        {
          name: "ha-build",
          description:
            "ローカル環境のビルド機能を管理するドキュメントプロジェクト",
        },
        {
          name: "ha-business",
          description: "業務ロジック機能を管理するmavenプロジェクト",
        },
        {
          name: "ha-common",
          description: "共通処理を管理するmavenプロジェクト",
        },
        {
          name: "ha-dashboard",
          description: "アプリが提供するダッシュボード画面機能をまとめたmavenプロジェクト(ユーザ向けシステム)",
        },
        {
          name: "ha-db",
          description: "データベースアクセスを管理するmavenプロジェクト",
        },
        {
          name: "ha-docs",
          description: "健康管理アプリの静的ドキュメントプロジェクト",
        },
        {
          name: "ha-node",
          description:
            "lambdaで動作する健康情報計算APIを管理するJavaScriptプロジェクト",
        },
        {
          name: "ha-pom",
          description: "アプリのmavenプロジェクトで共通で読み込む親pomプロジェクト",
        },
        {
          name: "ha-root",
          description: "健康管理アプリの管理サイト(管理者向けシステム)",
        },
        {
          name: "ha-track",
          description: "健康情報を蓄積するPythonプロジェクト",
        },
        {
          name: "ha-tool",
          description: "健康情報アプリのDDLなどを自動生成するmavenプロジェクト",
        },
        {
          name: "ha-tools",
          description: "健康情報アプリのDDLなどを自動生成するツールプロジェクト",
        },
      ],
      languages: [
        {
          name: "Java",
          version: "21.0.8",
        },
        {
          name: "Node.js",
          version: "20.19.5",
        },
        {
          name: "Python",
          version: "3.13.8",
        },
      ],
    };
  },
};
</script>
<style scoped></style>