<template>
  <div>
    <AppTitle icon="mdi-account-edit" title="ユーザ編集" />
    <AppError v-if="error.hasError" :message="error.message" />
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
              @click="openUserEditModal"
              :loading="loading"
              :disabled="loading"
            >
              <v-icon>mdi-comment-edit</v-icon>&ensp;更新
            </v-btn>
          </v-card-actions>
        </v-card>
        <AppConfirm ref="confirm"></AppConfirm>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppConfirm from "~/components/modal/ConfirmModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppError from "~/components/AppError.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user/";

export default {
  components: {
    AppConfirm,
    AppTitle,
    AppError,
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
    async openUserEditModal() {
      if (!this.validate()) {
        // 妥当性チェックエラーの場合
        this.error.hasError = true;
        this.error.message = "必須項目が未指定です";
        return;
      }
      if (
        await this.$refs.confirm.open("ユーザ更新", "", {
          color: "blue",
          width: 400,
        })
      ) {
        this.sendUserEdit();
      }
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
    sendUserEdit: function () {
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + this.edit_user_form.seq_login_id;
      let reqBody = {
        roles: this.edit_user_form.roles,
      };
      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result === "0") {
            this.loading = false;
          } else {
            this.loading = false;
            this.error.hasError = true;
            this.error.message = result.data.error.message;
          }
        },
        (error) => {
          this.loading = false;
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
    },
  },
  mounted: function () {
    // ログインID
    this.edit_user_form.seq_login_id = this.$store.state.auth.seq_login_id;
    // ユーザ権限
    this.edit_user_form.roles = this.$store.state.auth.roles.map(
      (item) => item.value
    );
  },
};
</script>

<style scoped>
</style>