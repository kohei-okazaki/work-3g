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
          text: "健康情報編集API",
          disabled: true,
          href: "/root/api/healthinfo/edit",
        },
      ],
      flow: [
        {
          id: "start",
          text: "リクエスト受付",
          edgeType: "circle",
          next: ["auth"],
        },
        {
          id: "auth",
          text: "認証トークンの署名・有効期限を検証",
          edgeType: "diamond",
          next: ["auth_error", "validate"],
        },
        {
          id: "auth_error",
          text: "認証不可：処理終了",
          edgeType: "circle",
        },
        {
          id: "validate",
          text: "認証成功：ユーザIDの必須と<br/>身長・体重の必須・数値範囲を検証",
          edgeType: "diamond",
          next: ["bad_request", "exists"],
        },
        {
          id: "bad_request",
          text: "入力不正：Slack通知・ログ出力<br/>HTTP 400／result: #quot;1#quot;",
          edgeType: "circle",
        },
        {
          id: "exists",
          text: "HEALTH_INFO件数確認<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>パス.seq_health_info_id<br/>AND HEALTH_INFO.SEQ_USER_ID = ボディ.seq_user_id<br/>HEALTH_INFO.DELETE_FLAG条件なし",
          edgeType: "diamond",
          next: ["not_found", "prepare"],
        },
        {
          id: "not_found",
          text: "対象なし：HTTP 400でエラー返却<br/>result: #quot;1#quot;",
          edgeType: "circle",
        },
        {
          id: "prepare",
          text: "対象あり：通信ログの関連付けIDを発行<br/>身長・体重を計算APIへ渡す準備",
          edgeType: "round",
          next: ["connection", "exception"],
        },
        {
          id: "connection",
          text: "計算APIの接続方式",
          edgeType: "diamond",
          next: ["direct", "token"],
        },
        {
          id: "direct",
          text: "移行済み：トークン取得を省略",
          edgeType: "round",
          next: ["calculate"],
        },
        {
          id: "token",
          text: "従来方式：トークン発行後に通信ログをSQS送信<br/>APIの成功結果を確認",
          edgeType: "diamond",
          next: ["token_ok", "exception"],
        },
        {
          id: "token_ok",
          text: "成功：発行トークンを計算APIに付与",
          edgeType: "round",
          next: ["calculate"],
        },
        {
          id: "calculate",
          text: "計算APIでBMI・標準体重を取得し<br/>通信ログをSQS送信してAPI結果を確認",
          edgeType: "diamond",
          next: ["range", "exception"],
        },
        {
          id: "range",
          text: "BMI_RANGE_MTのキャッシュ参照<br/>未取得・期限切れ時はBMI_RANGE_MT全件検索<br/>WHERE・ORDER BY指定なし<br/>BMI_RANGE_MT.RANGE_MIN #lt;= 計算API応答.bmi<br/>AND 計算API応答.bmi #lt; BMI_RANGE_MT.RANGE_MAX<br/>該当する先頭の範囲を選択",
          edgeType: "diamond",
          next: ["update", "exception"],
        },
        {
          id: "update",
          text: "HEALTH_INFO更新<br/>HEALTH_INFO.SEQ_HEALTH_INFO_ID =<br/>パス.seq_health_info_id<br/>計算API応答の身長・体重・BMI・標準体重と<br/>選択したBMI_RANGE_MT.SEQ_BMI_RANGE_MT_IDを設定<br/>ユーザIDの更新条件なし・更新日時を設定",
          edgeType: "round",
          next: ["success", "exception"],
        },
        {
          id: "success",
          text: "例外なし：HTTP 200でJSON返却<br/>result: #quot;0#quot;",
          edgeType: "circle",
        },
        {
          id: "exception",
          text: "API失敗・範囲なし・処理例外<br/>Slack通知・ログ出力",
          edgeType: "round",
          next: ["server_error"],
        },
        {
          id: "server_error",
          text: "HTTP 500でエラーJSON返却<br/>result: #quot;1#quot;",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
