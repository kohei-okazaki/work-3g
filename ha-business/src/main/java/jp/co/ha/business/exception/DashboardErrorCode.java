package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * ダッシュボードのエラーコード列挙
 *
 * @version 1.0.0
 */
public enum DashboardErrorCode implements BaseErrorCode, BaseEnum {

    /* ERROR */
    /** S3 ダウンロードエラー */
    AWS_S3_DOWNLOAD_ERROR("AWS_S3_DOWNLOAD_ERROR", "DE0027", LogLevel.ERROR),
    /** S3 アップロードエラー */
    AWS_S3_UPLOAD_ERROR("AWS_S3_UPLOAD_ERROR", "DE0028", LogLevel.ERROR),

    /* WARN */
    /** アカウント存在チェックエラー */
    ACCOUNT_ILLEGAL("ACCOUNT_ILLEGAL", "DW0021", LogLevel.WARN),
    /** アカウント削除済エラー */
    ACCOUNT_DELETE("ACCOUNT_DELETE", "DW0022", LogLevel.WARN),
    /** アカウント存在エラー */
    ACCOUNT_EXIST("ACCOUNT_EXIST", "DW0023", LogLevel.WARN),
    /** アカウント不一致エラー */
    ACCOUNT_INVALID_PASSWORD("ACCOUNT_INVALID_PASSWORD", "DW0024", LogLevel.WARN),
    /** アカウント有効期限エラー */
    ACCOUNT_EXPIRED("ACCOUNT_EXPIRED", "DW0025", LogLevel.WARN),
    /** 不正リクエストエラー */
    ILLEGAL_ACCESS_ERROR("ILLEGAL_ACCESS_ERROR", "DW0026", LogLevel.WARN),

    ;

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
    private DashboardErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOuterErrorCode() {
        return this.outerErrorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return DashboardErrorCode
     */
    public static DashboardErrorCode of(String value) {
        return BaseEnum.of(DashboardErrorCode.class, value);
    }
}
