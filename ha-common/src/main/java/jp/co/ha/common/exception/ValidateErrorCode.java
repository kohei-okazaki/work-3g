package jp.co.ha.common.exception;

import jp.co.ha.common.log.Logger.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * 妥当性チェック時に発生するエラーコードの列挙
 *
 * @version 1.0.0
 */
public enum ValidateErrorCode implements BaseErrorCode, BaseEnum {

    /** 必須 */
    REQUIRE("REQUIRE", "validate.message.NotEmpty"),
    /** 桁数超過 */
    LENGTH_OVER("LENGTH_OVER", "validate.message.LengthOver"),
    /** 桁数不足 */
    LENGTH_LACK("LENGTH_LACK", "validate.message.LengthLack"),
    /** 属性不一致 */
    TYPE_ERROR("TYPE_ERROR", "validate.message.TypeError"),
    /** 日付超過 */
    DATE_OVER("DATE_OVER", "validate.message.DateOver");

    /**
     * コンストラクタ
     *
     * @param value
     *     value
     * @param outerErrorCode
     *     ErrorCode(外部用)
     */
    private ValidateErrorCode(String value, String outerErrorCode) {
        this.value = value;
        this.outerErrorCode = outerErrorCode;
    }

    /** value */
    private String value;
    /** ErrorCode(外部用) */
    private String outerErrorCode;

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
        return null;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return ValidateErrorCode
     */
    public static ValidateErrorCode of(String value) {
        return BaseEnum.of(ValidateErrorCode.class, value);
    }
}
