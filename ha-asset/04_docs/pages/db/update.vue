<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle title="更新" />
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // DBのレイアウトを適用
  layout: "dbLayout",
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
          text: "DB",
          disabled: false,
          href: "/db",
        },
        {
          text: "更新",
          disabled: true,
          href: "/db/update",
        },
      ],
    }
  }
};
</script>

<style scoped>

</style>