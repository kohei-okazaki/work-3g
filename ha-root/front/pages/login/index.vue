<template>
  <div>
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="6">
        <br />
        <v-card>
          <v-card-title>{{ title }}</v-card-title>
          <v-card-text>
            <v-form ref="loginForm">
              <v-text-field
                v-model="seqLoginId"
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
            <v-btn color="primary" @click="submit">
              <v-icon>mdi-account-arrow-right</v-icon>&ensp;ログイン
            </v-btn>
            <v-spacer />
            <v-btn color="primary" :to="`user/entry`">
              <v-icon>mdi-account-multiple-plus</v-icon>&ensp;管理ユーザ作成
            </v-btn>
          </v-card-actions>
        </v-card>
        <UserRetrieve ref="userRetrieve" />
        <AppLoading :loading="loading" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";
import UserRetrieve from "~/components/user/UserRetrieve.vue";

export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  components: {
    AppMessageError,
    AppLoading,
    UserRetrieve,
  },
  data: function () {
    return {
      title: "ログイン",
      seqLoginId: "",
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
      if (!this.$refs.loginForm.validate()) {
        // 入力値エラーの場合
        return;
      }
      this.loading = true;
      this.$auth
        .loginWith("local", {
          data: {
            seq_login_id: this.seqLoginId,
            password: this.password,
          },
        })
        .then(
          (response) => {
            // JWTをレスポンスヘッダから取得
            let authorization = response.headers["authorization"];

            // storeにログイン情報を保存
            this.$store.commit("auth/setToken", {
              seq_login_id: this.seqLoginId,
              token: authorization,
            });

            // ログイン成功時、ユーザ情報照会APIを実行
            let retrieveResult = this.$refs.userRetrieve.retrieve(this.seqLoginId);
            if (retrieveResult.hasError) {
              this.error.hasError = true;
              this.error.message = retrieveResult.message;
            }
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
  },
};
</script>

<style scoped>
</style>