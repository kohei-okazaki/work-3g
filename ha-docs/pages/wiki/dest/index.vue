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
    <v-row
      align="center"
      v-for="(dest, i) in destList"
      :key="i"
    >
      <v-col sm="12">
        <v-card>
          <v-card-title>{{ dest.envName }}</v-card-title>
          <v-divider />
          <v-card-text d-flex>
            <v-simple-table>
              <template v-slot:default>
                <tr v-for="(item, i) in dest.items" :key="i">
                  <th>
                    <div class="text-left">{{ item.name }}</div>
                  </th>
                  <td>
                    <v-btn
                      text
                      color="link"
                      min-height="20"
                      class="x-small post-link align-center py-1 px-2"
                      rel="noopener noreferrer"
                      >{{ item.host }}</v-btn
                    >
                  </td>
                </tr>
              </template>
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
          text: "接続先一覧",
          disabled: true,
          href: "/wiki/dest",
        },
      ],
      destList: [
        {
          envName: "ローカル環境",
          items: [
            {
              name: "健康管理ダッシュボード",
              host: "localhost:8080",
            },
            {
              name: "健康管理API",
              host: "localhost:8081",
            },
            {
              name: "管理者用API",
              host: "localhost:8082",
            },
            {
              name: "管理者用サイト",
              host: "localhost:8083",
            },
            {
              name: "健康情報計算API",
              host: "https://XXXX.execute-api.ap-northeast-1.amazonaws.com/dev/",
            },
            {
              name: "健康管理ドキュメント",
              host: "localhost:8085",
            },
            {
              name: "健康情報蓄積API",
              host: "localhost:8086",
            },
            {
              name: "データベース",
              host: "localhost:3306",
            },
          ],
        },
        {
          envName: "dev1環境",
          items: [
            {
              name: "健康管理ダッシュボード",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "健康管理API",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "管理者用API",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "管理者用サイト",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "健康情報計算API",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "健康管理ドキュメント",
              host:
                "healthinfo-app-docs.s3-website-ap-northeast-1.amazonaws.com/",
            },
            {
              name: "健康情報蓄積API",
              host: "TODO 環境構築後、記載",
            },
            {
              name: "データベース",
              host: "TODO 環境構築後、記載",
            },
          ],
        },
      ],
    };
  },
};
</script>
<style scoped>
</style>