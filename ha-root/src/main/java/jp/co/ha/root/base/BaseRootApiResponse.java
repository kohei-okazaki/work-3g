package jp.co.ha.root.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.type.RootApiResult;
import jp.co.ha.root.type.RootApiResult.RootApiResultSerializer;

/**
 * RootAPI基底レスポンスクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRootApiResponse extends JsonEntity {

    /** 処理結果 */
    @JsonProperty("result")
    @JsonSerialize(using = RootApiResultSerializer.class)
    private RootApiResult rootApiResult;
    /** エラー情報 */
    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorData errorData;

    /**
     * エラー情報
     *
     * @version 1.0.0
     */
    public static class ErrorData extends JsonEntity {

        /** メッセージ */
        @JsonProperty("message")
        private String message;

        /**
         * messageを返す
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * messageを設定する
         *
         * @param message
         *     メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * rootApiResultを返す
     *
     * @return rootApiResult
     */
    public RootApiResult getRootApiResult() {
        return rootApiResult;
    }

    /**
     * rootApiResultを設定する
     *
     * @param rootApiResult
     *     処理結果
     */
    public void setRootApiResult(RootApiResult rootApiResult) {
        this.rootApiResult = rootApiResult;
    }

    /**
     * errorDataを返す
     *
     * @return errorData
     */
    public ErrorData getErrorData() {
        return errorData;
    }

    /**
     * errorDataを設定する
     *
     * @param errorData
     *     エラー情報
     */
    public void setErrorData(ErrorData errorData) {
        this.errorData = errorData;
    }

}
