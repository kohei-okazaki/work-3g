<template>
  <v-row justify="center" align-content="center">
    <v-col class="text-center">
      <br /><br />
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
          <v-btn color="primary" @click="submit">
            <v-icon>mdi-account-arrow-right</v-icon>ログイン
          </v-btn>
          <v-spacer />
          <v-btn color="primary" :to="`user/entry`">
            <v-icon>mdi-account-multiple-plus</v-icon>管理ユーザ作成
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  data: function () {
    return {
      title: "ログイン",
      seq_login_id: "",
      password: "",
      show: false,
      required: (value) => !!value || "必ず入力してください",
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
      if (!this.$refs.login_form.validate()) {
        // 入力値エラーの場合
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

            // let list = this.$store.state.auth.auth_data_list;
            // let value = null;
            // for (var i = 0; i < list.length; i++) {
            //   let auth_data = list[i];
            //   if (auth_data.seq_login_id == this.seq_login_id) {
            //     // 同一ログインIDの場合、トークン取得
            //     value = auth_data.token;
            //     break;
            //   }
            // }
            // console.log("[saved token]=" + value);

            return response;
          },
          (error) => {
            console.log("[error]=" + error);
            return error;
          }
        );
    },
  },
};
</script>

<style>
</style>