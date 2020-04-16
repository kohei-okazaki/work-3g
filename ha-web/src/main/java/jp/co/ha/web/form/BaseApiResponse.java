package jp.co.ha.web.form;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jp.co.ha.common.type.BaseEnum;

/**
 * API基底レスポンス
 *
 * @version 1.0.0
 */
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

    /**
     * JSONのAPIの結果コードのシリアライズクラス
     *
     * @version 1.0.0
     */
    public static class ResultTypeSerializer extends JsonSerializer<ResultType> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void serialize(ResultType resultType, JsonGenerator gen,
                SerializerProvider serializers) throws IOException {
            gen.writeString(resultType.getValue());
        }

    }

}
