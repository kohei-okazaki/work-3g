<template>
  <div>
    <v-row justify="center">
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // 健康管理APIのレイアウトを適用
  layout: "healthinfoappApiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
  },
  data: function () {
    return {
      breadcrumbs:[
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "健康管理API",
          disabled: true,
          href: "/healthinfoapp/api",
        },
      ]
    };
  },
};
</script>

<style scoped>
</style>