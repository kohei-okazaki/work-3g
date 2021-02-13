<template>
  <div>
    <AppError v-if="error.hasError" :data="error" />
    <v-row justify="center" align-content="center">
      <v-col class="text-center">
        <br />
        <v-card>
          <v-card-title>{{ title }}</v-card-title>
          <v-card-text>
            <v-form ref="login_form">
              <v-text-field
                v-model="seq_login_id"
                label="ログインID"
                prepend-icon="mdi-account-circle"
                :rules="[required]"
              ></v-text-field>
              <v-text-field
                v-model="password"
                label="パスワード"
                prepend-icon="mdi-lock"
                :append-icon="toggle.icon"
                :type="toggle.type"
                @click:append="show = !show"
                :rules="[required]"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              @click="submit"
              :loading="loading"
              :disabled="loading"
            >
              <v-icon>mdi-account-arrow-right</v-icon>&ensp;ログイン
            </v-btn>
            <v-spacer />
            <v-btn color="primary" :to="`user/entry`">
              <v-icon>mdi-account-multiple-plus</v-icon>&ensp;管理ユーザ作成
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppError from "~/components/AppError.vue";

const axios = require("axios");
let retriveUrl = process.env.api_base_url + "user/";

export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  components: {
    AppError,
  },
  data: function () {
    return {
      title: "ログイン",
      seq_login_id: "",
      password: "",
      show: false,
      loading: false,
      required: (value) => !!value || "必ず入力してください",
      error: {
        hasError: false,
        message: null,
      },
    };
  },
  computed: {
    toggle: function () {
      const icon = this.show ? "mdi-eye" : "mdi-eye-off";
      const type = this.show ? "text" : "password";
      return {
        icon,
        type,
      };
    },
  },
  created: function () {
    // vuex情報を削除
    this.$store.commit("auth/clearToken");
  },
  methods: {
    submit: function () {
      this.loading = true;
      if (!this.$refs.login_form.validate()) {
        // 入力値エラーの場合
        this.loading = false;
        return;
      }
      this.$auth
        .loginWith("local", {
          data: {
            seq_login_id: this.seq_login_id,
            password: this.password,
          },
        })
        .then(
          (response) => {
            // JWTをレスポンスヘッダから取得
            let authorization = response.headers["authorization"];
            console.log("[authorization]=" + authorization);

            // storeにログイン情報を保存
            this.$store.commit("auth/setToken", {
              seq_login_id: this.seq_login_id,
              token: authorization,
            });

            // ログイン成功時、ユーザ情報照会APIを実行
            this.retrieve(this.seq_login_id);

            this.loading = false;

            return response;
          },
          (error) => {
            this.loading = false;
            this.error.hasError = true;
            this.error.message = error;
            console.log("login [error]=" + error);
            return error;
          }
        );
    },
    retrieve: function (seq_login_id) {
      let headers = { Authorization: this.$store.state.auth.token };
      axios.get(retriveUrl + seq_login_id, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            // 正常終了した場合
            // storeにユーザ情報を保存
            let userData = {
              roles: response.data.roles,
            };
            this.$store.commit("auth/setUserData", userData);
          } else {
            this.error.hasError = true;
            this.error.message = response.data.error.message;
            this.$auth.logout();
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = response.data.error.message;
          console.log("retrieve [error]=" + error);
          return error;
        }
      );
    },
  },
};
</script>

<style scoped>
</style>