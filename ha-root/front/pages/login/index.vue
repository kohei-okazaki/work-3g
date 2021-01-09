<template>
  <v-row justify="center" align-content="center">
    <v-col class="text-center">
      <br /><br />
      <v-card>
        <v-card-title>{{ title }}</v-card-title>
        <v-card-text>
          <v-form ref="login_form">
            <v-text-field v-model="seq_login_id" label="ログインID" :rules="[required]"></v-text-field>
            <v-text-field v-model="password" label="パスワード" :type="'password'" :rules="[required]"></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-text v-if="api_data.api_result == '0'">
          <p>API送信成功</p>
          <p>{{ api_data.token }}</p>
          <p>{{ store_data }}</p>
        </v-card-text>
        <v-card-actions>
          <v-btn text @click="submit">ログイン</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
const axios = require("axios");
let url = process.env.api_base_url + "login";

export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  data: function () {
    return {
      title: 'Root App Login',
      seq_login_id: "",
      password: "",
      required: (value) => !!value || "必ず入力してください",
      api_data: {
        api_result: "",
        token: "",
      },
      store_data: [],
    };
  },
  methods: {
    submit: function () {
      if (this.$refs.login_form.validate()) {
        console.log("入力値は正しいです");

        // リクエスト情報作成
        let request = new URLSearchParams();
        request.append("seq_login_id", this.seq_login_id);
        request.append("password", this.password);

        // ログインAPI呼び出し
        axios
          .post(url, request)
          .then((result) => {
            if (result.data.result === "0") {
              this.api_data.api_result = result.data.result;
              this.api_data.token = result.data.token;

              // storeにログイン情報を保存
              this.$store.commit("auth/setToken", {
                seq_login_id: this.seq_login_id,
                token: this.api_data.token,
              });

              // storeのトークン情報を取得
              this.store_data = this.$store.state.auth.auth_data_list;
            }
          })
          .catch((error) => {
            alert("API通信に失敗しました. URL=" + url + "\r\n,error=" + error);
            console.log("API通信に失敗しました. URL=" + url + "\r\n,error=" + error);
          });
      }
    },
  },
};
</script>

<style>

</style>