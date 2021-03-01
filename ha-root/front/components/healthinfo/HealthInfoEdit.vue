<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="6">
        <v-card>
          <v-card-text>
            <v-form ref="editForm">
              <v-text-field
                v-model="healthInfoEditForm.seqHealthInfoId"
                label="更新対象の健康情報ID"
                disabled
                >{{ healthInfoEditForm.seqHealthInfoId }}</v-text-field
              >
              <v-text-field v-model="healthInfoEditForm.height" label="身長">{{
                healthInfoEditForm.height
              }}</v-text-field>
              <v-text-field v-model="healthInfoEditForm.weight" label="体重">{{
                healthInfoEditForm.weight
              }}</v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="submit">
              <v-icon>mdi-comment-edit</v-icon>&ensp;更新
            </v-btn>
            <v-btn color="accent" @click="reset">
              <v-icon>mdi-alert</v-icon>&ensp;リセット
            </v-btn>
          </v-card-actions>
        </v-card>
        <ProcessFinishModal ref="finish" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppMessageError from "~/components/AppMessageError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "healthinfo/";

export default {
  components: {
    ProcessFinishModal,
    AppMessageError,
  },
  props: {
    healthInfoEditForm: Object,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      loading: false,
      required: (value) => !!value || "必ず入力してください",
    };
  },
  methods: {
    submit: function () {
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + this.healthInfoEditForm.seqHealthInfoId;
      let reqBody = {
        seq_user_id: this.$store.state.auth.seq_login_id,
        height: this.healthInfoEditForm.height,
        weight: this.healthInfoEditForm.weight,
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            this.$refs.finish.open("健康情報更新処理", "更新完了しました", {
              color: "blue",
              width: 400,
            });
          } else {
            this.error.hasError = true;
            this.error.message = result.data.error.message;
          }
          this.loading = false;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
    reset: function () {
      this.$refs.editForm.reset();
    },
  },
};
</script>

<style scoped>
</style>