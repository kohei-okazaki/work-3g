package jp.co.ha.common.exception;

import static jp.co.ha.common.log.Logger.LogLevel.*;

import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * 共通エラーコード<br>
 * commonErrorCode.propertiesと一致させること
 *
 * @version 1.0.0
 */
public enum CommonErrorCode implements BaseErrorCode, BaseEnum {

    /* ERROR */
    /** 実行環境エラー */
    RUNTIME_ERROR("RUNTIME_ERROR", "CE0001", ERROR),
    /** 予期せぬ例外 */
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "CE0002", ERROR),
    /** アルゴリズムエラー */
    ALGORITH_ERROR("ALGORITH_ERROR", "CE0003", ERROR),
    /** DB暗号化・復号エラー */
    DB_ENCRYPT_ERROR("DB_CRYPT_ERROR", "CE0004", ERROR),
    /** DBアクセスエラー */
    DB_ACCESS_ERROR("DB_ACCESS_ERROR", "CE0005", ERROR),
    /** SQLの実行に失敗エラー */
    DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "CE0006", ERROR),
    /** SQL指定なしエラー */
    DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "CE0007", ERROR),
    /** DB切断エラー */
    DB_CLOSE_ERROR("DB_CLOSE_ERROR", "CE0008", ERROR),
    /** AWS-S3 ダウンロードエラー */
    AWS_S3_DOWNLOAD_ERROR("AWS_S3_DOWNLOAD_ERROR", "CE0027", ERROR),
    /** AWS-S3 アップロードエラー */
    AWS_S3_UPLOAD_ERROR("AWS_S3_UPLOAD_ERROR", "CE0028", ERROR),
    /** AWS接続エラー */
    AWS_CLIENT_CONNECT_ERROR("AWS_CLIENT_CONNECT_ERROR", "CE0029", ERROR),
    /** SESメールアドレス認証エラー */
    AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR("AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR", "CE0034",
            ERROR),
    /** API通信エラー(400系) */
    API_400_CONNECT_ERROR("API_400_CONNECT_ERROR", "CE0035", ERROR),
    /** API通信エラー(500系) */
    API_500_CONNECT_ERROR("API_500_CONNECT_ERROR", "CE0036", ERROR),
    /** AWS-SQS キュー登録エラー */
    AWS_SQS_ENQUEUE_ERROR("AWS_SQS_ENQUEUE_ERROR", "CE0041", ERROR),
    /** AWS-SQS キュー取得エラー */
    AWS_SQS_POLL_ERROR("AWS_SQS_POLL_ERROR", "CE0042", ERROR),
    /** AWS-SQS キュー削除エラー */
    AWS_SQS_ACK_ERROR("AWS_SQS_ACK_ERROR", "CE0043", ERROR),
    /** AWS-S3 ファイル削除エラー */
    AWS_S3_DELETE_ERROR("AWS_S3_DELETE_ERROR", "CE0044", ERROR),
    /** メールテンプレート未指定エラー */
    MAIL_TEMPLATE_REQUIED_ERROR("MAIL_TEMPLATE_REQUIED_ERROR", "CE0046", ERROR),
    /** AWS-SSM 値取得エラー */
    AWS_SSM_GET_ERROR("AWS_SSM_GET_ERROR", "CE0047", ERROR),

    /* WARN */
    /** 該当データ存在しないエラー */
    DB_NO_DATA("DB_NO_DATA", "CW0009", WARN),
    /** JSON変換エラー例外 */
    JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "CW0010", WARN),
    /** JSON変換エラー例外 */
    JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "CW0011", WARN),
    /** JSON変換エラー例外 */
    JSON_PARSE_ERROR("JSON_PARSE_ERROR", "CW0012", WARN),
    /** ファイルアップロード例外 */
    FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "CW0013", WARN),
    /** ファイル読込例外 */
    FILE_READING_ERROR("FILE_READING_ERROR", "CW0014", WARN),
    /** ファイル書込処理エラー */
    FILE_WRITE_ERROR("FILE_WRITE_ERROR", "CW0015", WARN),
    /** データ重複エラー */
    MULTIPLE_DATA("MULTIPLE_DATA", "CW0016", WARN),
    /** 妥当性チェックエラー */
    VALIDATE_ERROR("VALIDATE_ERROR", "CW0017", WARN),
    /** ファイルまたはディレクトリ操作エラー */
    FILE_OR_DIR_ERROR("FILE_OR_DIR_ERROR", "CW0040", WARN);

    /**
     * コンストラクタ
     *
     * @param value
     *     value
     * @param outerErrorCode
     *     ErrorCode(外部用)
     * @param logLevel
     *     ログレベル
     */
    private CommonErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
        this.value = value;
        this.outerErrorCode = outerErrorCode;
        this.logLevel = logLevel;
    }

    /** value */
    private String value;
    /** ErrorCode(外部用) */
    private String outerErrorCode;
    /** ログレベル */
    private LogLevel logLevel;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getOuterErrorCode() {
        return this.outerErrorCode;
    }

    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return CommonErrorCode
     */
    public static CommonErrorCode of(String value) {
        return BaseEnum.of(CommonErrorCode.class, value);
    }

}
