<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="6">
        <br />
        <br />
        <v-card>
          <v-card-title>{{ title }}</v-card-title>
          <v-card-text>
            <v-form ref="entryForm">
              <v-text-field
                v-model="password"
                label="パスワード"
                prepend-icon="mdi-lock"
                :append-icon="passwordToggle.passwordIcon"
                :type="passwordToggle.passwordType"
                @click:append="passwordShow = !passwordShow"
                :rules="[required]"
              ></v-text-field>
              <v-text-field
                v-model="confPassword"
                label="確認用パスワード"
                prepend-icon="mdi-lock"
                :append-icon="confPasswordToggle.confPasswordIcon"
                :type="confPasswordToggle.confPasswordType"
                @click:append="confPasswordShow = !confPasswordShow"
                :rules="[required]"
              ></v-text-field>
            </v-form>
            <v-card-text v-if="api_data.api_result === '0'">
              以下の情報をログイン画面より入力し、ログインしてください
              <ul>
                <li>ログインID = {{ api_data.seq_login_id }}</li>
                <li>パスワード = {{ this.password }}</li>
              </ul>
            </v-card-text>
          </v-card-text>
          <v-card-actions>
            <template v-if="api_data.api_result != '0'">
              <!-- APIが正常終了していない場合 -->
              <v-btn color="primary" @click="openUserEntryModal">
                <v-icon>mdi-account-multiple-plus</v-icon>&ensp;作成
              </v-btn>
              <v-btn color="accent" @click="reset">
                <v-icon>mdi-alert</v-icon>&ensp;リセット
              </v-btn>
            </template>
            <template v-else>
              <!-- APIが正常終了している場合 -->
              <v-btn color="primary" to="/login">
                <v-icon>mdi-account-arrow-right</v-icon>&ensp;ログイン
              </v-btn>
            </template>
          </v-card-actions>
        </v-card>
        <AppConfirm ref="confirm" />
        <AppLoading :loading="loading" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppConfirm from "~/components/modal/ConfirmModal.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user";

export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  // ログイン認証情報は不要
  auth: false,
  components: {
    AppConfirm,
    AppMessageError,
    AppLoading,
  },
  data: function () {
    return {
      title: "管理ユーザ作成",
      error: {
        hasError: false,
        message: null,
      },
      password: "",
      confPassword: "",
      passwordShow: false,
      confPasswordShow: false,
      required: (value) => !!value || "必ず入力してください",
      loading: false,
      api_data: {
        api_result: "",
        seq_login_id: "",
      },
    };
  },
  computed: {
    passwordToggle: function () {
      const passwordIcon = this.passwordShow ? "mdi-eye" : "mdi-eye-off";
      const passwordType = this.passwordShow ? "text" : "password";
      return {
        passwordIcon,
        passwordType,
      };
    },
    confPasswordToggle: function () {
      const confPasswordIcon = this.confPasswordShow
        ? "mdi-eye"
        : "mdi-eye-off";
      const confPasswordType = this.confPasswordShow ? "text" : "password";
      return {
        confPasswordIcon,
        confPasswordType,
      };
    },
  },
  methods: {
    reset: function () {
      this.$refs.entryForm.reset();
    },
    async openUserEntryModal() {
      if (!this.$refs.entryForm.validate()) {
        // 入力値エラーの場合
        return;
      }

      if (
        await this.$refs.confirm.open("ユーザ作成", "", {
          color: "blue",
          width: 400,
        })
      ) {
        if (!this.$refs.entryForm.validate()) {
          // 入力値エラーの場合
          return;
        }
        this.entryUser();
      }
    },
    entryUser: function () {
      this.loading = true;
      let reqBody = {
        password: this.password,
        conf_password: this.confPassword,
      };
      axios.post(url, reqBody).then(
        (result) => {
          if (result.data.result === "0") {
            // ユーザ作成APIが正常終了した場合
            this.api_data.api_result = result.data.result;
            this.api_data.seq_login_id = result.data.seq_login_id;
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
          console.log("userentry [error]=" + error);
          return error;
        }
      );
    },
  },
};
</script>

<style scoped>
</style>