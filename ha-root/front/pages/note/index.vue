<template>
  <div>
    <AppTitle icon="mdi-notebook" title="メモ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />

    <template v-if="isEntryShow">
      <template v-if="entryMode">
        <NoteEntry @get-notes="getNotes" />
      </template>
    </template>

    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table :headers="headers" :items="note_list"> </v-data-table>
        <ConfirmModal ref="confirm" />
        <ProcessFinishModal ref="finish" />
        <AppLoading :loading="loading" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NoteEntry from "~/components/note/NoteEntry.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "note";

export default {
  components: {
    NoteEntry,
    ConfirmModal,
    ProcessFinishModal,
    AppTitle,
    AppMessageError,
    AppLoading,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      isRefShow: false,
      isEntryShow: false,
      entryMode: true,
      loading: false,
      headers: [
        {
          text: "",
          value: "edit_action",
          sortable: false,
        },
        {
          text: "",
          value: "delete_action",
          sortable: false,
        },
        {
          text: "タイトル",
          value: "title",
          sortable: false,
        },
        {
          text: "詳細",
          value: "detail",
          sortable: false,
        },
        {
          text: "登録日時",
          value: "reg_date",
          sortable: false,
        },
        {
          text: "更新日時",
          value: "update_date",
          sortable: false,
        },
      ],
      note_list: [],
    };
  },
  methods: {
    isRefView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "01") {
          this.isRefShow = true;
          return;
        }
      }
      this.isRefShow = false;
    },
    isEntryView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "02") {
          this.isEntryShow = true;
          return;
        }
      }
      this.isEntryShow = false;
    },
    getNotes: function () {
      this.loading = true;
      let reqUrl =
        url + "?" + "seq_login_id=" + this.$store.state.auth.seq_login_id;
      axios
        .get(reqUrl, {
          headers: { Authorization: this.$store.state.auth.token },
        })
        .then(
          (response) => {
            if (response.data.result === "0") {
              this.note_list = response.data.note_list;
            } else {
              this.error.hasError = true;
              this.error.message = response.data.error.message;
            }
            this.loading = false;
          },
          (error) => {
            this.error.hasError = true;
            this.error.message = error;
            this.loading = false;
            console.log("[error]=" + error);
            return error;
          }
        );
    },
  },
  created: function () {
    this.isEntryView();
    this.isRefView();
    if (!this.isRefShow) {
      return;
    }
    this.getNotes();
  },
};
</script>
<style scoped>
</style>