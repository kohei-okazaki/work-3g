<template>
  <v-row justify="center" align-content="center">
    <v-col class="text-center">
      <br /><br />
      <v-card>
        <v-card-title>{{ title }}</v-card-title>
        <v-card-text>
          <v-form ref="entry_form">
            <v-text-field
              v-model="password"
              label="パスワード"
              prepend-icon="mdi-lock"
              :append-icon="password_toggle.password_icon"
              :type="password_toggle.password_type"
              @click:append="password_show = !password_show"
              :rules="[required]"
            ></v-text-field>
            <v-text-field
              v-model="conf_password"
              label="確認用パスワード"
              prepend-icon="mdi-lock"
              :append-icon="conf_password_toggle.conf_password_icon"
              :type="conf_password_toggle.conf_password_type"
              @click:append="conf_password_show = !conf_password_show"
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
            <v-btn color="primary" @click="submit" v-on="on">
              <v-icon>mdi-account-multiple-plus</v-icon>&ensp;作成
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
      <AppConfirm ref="confirm"></AppConfirm>
    </v-col>
  </v-row>
</template>

<script>
import AppConfirm from "~/components/modal/ConfirmModal.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user/entry";

export default {
  // ログイン前のレイアウトを適用
  layout: "nonAuthLayout",
  // ログイン認証情報は不要
  auth: false,
  components: {
    AppConfirm,
  },
  data: function () {
    return {
      title: "管理ユーザ作成",
      password: "",
      conf_password: "",
      password_show: false,
      conf_password_show: false,
      api_data: {
        api_result: "",
        seq_login_id: "",
      },
      modal: {
        title: 'ユーザ作成',
        contents: '',
      }
    };
  },
  computed: {
    password_toggle: function () {
      const password_icon = this.password_show ? "mdi-eye" : "mdi-eye-off";
      const password_type = this.password_show ? "text" : "password";
      return {
        password_icon,
        password_type,
      };
    },
    conf_password_toggle: function () {
      const conf_password_icon = this.conf_password_show
        ? "mdi-eye"
        : "mdi-eye-off";
      const conf_password_type = this.conf_password_show ? "text" : "password";
      return {
        conf_password_icon,
        conf_password_type,
      };
    },
  },
  methods: {
    async submit() {
      if (
        await this.$refs.confirm.open(this.modal.title, this.modal.contents, {
          color: 'blue',
          width: 400,
        })
      ) {
        this.send_user_entry();
      }
    },
    send_user_entry: function () {
      let params = new URLSearchParams();
      params.append("password", this.password);
      params.append("conf_password", this.conf_password);

      axios.post(url, params).then((result) => {
        if (result.data.result === "0") {
          // ユーザ作成APIが正常終了した場合
          this.api_data.api_result = result.data.result;
          this.api_data.seq_login_id = result.data.seq_login_id;
        }
      });
    },
  },
};
</script>

<style scope>

</style>