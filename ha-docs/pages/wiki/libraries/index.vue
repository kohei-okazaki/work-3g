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
            <p class="mb-4">
              各プロジェクトが直接利用、または依存定義・実行設定で明示している主要ライブラリを記載しています。推移依存は除外しています。
            </p>
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
                  <th>calc_api</th>
                  <th>root api</th>
                  <th>root front</th>
                  <th>track api</th>
                  <th>docs</th>
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
                      v-if="lib.projects.includes('calc_api')"
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
                      v-if="lib.projects.includes('root front')"
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
                  <td>
                    <v-icon v-if="lib.projects.includes('docs')" color="green"
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
          version: "4.0.6",
          description: "Javaアプリケーションのフレームワーク",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Spring Security",
          version: "7.0.5",
          description: "認証・認可を行うセキュリティフレームワーク",
          projects: ["dashboard", "api", "root api"],
        },
        {
          name: "Spring Batch",
          version: "6.0.3",
          description: "バッチ処理フレームワーク",
          projects: ["batch"],
        },
        {
          name: "Spring Data Commons",
          version: "4.0.5",
          description: "ページングなどのデータアクセス共通機能",
          projects: ["dashboard", "root api"],
        },
        {
          name: "AWS SDK for Java",
          version: "2.44.8",
          description: "S3、SES、SQS、SSMなどのAWSサービスを利用するSDK",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Jackson",
          version: "3.1.2 / 2.21.2",
          description: "JSONのシリアライズ・デシリアライズ用ライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Apache POI",
          version: "5.5.1",
          description: "Excel帳票の生成用ライブラリ",
          projects: ["dashboard"],
        },
        {
          name: "Logback",
          version: "1.5.32",
          description: "Javaアプリケーションのロギングライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "MySQL Connector/J",
          version: "8.4.0",
          description: "MySQLへ接続するJDBCドライバ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "MyBatis",
          version: "3.5.19",
          description: "SQLマッピングフレームワーク",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "MyBatis Spring",
          version: "4.0.0",
          description: "MyBatisとSpringを連携するライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Flyway",
          version: "11.14.1 / plugin 11.18.0",
          description: "データベースマイグレーション用ライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Thymeleaf",
          version: "3.1.5.RELEASE",
          description: "HTMLテンプレートエンジン",
          projects: ["dashboard"],
        },
        {
          name: "FreeMarker",
          version: "2.3.34",
          description: "メール本文のテンプレートエンジン",
          projects: ["dashboard", "batch"],
        },
        {
          name: "Apache Commons DBCP",
          version: "2.13.0",
          description: "JDBCコネクションプール",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Apache Commons Lang",
          version: "3.19.0",
          description: "Java標準APIを補完するユーティリティライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "OkHttp",
          version: "5.3.2",
          description: "Slack APIなどへのHTTP通信用ライブラリ",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "JJWT",
          version: "0.13.0",
          description: "JWTの生成・検証用ライブラリ",
          projects: ["root api"],
        },
        {
          name: "JUnit",
          version: "6.0.3",
          description: "Javaのテストフレームワーク",
          projects: ["dashboard", "api", "root api", "batch"],
        },
        {
          name: "Python標準ライブラリ",
          version: "Python 3.14",
          description: "計算Lambdaで使用するjson・math（外部依存なし）",
          projects: ["calc_api"],
        },
        {
          name: "Django",
          version: "4.2.27",
          description: "トラッキングAPIのWebフレームワーク",
          projects: ["track api"],
        },
        {
          name: "Django REST Framework",
          version: "3.16.1",
          description: "DjangoでREST APIを構築するフレームワーク",
          projects: ["track api"],
        },
        {
          name: "boto3",
          version: "1.42.96",
          description: "DynamoDBへ接続するAWS SDK for Python",
          projects: ["track api"],
        },
        {
          name: "Gunicorn",
          version: "23.0.0",
          description: "トラッキングAPIのWSGI HTTPサーバ",
          projects: ["track api"],
        },
        {
          name: "Ruff",
          version: "0.15.12",
          description: "PythonのLint・フォーマットツール",
          projects: ["track api"],
        },
        {
          name: "Vue",
          version: "3.5.38",
          description: "フロントエンドUIフレームワーク",
          projects: ["root front", "docs"],
        },
        {
          name: "Nuxt",
          version: "4.4.8",
          description: "Vueベースのアプリケーションフレームワーク",
          projects: ["root front", "docs"],
        },
        {
          name: "Vuetify",
          version: "4.1.2",
          description: "Vue向けUIコンポーネントライブラリ",
          projects: ["root front", "docs"],
        },
        {
          name: "Material Design Icons",
          version: "7.4.47",
          description: "画面表示用アイコンフォント（@mdi/font）",
          projects: ["root front", "docs"],
        },
        {
          name: "Axios",
          version: "1.18.0",
          description: "HTTPクライアント",
          projects: ["root front"],
        },
        {
          name: "cross-env",
          version: "10.1.0",
          description: "環境変数をOS非依存で設定する実行ツール",
          projects: ["root front"],
        },
        {
          name: "Sass",
          version: "1.101.0",
          description: "CSSプリプロセッサ",
          projects: ["root front", "docs"],
        },
        {
          name: "TypeScript",
          version: "6.0.3",
          description: "JavaScriptへ型を追加する開発言語・コンパイラ",
          projects: ["root front"],
        },
        {
          name: "vue-tsc",
          version: "3.3.5",
          description: "Vue・TypeScriptの型チェックツール",
          projects: ["root front"],
        },
        {
          name: "vuetify-nuxt-module",
          version: "0.19.5",
          description: "NuxtへVuetifyを統合するモジュール",
          projects: ["docs"],
        },
        {
          name: "Mermaid",
          version: "11.15.0",
          description: "ドキュメント内の図を生成するライブラリ",
          projects: ["docs"],
        },
        {
          name: "PostCSS",
          version: "8.5.15",
          description: "CSS変換ツール",
          projects: ["docs"],
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
