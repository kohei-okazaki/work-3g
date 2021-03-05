<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle title="開発用のページ" />
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="9">
        <v-card>
          <v-card-title>健康管理ダッシュボード</v-card-title>
          <v-card-text d-flex>
            <v-simple-table>
              <template v-slot:default>
                <tr v-for="(link, i) in healthInfoDashboardLinks" :key="i">
                  <th>
                    <div class="text-left">{{ link.name }}</div>
                  </th>
                  <td>
                    <v-btn
                      :href="link.url"
                      text
                      color="link"
                      min-height="20"
                      class="x-small post-link align-center py-1 px-2"
                      >{{ link.url }}</v-btn
                    >
                  </td>
                </tr>
              </template>
            </v-simple-table>
          </v-card-text></v-card
        ></v-col
      >
    </v-row>
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="9">
        <v-card>
          <v-card-title>管理者サイト</v-card-title>
          <v-card-text d-flex>
            <v-simple-table>
              <template v-slot:default>
                <tr v-for="(link, i) in rootAppLinks" :key="i">
                  <th>
                    <div class="text-left">{{ link.name }}</div>
                  </th>
                  <td>
                    <v-btn
                      :href="link.url"
                      text
                      color="link"
                      min-height="20"
                      class="x-small post-link align-center py-1 px-2"
                      >{{ link.url }}</v-btn
                    >
                  </td>
                </tr>
              </template>
            </v-simple-table>
          </v-card-text></v-card
        ></v-col
      >
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
      healthInfoDashboardLinks: [
        {
          name: "local",
          url: "http://localhost:8080/login/index",
        },
        {
          name: "EC2",
          url:
            "http://ec2-dashboard.ap-northeast-1.elasticbeanstalk.com/login/index",
        },
      ],
      rootAppLinks: [
        {
          name: "local",
          url: "http://localhost:8083/login",
        },
        {
          name: "EC2",
          url: "",
        },
      ],
    };
  },
};
</script>

<style scoped>
.post-link {
  display: inline-block;
  word-break: break-all;
  padding: 2px 8px;
  border-radius: 5px;
  text-transform: none !important;
  white-space: normal;
  max-width: calc(100% - 30px);
  height: inherit !important;
}
</style>