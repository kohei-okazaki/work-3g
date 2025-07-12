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
    <v-row align="center" v-for="(link, i) in linkList" :key="i">
      <v-col sm="12">
        <v-card>
          <v-card-title>{{ link.name }}</v-card-title>
          <v-divider />
          <v-card-text d-flex>
            <v-simple-table>
              <template v-slot:default>
                <tr v-for="(env, i) in link.envList" :key="i">
                  <th>
                    <div class="text-left">{{ env.envName }}</div>
                  </th>
                  <td>
                    <v-btn
                      :href="env.url"
                      text
                      color="link"
                      min-height="20"
                      class="x-small post-link align-center py-1 px-2"
                      target="_blank"
                      rel="noopener noreferrer"
                      >{{ env.url }}</v-btn
                    >
                  </td>
                </tr>
              </template>
            </v-simple-table>
          </v-card-text></v-card
        ></v-col
      >
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-card>
          <v-card-title>その他</v-card-title>
          <v-divider />
          <v-card-text d-flex>
            <v-simple-table>
              <template v-slot:default>
                <tr v-for="(other, i) in otherList" :key="i">
                  <th>
                    <div class="text-left">{{ other.name }}</div>
                  </th>
                  <td>
                    <v-btn
                      :href="other.url"
                      text
                      color="link"
                      min-height="20"
                      class="x-small post-link align-center py-1 px-2"
                      target="_blank"
                      rel="noopener noreferrer"
                      >{{ other.url }}</v-btn
                    >
                  </td>
                </tr>
              </template>
            </v-simple-table>
          </v-card-text></v-card
        >
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
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
          text: "開発用のページ",
          disabled: true,
          href: "/locallink",
        },
      ],
      linkList: [
        {
          name: "健康管理ダッシュボード",
          envList: [
            {
              envName: "local環境",
              url: "http://localhost:8080/login",
            },
            {
              envName: "dev1環境",
              url:
                "http://dev1-dashboard.ap-northeast-1.elasticbeanstalk.com/login",
            },
          ],
        },
        {
          name: "管理者サイト",
          envList: [
            {
              envName: "local環境",
              url: "http://localhost:8083/login",
            },
            {
              envName: "dev1環境",
              url: "",
            },
          ],
        },
      ],
      otherList: [
        {
          name: "Github",
          url: "https://github.com/kohei-okazaki/work-3g",
        },
        {
          name: "健康管理アプリDocs",
          url:
            "http://healthinfo-app-docs.s3-website-ap-northeast-1.amazonaws.com/",
        },
        {
          name: "AWS",
          url: "https://aws.amazon.com/jp/",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>