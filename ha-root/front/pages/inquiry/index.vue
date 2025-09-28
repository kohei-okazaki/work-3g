<template>
  <div>
    <AppTitle icon="mdi-notebook" title="問い合わせ一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row v-if="isRefShow">
      <v-col>
        <v-data-table
          :headers="headers"
          :items="inquiryList"
          hide-default-footer="true"
        >
          <!-- 詳細ボタン -->
          <template v-slot:[`item.detail_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openInquiryDetailModal(item.seq_inquiry_mng_id)"
              v-if="isRefShow"
            >
              <v-icon>mdi-information</v-icon>
            </v-btn>
          </template>

          <!-- ステータス列をプルダウンに -->
          <template v-slot:[`item.status`]="{ item }">
            <div v-if="isEntryShow">
              <v-select
                :items="inquiryStatusMtList"
                item-text="label"
                item-value="value"
                dense
                hide-details
                style="max-width: 90px"
                :value="item.status?.value"
                @change="(val) => onChangeStatus(item, val)"
              />
            </div>
            <div v-else>
              <v-chip small>{{ item.status.label }}</v-chip>
            </div>
          </template>
        </v-data-table>
        <AppLoading :loading="loading" />
        <v-pagination
          v-model="paging.page"
          :length="paging.totalPage"
          @input="pageChange()"
        />
      </v-col>
    </v-row>
    <v-dialog v-model="inquiryDetailModal.dialog" max-width="700px">
      <!-- ダイアログ本体 -->
      <v-card>
        <v-card-title>問い合わせ内容</v-card-title>
        <v-divider></v-divider>
        <br />
        <v-card-text>
          <v-row>
            <v-col class="text-center" cols="12" sm="12">
              <v-textarea v-model="inquiryDetailModal.body"></v-textarea>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions class="pt-0">
          <v-spacer></v-spacer>
          <v-btn color="grey" @click="inquiryDetailModal.dialog = false"
            >閉じる</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "inquiry";

export default {
  components: {
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
      loading: false,
      isRefShow: false,
      isEntryShow: false,
      inquiryList: [],
      inquiryStatusMtList: [],
      headers: [
        {
          text: "",
          value: "detail_action",
          sortable: false,
        },
        {
          text: "管理ID",
          value: "seq_inquiry_mng_id",
          sortable: false,
        },
        {
          text: "問い合わせユーザID",
          value: "seq_user_id",
          sortable: false,
        },
        {
          text: "対応者ID",
          value: "responder_login_id",
          sortable: false,
        },
        {
          text: "ステータス",
          value: "status",
          sortable: true,
        },
        {
          text: "問い合わせ種別",
          value: "type.label",
          sortable: true,
        },
        {
          text: "タイトル",
          value: "title",
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
      paging: {
        // 現在のページ数
        page: 0,
        // 総ページ数
        totalPage: 0,
      },
      inquiryDetailModal: {
        dialog: false,
        seq_inquiry_mng_id: null,
        body: null,
      },
    };
  },
  created: function () {
    this.checkEntryView();
    this.checkRefView();
    if (this.isRefShow) {
      // 照会権限がある場合、問い合わせ情報取得
      this.getInquiryList();
    }
    this.getInquiryStatusMtList();
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
     * 問い合わせステータスマスタリストを取得
     */
    getInquiryStatusMtList: function () {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // 問い合わせステータスマスタ一覧取得API
      let reqUrl = url + "/status";
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.inquiryStatusMtList = response.data.statuses;
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
          console.log("inquiry [error]=" + error);
          return error;
        }
      );
    },
    /**
     * 指定したページ数の問い合わせ情報リストを取得
     * @param page ページ数
     */
    getInquiryList: function (page) {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // 問い合わせ情報一覧取得API
      // APIは0~、frontは1~なのでAPIに合わせfrontのページ数に-1
      let reqUrl = url + "?page=" + (page == null ? 0 : page - 1);
      axios.get(reqUrl, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.inquiryList = response.data.inquiry_list;
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
          console.log("inquiry [error]=" + error);
          return error;
        }
      );
    },
    /**
     * 問い合わせ情報詳細モーダル表示
     * @param seq_inquiry_mng_id 問い合わせ管理ID
     */
    openInquiryDetailModal: function (seq_inquiry_mng_id) {
      this.inquiryDetailModal.dialog = true;
      this.inquiryDetailModal.seq_inquiry_mng_id = seq_inquiry_mng_id;
      this.inquiryDetailModal.body = this.inquiryList.find(
        (o) => o.seq_inquiry_mng_id === seq_inquiry_mng_id
      )?.body;
    },
    /**
     * ページ切り替え処理
     */
    pageChange: function () {
      this.getInquiryList(this.paging.page);
    },
    /**
     * ステータス変更ハンドラー
     * @param item 行レコード
     * @param newValue 変更値
     */
    onChangeStatus: function (item, newValue) {
      let oldValue = item.status?.value;

      // ステータス更新処理 呼出
      this.updateInquiryStatus(item.seq_inquiry_mng_id, newValue)
        .then(() => {
          // 成功時は何もしない（表示は既に更新済）
        })
        .catch((err) => {
          // 失敗時はロールバック
          let rollback = this.inquiryStatusMtList.find(
            (o) => o.value == oldValue
          );
          item.status = {
            value: oldValue,
            label: rollback ? rollback.label : oldValue,
          };

          this.error.hasError = true;
          this.error.message =
            err && err.message ? err.message : "ステータス更新に失敗しました";
          // 必要ならトースト表示など
        });
    },
    /**
     * 問い合わせステータスの更新処理
     * @param seqInquiryMngId 問い合わせ管理ID
     * @param statusValue 新しいステータス値
     */
    async updateInquiryStatus(seqInquiryMngId, statusValue) {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      // 問い合わせ情報編集API
      let reqUrl = url + "/" + seqInquiryMngId;
      let reqBody = {
        status: statusValue,
        seq_login_id: this.$store.state.auth.seq_login_id,
      };

      try {
        let response = await axios.put(reqUrl, reqBody, { headers });
        if (response.data?.result != 0) {
          // APIがエラー時の返しを持っている想定
          let msg = response.data?.error?.message || "更新に失敗しました";
          throw new Error(msg);
        }
      } catch (error) {
        throw error;
      } finally {
        this.loading = false;
        this.getInquiryList(this.paging.page);
      }
    },
  },
};
</script>

<style scoped></style>
