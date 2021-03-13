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
  // NodeAPIのレイアウトを適用
  layout: "nodeApiLayout",
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
          text: "Node API",
          disabled: true,
          href: "/node/api",
        },
      ]
    };
  },
};
</script>

<style scoped>
</style>