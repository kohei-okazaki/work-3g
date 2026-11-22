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
  // 健康管理バッチのレイアウトを適用
  layout: "healthinfoappBatchLayout",
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
          text: "健康管理バッチ",
          disabled: false,
          href: "/healthinfoapp/batch",
        },
        {
          text: "AWS SQS取込バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/sqs/import",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>開始ログを出力",
          edgeType: "circle",
          next: ["sh_prepare"],
        },
        {
          id: "sh_prepare",
          text: "共通設定を読み込み<br/>実行識別子を生成",
          edgeType: "round",
          next: ["sh_lock"],
        },
        {
          id: "sh_lock",
          text: "二重起動防止のロックを取得できたか",
          edgeType: "diamond",
          next: ["sh_busy", "sh_run"],
        },
        {
          id: "sh_busy",
          text: "取得不可<br/>終了コード50で終了",
          edgeType: "round",
        },
        {
          id: "sh_run",
          text: "取得成功<br/>キュー取込バッチをコンテナで実行",
          edgeType: "round",
          next: ["sh_exit_code"],
        },
        {
          id: "sh_exit_code",
          text: "コンテナ実行の終了コードは0か",
          edgeType: "diamond",
          next: ["sh_error", "sh_end"],
        },
        {
          id: "sh_error",
          text: "非0<br/>その時点で異常終了",
          edgeType: "round",
        },
        {
          id: "sh_end",
          text: "0<br/>終了ログを出力してシェル終了",
          edgeType: "round",
        },
        {
          id: "java_start",
          text: "Java処理<br/>ジョブ開始ログを出力",
          edgeType: "round",
          next: ["java_queue"],
        },
        {
          id: "java_queue",
          text: "API通信ログ用のキューを選択",
          edgeType: "round",
          next: ["java_poll"],
        },
        {
          id: "java_poll",
          text: "最大10件のメッセージを受信<br/>受信結果はどうか",
          edgeType: "diamond",
          next: ["java_empty", "java_receive_error", "java_item"],
        },
        {
          id: "java_empty",
          text: "受信なし<br/>取込を終了",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_receive_error",
          text: "受信・変換失敗<br/>取込を中断",
          edgeType: "round",
          next: ["java_failure_notify"],
        },
        {
          id: "java_item",
          text: "受信あり<br/>対象メッセージを1件選択",
          edgeType: "round",
          next: ["java_insert"],
        },
        {
          id: "java_insert",
          text: "受信メッセージをAPI_LOGへ登録<br/>登録は成功したか",
          edgeType: "diamond",
          next: ["java_insert_error", "java_ack"],
        },
        {
          id: "java_insert_error",
          text: "登録失敗<br/>対象をキューから削除せず中断",
          edgeType: "round",
          next: ["java_failure_notify"],
        },
        {
          id: "java_ack",
          text: "登録成功<br/>対象メッセージの削除は成功したか",
          edgeType: "diamond",
          next: ["java_ack_error", "java_remaining"],
        },
        {
          id: "java_ack_error",
          text: "削除失敗<br/>取込を中断",
          edgeType: "round",
          next: ["java_failure_notify"],
        },
        {
          id: "java_remaining",
          text: "削除成功<br/>受信済みの残りはあるか",
          edgeType: "diamond",
          next: ["java_next", "java_repeat"],
        },
        {
          id: "java_next",
          text: "残りあり<br/>次のメッセージを選択",
          edgeType: "round",
          next: ["java_insert"],
        },
        {
          id: "java_repeat",
          text: "残りなし<br/>次の受信へ進む",
          edgeType: "round",
          next: ["java_poll"],
        },
        {
          id: "java_failure_notify",
          text: "エラー概要と詳細をSlack通知",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_notify",
          text: "ジョブ結果をSlack通知し<br/>終了ログを出力",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
