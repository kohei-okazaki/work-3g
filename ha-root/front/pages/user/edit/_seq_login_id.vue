<template>
  <div>
    <AppTitle icon="mdi-account-edit" title="ユーザ編集" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <AppMessageSuccess v-if="apiResult.isSuccess" :data="apiResult" />
    <v-row>
      <v-col class="text-center">
        <v-card>
          <v-card-text>
            <v-form ref="editForm">
              <v-text-field
                v-model="editUserForm.seqLoginId"
                label="更新対象の管理者ユーザのログインID"
                disabled
                >{{ editUserForm.seqLoginId }}</v-text-field
              >
              <v-checkbox
                v-model="editUserForm.roles"
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
        <UserRetrieve ref="userRetrieve" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppConfirm from "~/components/modal/ConfirmModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppMessageSuccess from "~/components/AppMessageSuccess.vue";
import UserRetrieve from "~/components/user/UserRetrieve.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user/";
let rolesUrl = process.env.api_base_url + "roles";

export default {
  components: {
    AppConfirm,
    AppTitle,
    AppMessageError,
    AppMessageSuccess,
    UserRetrieve,
  },
  data: function () {
    return {
      loading: false,
      error: {
        hasError: false,
        message: null,
      },
      apiResult: {
        isSuccess: false,
        message: "",
      },
      required: (value) => !!value || "必ず入力してください",
      editUserForm: {
        seqLoginId: null,
        roles: [],
      },
      roles: [],
    };
  },
  methods: {
    async openUserEditModal() {
      let validateResult = this.validate();
      if (validateResult != null) {
        // 妥当性チェックエラーの場合
        this.error.hasError = true;
        this.error.message = validateResult.message;
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
        this.editUserForm.roles == null ||
        this.editUserForm.roles.length == 0
      ) {
        // 権限配列がnullまたは未指定の場合、エラー
        return {
          message: "権限が未指定です",
        };
      }
      return null;
    },
    sendUserEdit: function () {
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + this.editUserForm.seqLoginId;
      let reqBody = {
        roles: this.editUserForm.roles,
      };
      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result === "0") {
            this.apiResult.isSuccess = true;
            this.apiResult.message = "更新完了";

            // ユーザ情報照会APIを実行
            let retrieveResult = this.$refs.userRetrieve.retrieve(
              this.editUserForm.seqLoginId
            );
            if (retrieveResult.hasError) {
              this.error.hasError = true;
              this.error.message = retrieveResult.message;
            }
          } else {
            this.error.hasError = true;
            this.error.message = result.data.error.message;
          }
          this.loading = false;
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
    getRoles: function () {
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(rolesUrl, { headers }).then(
        (result) => {
          if (result.data.result === "0") {
            this.roles = result.data.roles;
          } else {
            this.error.hasError = true;
            this.error.message = result.data.error.message;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("roles [error]=" + error);
          return error;
        }
      );
    },
  },
  mounted: function () {
    // 権限リスト
    this.getRoles();
    // ログインID
    this.editUserForm.seqLoginId = this.$store.state.auth.seq_login_id;
    // ユーザ権限
    this.editUserForm.roles = this.$store.state.auth.roles.map(
      (item) => item.value
    );
  },
};
</script>

<style scoped>
</style>