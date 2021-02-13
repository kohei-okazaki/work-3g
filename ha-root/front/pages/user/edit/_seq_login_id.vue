<template>
  <div>
    <AppTitle icon="mdi-account-edit" title="ユーザ編集" />
    <v-row v-if="error.hasError">
      <v-col class="text-left">
          <v-alert border="left" color="red" type="error">{{
            error.message
          }}</v-alert>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="text-center">
        <v-card>
          <v-card-text>
            <v-form ref="edit_form">
              <v-text-field
                v-model="edit_user_form.seq_login_id"
                label="更新対象の管理者ユーザのログインID"
                disabled
                >{{ edit_user_form.seq_login_id }}</v-text-field
              >
              <v-checkbox
                v-model="edit_user_form.roles"
                v-for="(role, i) in roles"
                :key="i"
                :label="role.label"
                :value="role.value"
                :rules="[required]"
                hide-details
              ></v-checkbox>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              @click="submit"
              :loading="loading"
              :disabled="loading"
            >
              <v-icon>mdi-comment-edit</v-icon>&ensp;更新
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user/";

export default {
  components: {
    AppTitle,
  },
  data: function () {
    return {
      loading: false,
      error: {
        hasError: false,
        message: null,
      },
      required: (value) => !!value || "必ず入力してください",
      edit_user_form: {
        seq_login_id: null,
        roles: [],
      },
      roles: [
        {
          label: "管理者権限",
          value: "00",
        },
        {
          label: "照会権限",
          value: "01",
        },
        {
          label: "作成権限",
          value: "02",
        },
      ],
    };
  },
  methods: {
    submit: function () {
      if (!this.validate()) {
        // 妥当性チェックエラーの場合
        this.error.hasError = true;
        this.error.message = "必須項目が未指定です";
        return;
      }

      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + this.edit_user_form.seq_login_id;
    },
    validate: function () {
      if (
        this.edit_user_form.roles == null ||
        this.edit_user_form.roles.length == 0
      ) {
        // 権限配列がnullまたは未指定の場合、エラー
        return false;
      }
      return true;
    },
  },
  mounted: function () {
    this.edit_user_form.seq_login_id = this.$store.state.auth.seq_login_id;
    this.edit_user_form.roles = this.$store.state.auth.roles.map(
      (item) => item.value
    );
  },
};
</script>

<style scoped>
</style>