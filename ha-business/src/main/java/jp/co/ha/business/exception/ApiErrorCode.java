package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * APIのエラーコード列挙<br>
 * apiErrorCode.propertiesと一致させること
 *
 * @version 1.0.0
 */
public enum ApiErrorCode implements BaseErrorCode, BaseEnum {

    /* ERROR */
    /** API実行時エラー */
    API_EXEC_ERROR("API_EXEC_ERROR", "AE0018", LogLevel.WARN),

    /* WARN */
    /** API必須エラー */
    API_REQUIRE("API_REQUIRE", "AW0019", LogLevel.WARN),
    /** リクエスト種別エラー */
    REQUEST_TYPE_INVALID("REQUEST_TYPE_INVALID", "AW0020", LogLevel.WARN),

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
    private ApiErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
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
     * @return ApiErrorCode
     */
    public static ApiErrorCode of(String value) {
        return BaseEnum.of(ApiErrorCode.class, value);
    }
}
