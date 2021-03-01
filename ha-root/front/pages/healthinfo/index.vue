<template>
  <div>
    <AppTitle icon="mdi-pill" title="健康情報一覧" />
    <AppMessageError v-if="error.hasError" :data="error" />
    <v-row v-if="isEditMode">
      <v-col>
        <HealthInfoEdit :healthInfoEditForm="healthInfoEditForm" />
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
              @click="changeEditView(item.seq_health_info_id)"
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
import HealthInfoEdit from "~/components/healthinfo/HealthInfoEdit.vue";

const axios = require("axios");
let url = process.env.api_base_url + "healthinfo";

export default {
  components: {
    AppTitle,
    AppMessageError,
    AppLoading,
    HealthInfoEdit,
  },
  data: function () {
    return {
      error: {
        hasError: false,
        message: null,
      },
      loading: false,
      isEditMode: false,
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
      healthInfoEditForm: {
        seqHealthInfoId: null,
        height: null,
        weight: null,
      },
    };
  },
  created: function () {
    this.isRefView();
    if (!this.isRefShow) {
      return;
    }
    this.getHealthInfoList();
  },
  methods: {
    isRefView: function () {
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
    changeEditView: function (seqHealthInfoId) {
      for (var i = 0; i < this.healthInfoList.length; i++) {
        let healthInfo = this.healthInfoList[i];
        if (healthInfo.seq_health_info_id == seqHealthInfoId) {
          this.healthInfoEditForm.seqHealthInfoId = healthInfo.seq_health_info_id;
          this.healthInfoEditForm.height = healthInfo.height;
          this.healthInfoEditForm.weight = healthInfo.weight;
          this.isEditMode = true;
          break;
        }
      }
    },
  },
};
</script>

<style scoped>
</style>