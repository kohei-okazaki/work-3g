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
      class="develop-link-row"
      v-for="(link, i) in linkList"
      :key="i"
    >
      <v-col sm="12">
        <v-card>
          <v-card-title>{{ link.name }}</v-card-title>
          <v-divider />
          <v-card-text class="develop-link-card-body">
            <v-simple-table class="develop-link-table">
              <template v-slot:default>
                <tr v-for="(env, i) in link.envList" :key="i">
                  <th class="develop-link-label-cell">
                    <div class="text-left">{{ env.envName }}</div>
                  </th>
                  <td class="develop-link-url-cell">
                    <a
                      v-if="isUrl(env.url)"
                      :href="env.url"
                      class="develop-link-url"
                      target="_blank"
                      rel="noopener noreferrer"
                      >{{ env.url }}</a
                    >
                    <span v-else class="develop-link-url develop-link-url-text">
                      {{ env.url }}
                    </span>
                  </td>
                </tr>
              </template>
            </v-simple-table>
          </v-card-text></v-card
        ></v-col
      >
    </v-row>

    <v-row align="center" class="develop-link-row">
      <v-col sm="12">
        <v-card>
          <v-card-title>その他</v-card-title>
          <v-divider />
          <v-card-text class="develop-link-card-body">
            <v-simple-table class="develop-link-table">
              <template v-slot:default>
                <tr v-for="(other, i) in otherList" :key="i">
                  <th class="develop-link-label-cell">
                    <div class="text-left">{{ other.name }}</div>
                  </th>
                  <td class="develop-link-url-cell">
                    <a
                      v-if="isUrl(other.url)"
                      :href="other.url"
                      class="develop-link-url"
                      target="_blank"
                      rel="noopener noreferrer"
                      >{{ other.url }}</a
                    >
                    <span v-else class="develop-link-url develop-link-url-text">
                      {{ other.url }}
                    </span>
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
              envName: "dev環境",
              url:
                "TODO 実装したら記載",
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
              envName: "dev環境",
              url: "http://healthinfo-app-root-front-dev.s3-website-ap-northeast-1.amazonaws.com/login",
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
  methods: {
    isUrl: function (value) {
      return /^https?:\/\//.test(value);
    },
  },
};
</script>

<style scoped>
.develop-link-row {
  margin-bottom: 12px;
}

.develop-link-card-body {
  padding: 14px 16px 16px;
}

.develop-link-table :deep(table) {
  border-collapse: separate;
  border-spacing: 0 8px;
  table-layout: fixed;
  width: 100%;
}

.develop-link-table :deep(th),
.develop-link-table :deep(td) {
  border-bottom: 0;
  height: auto;
  padding: 0;
  vertical-align: top;
}

.develop-link-label-cell {
  width: 180px;
  padding-top: 3px !important;
}

.develop-link-url-cell {
  line-height: 1.55;
}

.develop-link-url {
  color: #006d77;
  display: inline;
  font-family: monospace;
  overflow-wrap: anywhere;
  text-decoration: none;
}

.develop-link-url-text {
  color: inherit;
}

.develop-link-url:hover {
  text-decoration: underline;
}
</style>
