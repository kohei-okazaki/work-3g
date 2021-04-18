package jp.co.ha.common.web.form;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jp.co.ha.common.type.BaseEnum;

/**
 * RestAPIの基底レスポンスクラス
 *
 * @version 1.0.0
 */
public class BaseRestApiResponse implements BaseForm {

    /** API結果コード */
    @JsonSerialize(using = ResultTypeSerializer.class)
    @JsonProperty(value = "result")
    private ResultType resultType = ResultType.SUCCESS;
    /** エラー情報 */
    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorInfo errorInfo;
    /** アカウント情報 */
    @JsonProperty("account")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account account;

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

        @Override
        public void serialize(ResultType resultType, JsonGenerator gen,
                SerializerProvider serializers) throws IOException {
            gen.writeString(resultType.getValue());
        }
    }

    /**
     * APIレスポンスの共通情報-error
     *
     * @version 1.0.0
     */
    public static class ErrorInfo {

        /** 外部エラーコード */
        @JsonProperty(value = "code")
        private String outerErrorCode;
        /** 詳細 */
        @JsonProperty(value = "detail")
        private String detail;

        /**
         * outerErrorCodeを返す
         *
         * @return outerErrorCode
         */
        public String getOuterErrorCode() {
            return outerErrorCode;
        }

        /**
         * outerErrorCodeを設定する
         *
         * @param outerErrorCode
         *     外部エラーコード
         */
        public void setOuterErrorCode(String outerErrorCode) {
            this.outerErrorCode = outerErrorCode;
        }

        /**
         * detailを返す
         *
         * @return detail
         */
        public String getDetail() {
            return detail;
        }

        /**
         * detailを設定する
         *
         * @param detail
         *     詳細
         */
        public void setDetail(String detail) {
            this.detail = detail;
        }

    }

    /**
     * APIレスポンスの共通情報-account
     *
     * @version 1.0.0
     */
    public static class Account {

        /** ユーザID */
        @JsonProperty("seqUserId")
        private Long seqUserId;

        /**
         * seqUserIdを返す
         *
         * @return seqUserId
         */
        public Long getSeqUserId() {
            return seqUserId;
        }

        /**
         * seqUserIdを設定する
         *
         * @param seqUserId
         *     ユーザID
         */
        public void setSeqUserId(Long seqUserId) {
            this.seqUserId = seqUserId;
        }

    }

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
     * errorInfoを返す
     *
     * @return errorInfo
     */
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    /**
     * errorInfoを設定する
     *
     * @param errorInfo
     *     エラー情報
     */
    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * accountを返す
     *
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * accountを設定する
     *
     * @param account
     *     アカウント情報
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
