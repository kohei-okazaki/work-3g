<template>
  <div>
    <v-row justify="center">
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppDocs :flow="flow" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppDocs from "~/components/AppDocs.vue";

export default {
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppDocs,
  },
  data: function () {
    return {
      breadcrumbs: [
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "管理者サイト用API",
          disabled: false,
          href: "/root/api",
        },
        {
          text: "問い合わせ情報編集API",
          disabled: true,
          href: "/root/api/inquiry/edit",
        },
      ],
      flow: [
        {
          id: "start",
          text: "リクエスト受付",
          edgeType: "round",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "JWTの署名と有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "validate"],
        },
        {
          id: "auth_error",
          text: "認証不可<br/>処理を終了",
          edgeType: "round",
        },
        {
          id: "validate",
          text: "認証成功<br/>変更後の状態と担当者IDを検証",
          edgeType: "diamond",
          next: ["validation_error", "exists"],
        },
        {
          id: "validation_error",
          text: "入力不正<br/>Slack通知後にHTTP 400で<br/>処理結果1のJSONを返却",
          edgeType: "round",
        },
        {
          id: "exists",
          text: "INQUIRY_MANAGEMENTの件数で存在確認<br/>INQUIRY_MANAGEMENT.SEQ_INQUIRY_MNG_ID =<br/>パス.seq_inquriy_mng_id<br/>AND INQUIRY_MANAGEMENT.DELETE_FLAG = false",
          edgeType: "diamond",
          next: ["not_found", "update"],
        },
        {
          id: "not_found",
          text: "対象なし<br/>HTTP 400で処理結果1のJSONを返却",
          edgeType: "round",
        },
        {
          id: "update",
          text: "INQUIRY_MANAGEMENT更新<br/>INQUIRY_MANAGEMENT.SEQ_INQUIRY_MNG_ID =<br/>パス.seq_inquriy_mng_id<br/>INQUIRY_MANAGEMENT.INQUIRY_STATUS = ボディ.status<br/>INQUIRY_MANAGEMENT.RESPONDER_LOGIN_ID =<br/>ボディ.seq_login_id<br/>INQUIRY_MANAGEMENT.UPDATE_DATE = システム日時",
          edgeType: "round",
          next: ["result"],
        },
        {
          id: "result",
          text: "更新処理の結果",
          edgeType: "diamond",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "正常終了<br/>HTTP 200で処理結果0のJSONを返却",
          edgeType: "round",
        },
        {
          id: "exception",
          text: "例外発生<br/>Slack通知後にHTTP 500で<br/>処理結果1のJSONを返却",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
