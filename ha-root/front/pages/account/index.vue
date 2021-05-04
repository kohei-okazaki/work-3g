<template>
  <div>
    <AppTitle icon="mdi-account" title="アカウント情報一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row>
      <v-col>
        <v-dialog v-model="accountEditModal.dialog" max-width="600px">
          <!-- ダイアログ本体 -->
          <v-card>
            <v-card-title>アカウント情報更新</v-card-title>
            <v-divider></v-divider>
            <br />
            <v-card-text>
              <v-row>
                <v-col class="text-center" cols="12" sm="12">
                  <v-form ref="editAccountForm">
                    <v-text-field
                      v-model="accountEditModal.mailAddress"
                      label="メールアドレス"
                      clearable
                      :rules="[required]"
                    ></v-text-field>
                    <v-text-field
                      v-model="accountEditModal.passwordExpire"
                      label="パスワード有効期限"
                      hint="年/月/日"
                      persistent-hint
                      @click="
                        accountEditModal.isDispCalendar = !accountEditModal.isDispCalendar
                      "
                      clearable
                      :rules="[required]"
                    ></v-text-field>
                    <template v-if="accountEditModal.isDispCalendar">
                      <v-date-picker
                        v-model="accountEditModal.passwordExpire"
                        no-title
                        @input="
                          accountEditModal.isDispCalendar = !accountEditModal.isDispCalendar
                        "
                      >
                      </v-date-picker>
                    </template>
                  </v-form>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions class="pt-0">
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="editAccount">
                <v-icon>mdi-newspaper-plus</v-icon>&ensp;更新
              </v-btn>
              <v-btn color="accent" @click="reset">
                <v-icon>mdi-alert</v-icon>&ensp;リセット
              </v-btn>
              <v-btn color="grey" @click="accountEditModal.dialog = false"
                >取消</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>

    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table
          :headers="headers"
          :items="accountList"
          hide-default-footer=true
        >
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.edit_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openAccountEditModal(item.seq_user_id)"
              v-if="isEntryShow"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.delete_flag`]="{ item }">
            <v-icon v-if="item.delete_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.delete_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.header_flag`]="{ item }">
            <v-icon v-if="item.header_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.header_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.footer_flag`]="{ item }">
            <v-icon v-if="item.footer_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.footer_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.mask_flag`]="{ item }">
            <v-icon v-if="item.mask_flag == 1" color="green">mdi-check</v-icon>
            <v-icon v-else-if="item.mask_flag == 0">mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.enclosure_char_flag`]="{ item }">
            <v-icon v-if="item.enclosure_char_flag == 1" color="green"
              >mdi-check</v-icon
            >
            <v-icon v-else-if="item.enclosure_char_flag == 0">mdi-minus</v-icon>
          </template>
        </v-data-table>
        <ConfirmModal ref="confirm" />
        <ProcessFinishModal ref="finish" />
        <AppLoading :loading="loading" />
        <v-pagination
          v-model="paging.page"
          :length="paging.totalPage"
          @input="pageChange()"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";

const axios = require("axios");
let url = process.env.api_base_url + "account";

export default {
  components: {
    AppTitle,
    AppMessageError,
    AppLoading,
    ConfirmModal,
    ProcessFinishModal,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      loading: false,
      isRefShow: false,
      isEntryShow: false,
      accountList: [],
      headers: [
        {
          value: "edit_action",
          sortable: false,
        },
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "メールアドレス",
          value: "mail_address",
        },
        {
          text: "削除フラグ",
          value: "delete_flag",
        },
        {
          text: "パスワード有効期限",
          value: "password_expire",
        },
        {
          text: "備考",
          value: "remarks",
        },
        {
          text: "APIキー",
          value: "api_key",
        },
        {
          text: "登録日時",
          value: "reg_date",
        },
        {
          text: "更新日時",
          value: "update_date",
        },
        {
          text: "ヘッダ利用有無",
          value: "header_flag",
        },
        {
          text: "フッタ利用有無",
          value: "footer_flag",
        },
        {
          text: "マスク利用有無",
          value: "mask_flag",
        },
        {
          text: "囲み文字利用有無",
          value: "enclosure_char_flag",
        },
      ],
      paging: {
        // 現在のページ数
        page: 0,
        // 総ページ数
        totalPage: 0,
      },
      accountEditModal: {
        dialog: false,
        seqUserId: null,
        mailAddress: null,
        passwordExpire: null,
        isDispCalendar: false,
      },
      required: (value) => !!value || "必ず入力してください",
    };
  },
  methods: {
    /**
     * 照会権限判定処理
     */
    checkRefView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "01") {
          this.isRefShow = true;
          return;
        }
      }
      this.isRefShow = false;
    },
    /**
     * 登録権限判定処理
     */
    checkEntryView: function () {
      let roles = this.$store.state.auth.roles;
      for (var i = 0; i < roles.length; i++) {
        let role = roles[i];
        if (role.value == "02") {
          this.isEntryShow = true;
          return;
        }
      }
      this.isEntryShow = false;
    },
    /**
     * アカウント情報取得処理
     * @param page ページ数
     */
    getAccountList: function (page) {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // APIは0~、frontは1~なのでAPIに合わせfrontのページ数に-1
      let reqUrl = url + "?page=" + (page == null ? 0 : page - 1);
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.accountList = response.data.account_list;
            // APIは0~、frontは1~なのでfrontに合わせAPIの戻り値に+1
            this.paging.page = response.data.paging.current_page_num + 1;
            this.paging.totalPage = response.data.paging.total_page;
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
          console.log("accountList [error]=" + error);
          return error;
        }
      );
    },
    /**
     * アカウント情報編集モーダル表示
     * @param seqUserId ユーザID
     */
    openAccountEditModal: function (seqUserId) {
      console.log("[seqUserId]=" + seqUserId);
      this.accountEditModal.dialog = true;
      this.accountEditModal.seqUserId = seqUserId;
      for (var i = 0; i < this.accountList.length; i++) {
        let account = this.accountList[i];
        console.log("[account.seq_user_id]=" + account.seq_user_id);
        if (account.seq_user_id == seqUserId) {
          this.accountEditModal.mailAddress = account.mail_address;
          // カレンダー表示に対応させるため、YYYY-MM-DD形式に変換する
          this.accountEditModal.passwordExpire = account.password_expire.replaceAll(
            "/",
            "-"
          );
          console.log(this.accountEditModal);
          break;
        }
      }
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getAccountList(this.paging.page);
    },
    /**
     * アカウント情報を更新する
     */
    editAccount: function () {
      if (!this.$refs.editAccountForm.validate()) {
        // 入力値エラーの場合
        return;
      }

      this.loading = true;
      let reqUrl = url + "/" + this.accountEditModal.seqUserId;
      let reqBody = {
        mail_address: this.accountEditModal.mailAddress,
        password_expire: this.accountEditModal.passwordExpire.replaceAll(
          "-",
          "/"
        ),
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      let headers = {
        Authorization: this.$store.state.auth.token,
      };

      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result == 0) {
            // 処理完了モーダルを表示
            let json = JSON.stringify(reqBody, null, "\t");
            this.$refs.finish.open("アカウント情報 更新処理", json, {
              color: "blue",
              width: 400,
            });

            // 最新のアカウント情報を取得する
            this.getAccountList();
            this.loading = false;
          }
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("account edit [error]=" + error);
          this.loading = false;
          return error;
        }
      );
      this.accountEditModal.dialog = false;
    },
    /**
     * 編集モーダルの入力情報をクリアする
     */
    reset: function () {
      this.$refs.editAccountForm.reset();
    },
  },
  created: function () {
    this.checkEntryView();
    this.checkRefView();
    if (this.isRefShow) {
      this.getAccountList();
    }
  },
};
</script>

<style scoped>
</style>