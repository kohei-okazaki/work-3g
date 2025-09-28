<template>
  <div>
    <AppTitle icon="mdi-account-edit" title="ユーザ編集" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <AppMessageSuccess v-if="apiResult.isSuccess" :data="apiResult" />
    <v-row justify="center" align="center">
      <v-col class="text-center" cols="12" sm="8" md="8">
        <v-card>
          <v-form ref="editForm">
            <v-card-text>
              <v-text-field
                v-model="editUserForm.seqLoginId"
                label="更新対象の管理者ユーザのログインID"
                disabled
                >{{ editUserForm.seqLoginId }}</v-text-field
              >
            </v-card-text>
            <v-card-title>削除フラグ</v-card-title>
            <v-card-text>
              <v-switch
                :label="!editUserForm.deleteFlag ? '削除しない' : '削除する'"
                v-model="editUserForm.deleteFlag"
              ></v-switch>
            </v-card-text>

            <v-card-title>パスワード</v-card-title>
            <v-card-text>
              <v-text-field
                v-model="editUserForm.password"
                label="更新後パスワード"
                clearable
                >{{ editUserForm.password }}</v-text-field
              >
            </v-card-text>

            <v-card-title>パスワード有効期限</v-card-title>
            <v-card-text>
              <v-text-field
                v-model="editUserForm.passwordExpire"
                label="日付"
                hint="年/月/日"
                persistent-hint
                @click="isDispCalendar = !isDispCalendar"
                clearable
                :rules="[required]"
              ></v-text-field>
              <template v-if="isDispCalendar">
                <v-date-picker
                  v-model="editUserForm.passwordExpire"
                  no-title
                  @input="isDispCalendar = !isDispCalendar"
                ></v-date-picker>
              </template>
            </v-card-text>

            <v-card-title>備考</v-card-title>
            <v-card-text>
              <v-textarea
                v-model="editUserForm.remarks"
                label="備考"
                clearable
                >{{ editUserForm.remarks }}</v-textarea
              >
            </v-card-text>

            <v-card-title>権限</v-card-title>
            <v-card-text>
              <v-checkbox
                v-model="userRoles"
                v-for="(role, i) in roles"
                :key="i"
                :label="role.label"
                :value="role.value"
                :rules="[required]"
                hide-details
              ></v-checkbox>
              <br />
              <v-divider />
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" @click="openUserEditModal">
                <v-icon>mdi-comment-edit</v-icon>&ensp;更新
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
        <AppConfirm ref="confirm" />
        <AppLoading :loading="loading" />
        <UserRetrieve
          :seqLoginId="editUserForm.seqLoginId"
          @setRetrieveApiResult="setRetrieveApiResult"
        />
        <UserDataSave ref="userDataSave" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppConfirm from "~/components/modal/ConfirmModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppMessageSuccess from "~/components/AppMessageSuccess.vue";
import AppLoading from "~/components/AppLoading.vue";
import UserRetrieve from "~/components/user/UserRetrieve.vue";
import UserDataSave from "~/components/user/UserDataSave.vue";

const axios = require("axios");
let url = process.env.api_base_url + "user/";
let rolesUrl = process.env.api_base_url + "roles";

export default {
  components: {
    AppConfirm,
    AppTitle,
    AppMessageError,
    AppMessageSuccess,
    AppLoading,
    UserRetrieve,
    UserDataSave,
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
      isDispCalendar: false,
      editUserForm: {
        seqLoginId: null,
        deleteFlag: false,
        password: null,
        passwordExpire: null,
        remarks: null,
      },
      // editUserFormの中で定義すると#setRetrieveApiResultの影響のようで権限を一気に2つ以上消せない状態になる
      userRoles: [],
      roles: [],
    };
  },
  methods: {
    /**
     * ユーザ更新確認モーダル表示
     */
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
        // ユーザ情報更新API実行
        this.updateUser();
      }
    },
    /**
     * 妥当性チェック
     */
    validate: function () {
      if (this.userRoles == null || this.userRoles.length == 0) {
        // 権限配列がnullまたは未指定の場合、エラー
        return {
          message: "権限が未指定です",
        };
      }
      return null;
    },
    /**
     * ユーザ更新処理
     */
    updateUser: function () {
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // ユーザ情報編集API
      let reqUrl = url + this.editUserForm.seqLoginId;
      let reqBody = {
        roles: this.userRoles,
        delete_flag: this.editUserForm.deleteFlag,
        remarks: this.editUserForm.remarks,
        password: this.editUserForm.password,
        password_expire: this.editUserForm.passwordExpire.replaceAll("-", "/"),
      };
      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == "0") {
            this.apiResult.isSuccess = true;
            this.apiResult.message = "更新完了";
            this.saveUpdateData();
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
          console.log("user update [error]=" + error);
          return error;
        }
      );
    },
    /**
     * 権限リスト取得
     */
    getRoles: function () {
      this.loading = true;
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
          this.loading = false;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          this.loading = false;
          console.log("roles [error]=" + error);
          return error;
        }
      );
    },
    /**
     * ユーザ更新後、storeのユーザ情報を更新
     */
    saveUpdateData: function () {
      this.$refs.userDataSave.save(this.editUserForm.seqLoginId);
    },
    /**
     * ユーザ情報取得コンポーネントからのイベント
     * @param retrieveApiResult 
     */
    setRetrieveApiResult: function (retrieveApiResult) {
      this.editUserForm = retrieveApiResult;
    },
  },
  created: function () {
    // ログインID
    this.editUserForm.seqLoginId = this.$store.state.auth.seq_login_id;
  },
  mounted: function () {
    // 権限リスト
    this.getRoles();
    // ユーザ権限
    this.userRoles = this.$store.state.auth.roles.map((item) => item.value);
  },
};
</script>

<style scoped>
</style>