package jp.co.ha.business.api.track.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * API通信ログ取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class ApiLogGetApiResponse extends BaseTrackApiResponse
        implements BaseApiResponse {

    /** ログリスト */
    @JsonProperty("log_list")
    private List<Log> logList = new ArrayList<>();
    /** 次回トークン情報 */
    @JsonProperty("next_token")
    private String nextToken;

    /**
     * ログリストを返す
     * 
     * @return logList
     */
    public List<Log> getLogList() {
        return logList;
    }

    /**
     * ログリストを設定する
     * 
     * @param logList
     *     ログリスト
     */
    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    /**
     * 次回トークン情報を返す
     * 
     * @return nextToken
     */
    public String getNextToken() {
        return nextToken;
    }

    /**
     * 次回トークン情報を設定する
     * 
     * @param nextToken
     *     次回トークン情報
     */
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    /**
     * ログデータ
     * 
     * @version 1.0.0
     */
    public static class Log {

        /** トランザクションID */
        @JsonProperty("transaction_id")
        private String transactionId;
        /** HTTPメソッド */
        @JsonProperty("http_method")
        private String httpMethod;
        /** URL */
        @JsonProperty("url")
        private String url;
        /** リクエストボディ */
        @JsonProperty("body")
        private String body;
        /** 要求日時 */
        @JsonProperty("request_date")
        private String requestDate;
        /** HTTPステータス */
        @JsonProperty("http_status")
        private String httpStatus;
        /** 応答日時 */
        @JsonProperty("response_date")
        private String responseDate;

        /**
         * トランザクションIDを返す
         * 
         * @return transactionId
         */
        public String getTransactionId() {
            return transactionId;
        }

        /**
         * トランザクションIDを設定する
         * 
         * @param transactionId
         *     トランザクションID
         */
        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        /**
         * HTTPメソッドを返す
         * 
         * @return httpMethod
         */
        public String getHttpMethod() {
            return httpMethod;
        }

        /**
         * HTTPメソッドを設定する
         * 
         * @param httpMethod
         *     HTTPメソッド
         */
        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        /**
         * URLを返す
         * 
         * @return url
         */
        public String getUrl() {
            return url;
        }

        /**
         * URLを設定する
         * 
         * @param url
         *     URL
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * リクエストボディを返す
         * 
         * @return body
         */
        public String getBody() {
            return body;
        }

        /**
         * リクエストボディを設定する
         * 
         * @param body
         *     リクエストボディ
         */
        public void setBody(String body) {
            this.body = body;
        }

        /**
         * 要求日時を返す
         * 
         * @return requestDate
         */
        public String getRequestDate() {
            return requestDate;
        }

        /**
         * 要求日時を設定する
         * 
         * @param requestDate
         *     要求日時
         */
        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        /**
         * HTTPステータスを返す
         * 
         * @return httpStatus
         */
        public String getHttpStatus() {
            return httpStatus;
        }

        /**
         * HTTPステータスを設定する
         * 
         * @param httpStatus
         *     HTTPステータス
         */
        public void setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
        }

        /**
         * 応答日時を返す
         * 
         * @return responseDate
         */
        public String getResponseDate() {
            return responseDate;
        }

        /**
         * 応答日時を設定する
         * 
         * @param responseDate
         *     応答日時
         */
        public void setResponseDate(String responseDate) {
            this.responseDate = responseDate;
        }

    }
}
