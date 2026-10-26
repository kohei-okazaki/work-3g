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
          text: "健康情報一括登録バッチ",
          disabled: true,
          href: "/healthinfoapp/batch/healthinfo/regist",
        },
      ],
      flow: [
        {
          id: "sh_start",
          text: "シェル処理<br/>健康情報一括登録を開始",
          edgeType: "circle",
          next: ["sh_common"],
        },
        {
          id: "sh_common",
          text: "共通設定を読込<br/>実行を識別するIDを生成",
          edgeType: "round",
          next: ["sh_lock"],
        },
        {
          id: "sh_lock",
          text: "同じシェルの実行ロックを取得",
          edgeType: "diamond",
          next: ["sh_locked", "sh_run"],
        },
        {
          id: "sh_locked",
          text: "取得不可: 二重起動として終了<br/>終了コード50",
          edgeType: "round",
        },
        {
          id: "sh_run",
          text: "DockerでJavaバッチを起動して待機<br/>実行IDを渡す",
          edgeType: "round",
          next: ["sh_failed", "sh_end"],
        },
        {
          id: "sh_failed",
          text: "Docker実行が非0: シェル停止",
          edgeType: "round",
        },
        {
          id: "sh_end",
          text: "コマンド完了後に終了メッセージ出力",
          edgeType: "round",
        },
        {
          id: "java_start",
          text: "Java処理<br/>健康情報一括登録を開始",
          edgeType: "circle",
          next: ["java_open"],
        },
        {
          id: "java_open",
          text: "開始ログを記録<br/>入力フォルダの存在と配下全ファイルを確認",
          edgeType: "round",
          next: ["java_exception", "java_read"],
        },
        {
          id: "java_read",
          text: "次のJSON明細を読込・通信IDを設定<br/>空の明細は次ファイルへ・0件は終了",
          edgeType: "diamond",
          next: ["java_exception", "java_validate", "java_ids"],
        },
        {
          id: "java_validate",
          text: "ユーザID・身長・体重を検証<br/>APIキーはこの段階の検証から除外",
          edgeType: "diamond",
          next: ["java_exception", "java_user"],
        },
        {
          id: "java_user",
          text: "USER検索<br/>USER.SEQ_USER_ID = 入力JSON.seq_user_id<br/>USER.API_KEYを取得<br/>削除状態による絞込なし<br/>ユーザ未存在はエラー",
          edgeType: "round",
          next: ["java_exception", "java_api"],
        },
        {
          id: "java_api",
          text: "健康情報登録APIを呼び出す<br/>返された健康情報IDがあれば収集",
          edgeType: "round",
          next: ["java_exception", "java_log"],
          url: "/healthinfoapp/api/healthinfo/regist",
        },
        {
          id: "java_log",
          text: "通信ログをSQS送信<br/>APIの失敗結果だけでは中断しない",
          edgeType: "round",
          next: ["java_exception", "java_next"],
        },
        {
          id: "java_next",
          text: "1件単位で処理を確定<br/>次の明細へ",
          edgeType: "round",
          next: ["java_read"],
        },
        {
          id: "java_exception",
          text: "外へ伝わった例外を処理<br/>一部通信例外は上限付き再試行<br/>HTTP4xx例外は最大10件スキップ",
          edgeType: "diamond",
          next: ["java_next", "java_failed"],
        },
        {
          id: "java_failed",
          text: "継続不可: ジョブ失敗を記録",
          edgeType: "round",
          next: ["java_ids"],
        },
        {
          id: "java_ids",
          text: "処理終了時に収集した登録IDがあれば<br/>ID一覧をSlack通知",
          edgeType: "round",
          next: ["java_notify"],
        },
        {
          id: "java_notify",
          text: "終了状態をSlack通知・ログ記録<br/>ジョブ失敗時は例外詳細も通知",
          edgeType: "round",
          next: ["java_end"],
        },
        {
          id: "java_end",
          text: "Java処理を終了",
          edgeType: "circle",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>