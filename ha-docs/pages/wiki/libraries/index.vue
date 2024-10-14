<template>
  <div>
    <v-row>
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row>
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title class="text-subtitle-1">ライブラリ一覧</v-card-title>
          <v-card-text>
            <v-simple-table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>名前</th>
                  <th>Version</th>
                  <th>内容</th>
                  <th>dashboard</th>
                  <th>api</th>
                  <th>batch</th>
                  <th>nodeapi</th>
                  <th>rootapi</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(lib, i) in libraryList" :key="i">
                  <td>
                    <div>{{ i + 1 }}</div>
                  </td>
                  <td>
                    <div>{{ lib.name }}</div>
                  </td>
                  <td>
                    <div>{{ lib.version }}</div>
                  </td>
                  <td>
                    <div>{{ lib.description }}</div>
                  </td>
                  <td>
                    <v-icon
                      v-if="lib.projects.includes('dashboard')"
                      color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon v-if="lib.projects.includes('api')" color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon v-if="lib.projects.includes('batch')" color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon
                      v-if="lib.projects.includes('nodeapi')"
                      color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon
                      v-if="lib.projects.includes('rootapi')"
                      color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
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
          text: "ライブラリ一覧",
          disabled: true,
          href: "/wiki/libraries",
        },
      ],
      libraryList: [
        {
          name: "Spring Framework",
          version: "6.1.13",
          description: "健康管理API/ダッシュボードで使用するWeb FW",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "Spring Boot",
          version: "3.3.3",
          description: "管理者APIと健康管理バッチで使用するSpring FW",
          projects: ["rootapi", "batch"],
        },
        {
          name: "express",
          version: "4.17.1",
          description: "Node.jsで使用するWeb FW",
          projects: ["nodeapi"],
        },
        {
          name: "AWS-SDK",
          version: "1.12.190",
          description: "AWSのサービスを使うためのライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "Jackson",
          version: "2.17.2",
          description: "JSONパースライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "jwt",
          version: "0.9.1",
          description: "JSON Web Tokenライブラリ",
          projects: ["rootapi"],
        },
        {
          name: "jsonwebtoken",
          version: "8.5.1",
          description: "JSON Web Tokenライブラリ",
          projects: ["nodeapi"],
        },
        {
          name: "apache poi",
          version: "5.3.0",
          description: "健康管理ダッシュボードで使用する帳票用ライブラリ",
          projects: ["dashboard"],
        },
        {
          name: "logback",
          version: "1.5.8",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するロギングライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "mysql",
          version: "8.4.0",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するMySQLライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "mybatis",
          version: "3.5.16",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するORMライブラリ",
          projects: ["dashboard", "api", "rootapi"],
        },
        {
          name: "flyway",
          version: "8.5.12",
          description: "DataBaseのマイグレーションライブラリ",
          projects: ["dashboard", "api", "rootapi"],
        },
        {
          name: "thymeleaf",
          version: "3.1.2.RELEASE",
          description: "健康管理ダッシュボードで使用するテンプレートエンジン",
          projects: ["dashboard"],
        },
        {
          name: "slack",
          version: "1.4.0",
          description: "JavaからSlackAPIを呼び出すためのライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
        {
          name: "junit",
          version: "5.11.0",
          description: "Javaのテストライブラリ",
          projects: ["dashboard", "api", "rootapi", "batch"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>