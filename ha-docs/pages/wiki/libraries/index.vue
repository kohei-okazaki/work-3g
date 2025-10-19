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
                  <th>node api</th>
                  <th>root api</th>
                  <th>track api</th>
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
                      v-if="lib.projects.includes('node api')"
                      color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon
                      v-if="lib.projects.includes('root api')"
                      color="green"
                      >mdi-check</v-icon
                    >
                    <div v-else></div>
                  </td>
                  <td>
                    <v-icon
                      v-if="lib.projects.includes('track api')"
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
          name: "Spring Boot",
          version: "3.5.5",
          description: "健康管理ダッシュボード、健康管理API、管理者API、健康管理バッチで使用するSpring FW",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "AWS-SDK",
          version: "2.31.78",
          description: "AWSのサービスを使うためのライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Jackson",
          version: "2.19.2",
          description: "JSONパースライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "apache poi",
          version: "5.4.0",
          description: "健康管理ダッシュボードで使用する帳票用ライブラリ",
          projects: ["dashboard"],
        },
        {
          name: "logback",
          version: "1.5.18",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するロギングライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "mysql",
          version: "8.4.0",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するMySQLライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "mybatis",
          version: "3.5.19",
          description:
            "健康管理API/ダッシュボード/管理者APIで使用するORMライブラリ",
          projects: ["dashboard", "api", "root api"],
        },
        {
          name: "flyway",
          version: "8.5.12",
          description: "DataBaseのマイグレーションライブラリ",
          projects: ["dashboard", "api", "root api"],
        },
        {
          name: "thymeleaf",
          version: "3.1.3.RELEASE",
          description: "健康管理ダッシュボードで使用するテンプレートエンジン",
          projects: ["dashboard"],
        },
        {
          name: "junit",
          version: "5.12.0",
          description: "Javaのテストライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "mongoDB",
          version: "8.2.1",
          description: "健康情報蓄積APIで使用するNoSQL",
          projects: ["track api"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>