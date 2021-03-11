<template>
  <v-footer :absolute="fixed" app>
    <v-row justify="left" align-content="center" no-gutters>
      <v-col xs="12" class="copywriter" md="1" lg="1">
        <span>&copy; {{ new Date().getFullYear() }}</span>
      </v-col>
      <v-col xs="12" md="3" lg="2">
        <v-btn color="info" text @click="openContentAlertModal"
          >管理者サイトについて</v-btn
        >
        <ContentAlertModal ref="contentAlertModal" />
      </v-col>
      <v-col xs="12" md="3" lg="2">
        <v-btn
          color="info"
          text
          href="http://healthinfo-app-docs.s3-website-ap-northeast-1.amazonaws.com/"
          target="_blank"
          rel="noopener noreferrer"
          >健康管理アプリについて</v-btn
        >
      </v-col>
    </v-row>
  </v-footer>
</template>

<script>
import ContentAlertModal from "~/components/modal/ContentAlertModal.vue";

export default {
  components: {
    ContentAlertModal,
  },
  data: function () {
    return {
      fixed: true,
    };
  },
  methods: {
    openContentAlertModal: function () {
      this.$refs.contentAlertModal.open();
    },
  },
};
</script>

<style scoped>
.copywriter {
  padding-top: 7px;
}
</style>