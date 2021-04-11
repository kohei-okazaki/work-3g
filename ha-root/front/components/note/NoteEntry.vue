<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="6">
        <v-card>
          <v-card-text>
            <v-form ref="entryForm">
              <v-text-field
                v-model="entryInfo.title"
                label="タイトル"
                clearable
                :rules="[required]"
              ></v-text-field>
              <v-textarea
                v-model="entryInfo.detail"
                label="詳細(htmlタグの入力も可能です)"
                clearable
                :rules="[required]"
              ></v-textarea>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="submit">
              <v-icon>mdi-newspaper-plus</v-icon>&ensp;登録
            </v-btn>
            <v-btn color="accent" @click="reset">
              <v-icon>mdi-alert</v-icon>&ensp;リセット
            </v-btn>
          </v-card-actions>
          <ProcessFinishModal ref="finish" />
          <AppLoading :loading="loading" />
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>
      
<script>
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "note";

export default {
  components: {
    ProcessFinishModal,
    AppMessageError,
    AppLoading,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      loading: false,
      entryInfo: {
        title: null,
        detail: null,
      },
      required: (value) => !!value || "必ず入力してください",
    };
  },
  methods: {
    reset() {
      this.$refs.entryForm.reset();
    },
    submit: function () {
      this.loading = true;
      if (!this.$refs.entryForm.validate()) {
        // 入力値エラーの場合
        this.loading = false;
        return;
      }

      let reqBody = {
        seq_login_id: this.$store.state.auth.seq_login_id,
        title: this.entryInfo.title,
        detail: this.entryInfo.detail,
      };
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      axios.post(url, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // 処理完了モーダルを表示
            let json = JSON.stringify(this.entryInfo, null, "\t");
            this.$refs.finish.open("メモ登録処理", json, {
              color: "blue",
              width: 400,
            });

            // メモ情報登録後、最新のメモ情報を取得する
            this.$emit("get-notes");

            this.loading = false;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          this.loading = false;
          return error;
        }
      );
    },
  },
};
</script>
      
<style scoped>
</style>