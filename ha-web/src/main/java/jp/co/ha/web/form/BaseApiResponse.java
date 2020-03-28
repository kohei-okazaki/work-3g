package jp.co.ha.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.web.convert.ResultTypeSerializer;

/**
 * API基底レスポンス
 *
 * @version 1.0.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public abstract class BaseApiResponse implements BaseForm {

    /** API結果コード */
    @JsonSerialize(using = ResultTypeSerializer.class)
    @JsonProperty(value = "result")
    private ResultType resultType = ResultType.SUCCESS;

    /**
     * resultTypeを返す
     *
     * @return resultType
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * resultTypeを設定する
     *
     * @param resultType
     *     API結果コード
     */
    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    /**
     * APIの結果コードの列挙
     *
     * @version 1.0.0
     */
    public static enum ResultType implements BaseEnum {

        /** SUCCESS */
        SUCCESS("0", "成功"),
        /** FAILURE */
        FAILURE("1", "失敗");

        /** コード値 */
        private String value;
        /** メッセージ */
        private String message;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         * @param message
         *     メッセージ
         */
        private ResultType(String value, String message) {
            this.value = value;
            this.message = message;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * messageを返す
         *
         * @return message
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return ResultType
         */
        public static ResultType of(String value) {
            return BaseEnum.of(ResultType.class, value);
        }

    }

}
