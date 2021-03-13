<template>
  <div>
    <v-row justify="center">
      <v-col class="text-center" cols="12" sm="8" md="10">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col class="text-left" cols="12" sm="8" md="10">
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
  layout: "healthinfoappLayout",
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
          href: "/helthinfoapp/api",
        },
      ]
    };
  },
};
</script>

<style scoped>
</style>