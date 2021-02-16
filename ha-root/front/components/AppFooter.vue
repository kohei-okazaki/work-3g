<template>
  <v-footer :absolute="fixed" app>
    <v-row justify="left" align-content="center" no-gutters>
      <v-col cols="1" md="1" class="copywriter">
        <span>&copy; {{ new Date().getFullYear() }}</span>
      </v-col>
      <v-col cols="11" md="11">
        <v-btn color="info" text @click="openContentAlertModal"
          >管理者サイトについて</v-btn
        >
        <ContentAlertModal ref="contentAlertModal" />
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