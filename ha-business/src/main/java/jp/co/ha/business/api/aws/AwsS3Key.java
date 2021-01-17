package jp.co.ha.business.api.aws;

import jp.co.ha.common.type.BaseEnum;

/**
 * AWSのS3のキー列挙
 *
 * @version 1.0.0
 */
public enum AwsS3Key implements BaseEnum {

    /** 健康情報一括登録CSVファイル配置キー */
    HEALTHINFO_FILE_REGIST("healthinfo-file-regist/"),
    /** 健康情報照会CSVファイルの配置キー */
    HEALTHINFO_FILE_REFERENCE("healthinfo-file-reference/"),
    /** お知らせ一覧JSONファイルの配置キー */
    NEWS_JSON("news/news.json"),
    /** 健康管理アプリパスワード再設定メールのテンプレートキー */
    ACCOUNT_RECOVERY_TEMPLATE("mail-template/account-recovery-template.txt"),
    /** 健康管理アプリパスワード再設定メールのテンプレートキー */
    HEALTHINFO_CHECK_TEMPLATE("mail-template/health-check-template.txt"),
    /** 健康管理アプリパスワード再設定メールのテンプレートキー */
    HEALTHINFO_REGIST_TEMPLATE("mail-template/healthinfo-regist-template.txt"),
    /** 月次健康情報集計CSV配置キー */
    MONTHLY_HEALTHINFO_SUMMARY("monthly/healthinfo/"),
    /** Slack接続情報キー */
    SLACK_CONNECTION_DATA("slack/slack_connection_data.json");

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private AwsS3Key(String value) {
        this.value = value;
    }

    /** 値 */
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

}
