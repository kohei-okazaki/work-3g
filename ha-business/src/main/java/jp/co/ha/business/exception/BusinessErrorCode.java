package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * ビジネスのエラーコード列挙
 *
 * @version 1.0.0
 */
public enum BusinessErrorCode implements BaseErrorCode, BaseEnum {

    /* ERROR */
    /** S3 ダウンロードエラー */
    AWS_S3_DOWNLOAD_ERROR("AWS_S3_DOWNLOAD_ERROR", "BE0027", LogLevel.ERROR),
    /** S3 アップロードエラー */
    AWS_S3_UPLOAD_ERROR("AWS_S3_UPLOAD_ERROR", "BE0028", LogLevel.ERROR),
    /** AWS接続エラー */
    AWS_CLIENT_CONNECT_ERROR("AWS_CLIENT_CONNECT_ERROR", "BE0029", LogLevel.ERROR),
    /** SDK接続エラー */
    SDK_CLIENT_CONNECT_ERROR("SDK_CLIENT_CONNECT_ERROR", "BE0030", LogLevel.ERROR),
    /** 基礎健康情報計算API接続エラー */
    BASIC_API_CONNECT_ERROR("BASIC_API_CONNECT_ERROR", "BE0032", LogLevel.ERROR),
    /** カロリー計算API接続エラー */
    CALORIE_API_CONNECT_ERROR("CALORIE_API_CONNECT_ERROR", "BE0033", LogLevel.ERROR),
    /** SESメールアドレス認証エラー */
    AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR("AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR", "BE0034",
            LogLevel.ERROR),
    /** 肺活量計算API接続エラー */
    BREATHING_API_CONNECT_ERROR("BREATHING_API_CONNECT_ERROR", "BE0037", LogLevel.ERROR),
    /** Token発行API接続エラー */
    TOKEN_API_CONNECT_ERROR("TOKEN_API_CONNECT_ERROR", "BE0038", LogLevel.ERROR);

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
    private BusinessErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
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
     * @return BusinessErrorCode
     */
    public static BusinessErrorCode of(String value) {
        return BaseEnum.of(ApiErrorCode.class, value);
    }
}
