<template>
  <div>
    <AppTitle icon="mdi-notebook" title="メモ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />

    <template v-if="isEntryShow">
      <template v-if="entryMode">
        <NoteEntry @get-notes="getNotes" />
        <v-dialog v-model="noteEditModal.dialog" max-width="600px">
          <!-- ダイアログ本体 -->
          <v-card>
            <v-card-title>メモ情報更新</v-card-title>
            <v-divider></v-divider>
            <br />
            <v-card-text>
              <v-row>
                <v-col class="text-center" cols="12" sm="12">
                  <v-form ref="editNoteForm">
                    <v-text-field
                      v-model="noteEditModal.title"
                      label="タイトル"
                      clearable
                      :rules="[required]"
                    ></v-text-field>
                    <v-textarea
                      v-model="noteEditModal.detail"
                      label="詳細(htmlタグの入力も可能です)"
                      clearable
                      :rules="[required]"
                    ></v-textarea
                  ></v-form>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions class="pt-0">
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="editNote">
                <v-icon>mdi-newspaper-plus</v-icon>&ensp;更新
              </v-btn>
              <v-btn color="accent" @click="reset">
                <v-icon>mdi-alert</v-icon>&ensp;リセット
              </v-btn>
              <v-btn color="grey" @click="noteEditModal.dialog = false"
                >取消</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
    </template>

    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table
          :headers="headers"
          :items="note_list"
          hide-default-footer="true"
        >
          <template v-slot:[`item.edit_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openNoteEditModal(item.seq_root_user_note_info_id)"
              v-if="isEntryShow"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.delete_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openNoteDeleteModal(item.seq_root_user_note_info_id)"
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
          <template v-slot:[`item.detail`]="{ item }">
            {{ viewMemoDetail(item.detail) }}
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
import NoteEntry from "~/components/note/NoteEntry.vue";
import ConfirmModal from "~/components/modal/ConfirmModal.vue";
import ProcessFinishModal from "~/components/modal/ProcessFinishModal.vue";
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "note";

export default {
  components: {
    NoteEntry,
    ConfirmModal,
    ProcessFinishModal,
    AppTitle,
    AppMessageError,
    AppLoading,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      isRefShow: false,
      isEntryShow: false,
      entryMode: true,
      loading: false,
      headers: [
        {
          text: "",
          value: "edit_action",
          sortable: false,
        },
        {
          text: "",
          value: "delete_action",
          sortable: false,
        },
        {
          text: "タイトル",
          value: "title",
          sortable: false,
        },
        {
          text: "詳細",
          value: "detail",
          sortable: false,
        },
        {
          text: "登録日時",
          value: "reg_date",
          sortable: false,
        },
        {
          text: "更新日時",
          value: "update_date",
          sortable: false,
        },
      ],
      note_list: [],
      paging: {
        // 現在のページ数
        page: 0,
        // 総ページ数
        totalPage: 0,
      },
      noteEditModal: {
        dialog: false,
        seq_root_user_note_info_id: null,
        title: null,
        detail: null,
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
     * 指定したページのメモ情報取得処理
     * @param page 取得対象ページ
     */
    getNotes: function (page) {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + "?seq_login_id=" + this.$store.state.auth.seq_login_id;
      // APIは0~、frontは1~なのでAPIに合わせfrontのページ数に-1
      reqUrl += "&page=" + (page == null ? 0 : page - 1);
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result === "0") {
            this.note_list = response.data.note_list;
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
          console.log("[error]=" + error);
          return error;
        }
      );
    },
    /**
     * メモ内容表示制御
     * @param detail メモ内容
     */
    viewMemoDetail: function (detail) {
      // 文字列長が15byteより大きい場合、先頭15byteのみを表示
      return detail.length > 15 ? detail.substr(0, 15) + "..." : detail;
    },
    /**
     * メモ情報編集モーダル表示
     * @param seq_root_user_note_info_id 管理者サイトユーザメモ情報ID
     */
    openNoteEditModal: function (seq_root_user_note_info_id) {
      this.noteEditModal.dialog = true;
      this.noteEditModal.seq_root_user_note_info_id =
        seq_root_user_note_info_id;
      let temp_note = this.note_list.find(
        (o) => o.seq_root_user_note_info_id == seq_root_user_note_info_id
      );
      this.noteEditModal.title = temp_note?.title;
      this.noteEditModal.detail = temp_note?.detail;
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getNotes(this.paging.page);
    },
    /**
     * メモ情報編集を行う
     */
    editNote: function () {
      if (!this.$refs.editNoteForm.validate()) {
        // 入力値エラーの場合
        return;
      }

      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + "/" + this.noteEditModal.seq_root_user_note_info_id;
      let reqBody = {
        title: this.noteEditModal.title,
        detail: this.noteEditModal.detail,
      };

      axios.put(reqUrl, reqBody, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            // 処理完了モーダルを表示
            this.$refs.finish.open("メモ情報 更新処理", reqBody.detail, {
              color: "blue",
              width: 400,
            });
          } else {
            this.error.hasError = true;
            this.error.message = response.data.error.message;
          }

          // 最新のメモ情報取得
          this.getNotes();
          this.loading = false;
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          this.loading = false;
          console.log("[error]=" + error);
          return error;
        }
      );
      this.noteEditModal.dialog = false;
    },
    /**
     * 編集モーダルの入力情報をクリアする
     */
    reset: function () {
      this.$refs.editNoteForm.reset();
    },
    /**
     * メモ情報削除モーダル表示
     * @param seq_root_user_note_info_id 管理者サイトユーザメモ情報ID
     */
    async openNoteDeleteModal(seq_root_user_note_info_id) {
      let modalInfo = {
        color: "red",
        width: 400,
      };

      if (
        await this.$refs.confirm.open(
          "削除確認",
          "このメモを削除しますか？",
          modalInfo
        )
      ) {
        // 確認モーダルで削除に同意した場合、メモ情報削除APIを呼出
        this.deleteNote(seq_root_user_note_info_id);
      }
    },
    /**
     * メモ情報削除処理
     * @param seq_root_user_note_info_id 管理者サイトユーザメモ情報ID
     */
    async deleteNote(seq_root_user_note_info_id) {
      this.loading = true;
      let deleteUrl = url + "/" + seq_root_user_note_info_id;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios
        .delete(deleteUrl, {
          headers,
        })
        .then(
          (response) => {
            if (response.data.result == "0") {
              // 削除成功時

              // 処理完了モーダルを表示
              this.$refs.finish.open("メモ情報削除", "", {
                color: "blue",
                width: 400,
              });

              // 最新メモ情報取得する
              this.getNotes();
              this.loading = false;
            }
          },
          (error) => {
            this.error.hasError = true;
            this.error.message = error;
            this.loading = false;
            console.log("[error]=" + error);
            return error;
          }
        );
    },
  },
  created: function () {
    this.checkEntryView();
    this.checkRefView();
    if (this.isRefShow) {
      // 照会権限がある場合、最新メモ情報取得
      this.getNotes();
    }
  },
};
</script>

<style scoped></style>
