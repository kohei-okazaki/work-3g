<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
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
      breadcrumbs:[
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "健康管理ダッシュボード",
          disabled: true,
          href: "/helthinfoapp/dashboard",
        },
      ]
    };
  },
};
</script>

<style scoped>
</style>