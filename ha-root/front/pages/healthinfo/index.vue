<template>
  <div>
    <AppTitle icon="mdi-pill" title="健康情報一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row>
      <v-col>
        <v-dialog v-model="healthInfoEditModal.dialog" max-width="600px">
          <!-- ダイアログ本体 -->
          <v-card>
            <v-card-title>健康情報更新</v-card-title>
            <v-divider></v-divider>
            <br />
            <v-card-text>
              <v-row>
                <v-col class="text-center" cols="12" sm="12">
                  <v-form ref="editHealthInfoForm">
                    <v-text-field
                      v-model="healthInfoEditModal.height"
                      label="身長"
                      clearable
                      :rules="[required]"
                    ></v-text-field>
                    <v-text-field
                      v-model="healthInfoEditModal.weight"
                      label="体重"
                      clearable
                      :rules="[required]"
                    ></v-text-field
                  ></v-form>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions class="pt-0">
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="editHealthInfo">
                <v-icon>mdi-newspaper-plus</v-icon>&ensp;更新
              </v-btn>
              <v-btn color="accent" @click="reset">
                <v-icon>mdi-alert</v-icon>&ensp;リセット
              </v-btn>
              <v-btn color="grey" @click="healthInfoEditModal.dialog = false"
                >取消</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-row v-if="isRefShow">
      <v-col>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索条件"
          single-line
          hide-details
        ></v-text-field>
        <v-data-table
          :headers="headers"
          :items="healthInfoList"
          :search="search"
        >
          <template v-slot:[`item.edit_action`]="{ item }">
            <v-btn
              small
              class="mx-1"
              @click="openHealthInfoEditModal(item.seq_health_info_id)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
        </v-data-table>
        <AppLoading :loading="loading" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppTitle from "~/components/AppTitle.vue";
import AppMessageError from "~/components/AppMessageError.vue";
import AppLoading from "~/components/AppLoading.vue";

const axios = require("axios");
let url = process.env.api_base_url + "healthinfo";

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
      search: "",
      healthInfoList: [],
      headers: [
        {
          value: "edit_action",
          sortable: false,
        },
        {
          text: "健康情報ID",
          value: "seq_health_info_id",
        },
        {
          text: "ユーザID",
          value: "seq_user_id",
        },
        {
          text: "身長(cm)",
          value: "height",
        },
        {
          text: "体重(kg)",
          value: "weight",
        },
        {
          text: "BMI",
          value: "bmi",
        },
        {
          text: "標準体重(kg)",
          value: "standard_weight",
        },
        {
          text: "前回と比較",
          value: "health_info_status.message",
        },
        {
          text: "肥満度",
          value: "bmi_status.message",
        },
        {
          text: "健康情報登録日時",
          value: "health_info_reg_date",
        },
      ],
      healthInfoEditModal: {
        dialog: false,
        seqHealthInfoId: null,
        seqUserId: null,
        height: null,
        weight: null,
      },
      required: (value) => !!value || "必ず入力してください",
    };
  },
  created: function () {
    this.checkRefView();
    if (this.isRefShow) {
      this.getHealthInfoList();
    }
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
     * 健康情報取得処理
     */
    getHealthInfoList: function () {
      this.loading = true;
      // 保存済のAPIトークンを取得
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      axios.get(url, { headers }).then(
        (response) => {
          if (response.data.result == 0) {
            this.healthInfoList = response.data.health_info_list;
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
          console.log("healthinfo [error]=" + error);
          return error;
        }
      );
    },
    /**
     * 健康情報編集モーダル表示
     * @param seqHealthInfoId 健康情報ID
     */
    openHealthInfoEditModal: function (seqHealthInfoId) {
      this.healthInfoEditModal.dialog = true;
      this.healthInfoEditModal.seqHealthInfoId = seqHealthInfoId;
      for (var i = 0; i < this.healthInfoList.length; i++) {
        let healthInfo = this.healthInfoList[i];
        if (healthInfo.seq_health_info_id == seqHealthInfoId) {
          this.healthInfoEditModal.seqUserId = healthInfo.seq_user_id;
          this.healthInfoEditModal.height = healthInfo.height;
          this.healthInfoEditModal.weight = healthInfo.weight;
          break;
        }
      }
    },
    /**
     * 健康情報編集処理を行う
     */
    editHealthInfo: function () {
      if (!this.$refs.editHealthInfoForm.validate()) {
        // 入力値エラーの場合
        return;
      }
      
      this.loading = true;
      let headers = {
        Authorization: this.$store.state.auth.token,
      };
      let reqUrl = url + "/" + this.healthInfoEditModal.seqHealthInfoId;
      let reqBody = {
        seq_user_id: this.healthInfoEditModal.seqUserId,
        height: this.healthInfoEditModal.height,
        weight: this.healthInfoEditModal.weight,
      };
      console.log(JSON.stringify(reqBody, null, "\t"));

      axios.put(reqUrl, reqBody, { headers }).then(
        (result) => {
          if (result.data.result != "0") {
            this.error.hasError = true;
            this.error.message = result.data.error.message;
          }
          this.loading = false;

          // 最新の健康情報取得
          this.getHealthInfoList();
        },
        (error) => {
          this.error.hasError = true;
          this.error.message = error;
          console.log("[error]=" + error);
          return error;
        }
      );
      this.healthInfoEditModal.dialog = false;
    },
    /**
     * 編集モーダルの入力情報をクリアする
     */
    reset: function () {
      this.$refs.editHealthInfoForm.reset();
    },
  },
};
</script>

<style scoped>
</style>