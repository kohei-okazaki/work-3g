<template>
    <div>
        <v-row>
            <v-col cols="12">
                <AppBreadCrumbs :items="breadcrumbs" />
            </v-col>
        </v-row>
        <v-row>
            <v-col class="text-left" sm="12">
                <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
            </v-col>
        </v-row>
        <v-row align="center">
            <v-col cols="12">
                <v-text-field v-model="search" append-icon="mdi-magnify" label="検索条件" single-line></v-text-field>
                <v-data-table dense :headers="headers" :items="errorCodes" :search="search" :items-per-page="1000"
                    :footer-props="{
                        showFirstLastPage: true,
                        firstIcon: 'mdi-arrow-collapse-left',
                        lastIcon: 'mdi-arrow-collapse-right',
                        prevIcon: 'mdi-minus',
                        nextIcon: 'mdi-plus',
                    }">
                    <template v-slot:[`item.innerErrorCode`]="{ item }">
                        <span>{{ item.innerErrorCode }}</span>
                    </template>
                    <template v-slot:[`item.outerErrorCode`]="{ item }">
                        <span>{{ item.outerErrorCode }}</span>
                    </template>
                    <template v-slot:[`item.message`]="{ item }">
                        <span v-html="item.message"></span>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
    </div>
</template>
<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
export default {
    // Wikiのレイアウトを適用
    layout: "wikiLayout",
    components: {
        AppBreadCrumbs,
        AppContentsTitle,
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
                    text: "Wiki",
                    disabled: false,
                    href: "/wiki",
                },
                {
                    text: "エラーコード一覧",
                    disabled: true,
                    href: "/wiki/errorcode",
                },
            ],
            headers: [
                {
                    text: "内部エラーコード",
                    value: "innerErrorCode",
                },
                {
                    text: "外部エラーコード",
                    value: "outerErrorCode",
                },
                {
                    text: "メッセージ内容",
                    value: "message",
                },
            ],
            errorCodes: [
                {
                    innerErrorCode: "RUNTIME_ERROR",
                    outerErrorCode: "CE0001",
                    message: "実行時にエラーが発生しました",
                },
                {
                    innerErrorCode: "UNEXPECTED_ERROR",
                    outerErrorCode: "CE0002",
                    message: "予期せぬエラーが発生しました",
                },
                {
                    innerErrorCode: "ALGORITH_ERROR",
                    outerErrorCode: "CE0003",
                    message: "アルゴリズムが不正です",
                },
                {
                    innerErrorCode: "DB_CRYPT_ERROR",
                    outerErrorCode: "CE0004",
                    message: "DBが不正なため、暗号化と復号に失敗しました",
                },
                {
                    innerErrorCode: "DB_ACCESS_ERROR",
                    outerErrorCode: "CE0005",
                    message: "DBへの接続に失敗しました",
                },
                {
                    innerErrorCode: "DB_SQL_EXE_ERROR",
                    outerErrorCode: "CE0006",
                    message: "SQLの実行に失敗しました",
                },
                {
                    innerErrorCode: "DB_SQL_SELECT_ERROR",
                    outerErrorCode: "CE0007",
                    message: "DBの検索に失敗しました",
                },
                {
                    innerErrorCode: "DB_CLOSE_ERROR",
                    outerErrorCode: "CE0008",
                    message: "DB切断に失敗しました",
                },
                {
                    innerErrorCode: "DB_NO_DATA",
                    outerErrorCode: "CW0009",
                    message: "指定されたデータが存在しません",
                },
                {
                    innerErrorCode: "JSON_FORMAT_ERROR",
                    outerErrorCode: "CW0010",
                    message: "JSONのフォーマットが不正です",
                },
                {
                    innerErrorCode: "JSON_MAPPING_ERROR",
                    outerErrorCode: "CW0011",
                    message: "JSONとのマッピングに失敗しました",
                },
                {
                    innerErrorCode: "JSON_PARSE_ERROR",
                    outerErrorCode: "CW0012",
                    message: "JSONが不正です",
                },
                {
                    innerErrorCode: "FILE_UPLOAD_ERROR",
                    outerErrorCode: "CW0013",
                    message: "ファイルアップロードに失敗しました",
                },
                {
                    innerErrorCode: "FILE_READING_ERROR",
                    outerErrorCode: "CW0014",
                    message: "ファイルの読込に失敗しました",
                },
                {
                    innerErrorCode: "FILE_WRITE_ERROR",
                    outerErrorCode: "CW0015",
                    message: "ファイルの書込に失敗しました",
                },
                {
                    innerErrorCode: "MULTIPLE_DATA",
                    outerErrorCode: "CW0016",
                    message: "該当のデータが複数存在します",
                },
                {
                    innerErrorCode: "VALIDATE_ERROR",
                    outerErrorCode: "CW0017",
                    message: "入力チェックエラーが発生しました",
                },
                {
                    innerErrorCode: "API_EXEC_ERROR",
                    outerErrorCode: "AE0018",
                    message: "指定したユーザでAPIを実行出来ません",
                },
                {
                    innerErrorCode: "API_REQUIR",
                    outerErrorCode: "AW0019",
                    message: "必須項目が指定されていません",
                },
                {
                    innerErrorCode: "REQUEST_TYPE_INVALID_ERROR",
                    outerErrorCode: "AW0020",
                    message: "リクエスト種別が不正です",
                },
                {
                    innerErrorCode: "ACCOUNT_ILLEGAL",
                    outerErrorCode: "DW0021",
                    message: "指定したアカウントは不正です",
                },
                {
                    innerErrorCode: "ACCOUNT_DELETE",
                    outerErrorCode: "DW0022",
                    message: "指定したアカウントは削除されています",
                },
                {
                    innerErrorCode: "ACCOUNT_EXIST",
                    outerErrorCode: "DW0023",
                    message: "指定したアカウントは存在しません",
                },
                {
                    innerErrorCode: "ACCOUNT_INVALID_PASSWORD",
                    outerErrorCode: "DW0024",
                    message: "パスワードが一致しません",
                },
                {
                    innerErrorCode: "ACCOUNT_EXPIRED",
                    outerErrorCode: "DW0025",
                    message: "指定したアカウントは有効期限を超過しています",
                },
                {
                    innerErrorCode: "ILLEGAL_ACCESS_ERROR",
                    outerErrorCode: "DW0026",
                    message: "不正リクエストエラーが発生しました",
                },
                {
                    innerErrorCode: "AWS_S3_DOWNLOAD_ERROR",
                    outerErrorCode: "CE0027",
                    message: "S3からのダウンロード処理が失敗しました",
                },
                {
                    innerErrorCode: "AWS_S3_UPLOAD_ERROR",
                    outerErrorCode: "CE0028",
                    message: "S3からのアップロード処理が失敗しました",
                },
                {
                    innerErrorCode: "AWS_CLIENT_CONNECT_ERROR",
                    outerErrorCode: "CE0029",
                    message: "AWSへの接続に失敗しました",
                },
                {
                    innerErrorCode: "USER_REGIST_ERROR",
                    outerErrorCode: "BE0030",
                    message: "ユーザの作成に失敗しました",
                },
                {
                    innerErrorCode: "MULTI_SUBMIT_ERROR",
                    outerErrorCode: "DW0031",
                    message: "画面で複数回送信ボタンを押下したため、失敗しました",
                },
                {
                    innerErrorCode: "BASIC_API_CONNERR",
                    outerErrorCode: "BE0032",
                    message: "基礎健康情報計算APIの通信に失敗しました",
                },
                {
                    innerErrorCode: "CALORIE_API_CONNERR",
                    outerErrorCode: "BE0033",
                    message: "カロリー計算APIの通信に失敗しました",
                },
                {
                    innerErrorCode: "AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR",
                    outerErrorCode: "CE0034",
                    message: "送信先メールアドレスがSESに認証されていません<br>メールアドレスを確認するように連絡してください",
                },
                {
                    innerErrorCode: "API_400_CONNECT_ERROR",
                    outerErrorCode: "CE0035",
                    message: "API通信に失敗しました、サーバの状態が正しくないため管理者に連絡して下さい",
                },
                {
                    innerErrorCode: "API_500_CONNECT_ERROR",
                    outerErrorCode: "CE0036",
                    message: "API通信に失敗しました、サーバの状態が正しくないため管理者に連絡して下さい",
                },
                {
                    innerErrorCode: "BREATHING_API_CONNECT_ERROR",
                    outerErrorCode: "BE0037",
                    message: "肺活量計算APIの通信に失敗しました",
                },
                {
                    innerErrorCode: "TOKEN_API_CONNECT_ERROR",
                    outerErrorCode: "BE0038",
                    message: "Token発行APIの通信に失敗しました",
                },
                {
                    innerErrorCode: "HEALTH_INFO_REGIST_API_CONNECT_ERROR",
                    outerErrorCode: "BE0039",
                    message: "健康情報登録APIの通信に失敗しました",
                },
                {
                    innerErrorCode: "FILE_OR_DIR_ERROR",
                    outerErrorCode: "CW0040",
                    message: "ファイルまたはフォルダが存在しません",
                },
                {
                    innerErrorCode: "AWS_SQS_ENQUEUE_ERROR",
                    outerErrorCode: "CE0041",
                    message: "SQSへキューの登録に失敗しました",
                },
                {
                    innerErrorCode: "AWS_SQS_POLL_ERROR",
                    outerErrorCode: "CE0042",
                    message: "SQSへキューの取得に失敗しました",
                },
                {
                    innerErrorCode: "AWS_SQS_ACK_ERROR",
                    outerErrorCode: "CE0043",
                    message: "SQSへキューの削除に失敗しました",
                },
                {
                    innerErrorCode: "AWS_S3_DELETE_ERROR",
                    outerErrorCode: "CE0044",
                    message: "S3ファイルの削除に失敗しました",
                },
                {
                    innerErrorCode: "USER_UPDATE_ERROR",
                    outerErrorCode: "BE0045",
                    message: "ユーザの更新に失敗しました",
                },
                {
                    innerErrorCode: "MAIL_TEMPLATE_REQUIED_ERROR",
                    outerErrorCode: "CE0046",
                    message: "メール送信に失敗しました",
                },
                {
                    innerErrorCode: "AWS_SSM_GET_ERROR",
                    outerErrorCode: "CE0047",
                    message: "Systems Managerから値の取得に失敗しました",
                },
            ],
        }
    }
}
</script>
<style scoped></style>