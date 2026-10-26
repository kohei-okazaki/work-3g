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
          text: "問い合わせ情報一覧取得API",
          disabled: true,
          href: "/root/api/inquiry",
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
          text: "認証成功<br/>ページ番号の型と範囲を検証",
          edgeType: "diamond",
          next: ["validation_error", "page"],
        },
        {
          id: "validation_error",
          text: "入力不正<br/>Slack通知後にHTTP 400で<br/>処理結果1のJSONを返却",
          edgeType: "round",
        },
        {
          id: "page",
          text: "入力正常<br/>ページ番号と表示件数を設定",
          edgeType: "round",
          next: ["list"],
        },
        {
          id: "list",
          text: "INQUIRY_MANAGEMENT検索<br/>LEFT JOIN INQUIRY_STATUS_MT<br/>INQUIRY_MANAGEMENT.INQUIRY_STATUS =<br/>INQUIRY_STATUS_MT.INQUIRY_STATUS<br/>AND INQUIRY_STATUS_MT.DELETE_FLAG = false<br/>LEFT JOIN INQUIRY_TYPE_MT<br/>INQUIRY_MANAGEMENT.INQUIRY_TYPE =<br/>INQUIRY_TYPE_MT.INQUIRY_TYPE<br/>AND INQUIRY_TYPE_MT.DELETE_FLAG = false<br/>本体のWHERE条件なし<br/>ORDER BY INQUIRY_MANAGEMENT.REG_DATE ASC<br/>指定ページの開始位置・設定件数で取得",
          edgeType: "round",
          next: ["body"],
        },
        {
          id: "body",
          text: "INQUIRY_MANAGEMENT.S3_KEYをキーに本文をS3取得<br/>状態名・種別名などとともに一覧を作成",
          edgeType: "round",
          next: ["paging"],
        },
        {
          id: "paging",
          text: "INQUIRY_MANAGEMENTの件数からページ情報を作成<br/>INQUIRY_MANAGEMENT.DELETE_FLAG = false",
          edgeType: "round",
          next: ["result"],
        },
        {
          id: "result",
          text: "一覧取得処理の結果",
          edgeType: "diamond",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "正常終了<br/>一覧とページ情報をHTTP 200で<br/>処理結果0のJSONとして返却",
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
