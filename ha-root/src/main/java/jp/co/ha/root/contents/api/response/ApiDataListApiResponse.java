package jp.co.ha.root.contents.api.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * API通信情報一覧取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class ApiDataListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** API通信情報リスト */
    @JsonProperty("api_data_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiData> apiDataList;

    /**
     * API通信情報リストを返す
     *
     * @return apiDataList
     */
    public List<ApiData> getApiDataList() {
        return apiDataList;
    }

    /**
     * API通信情報リストを設定する
     *
     * @param apiDataList
     *     API通信情報リスト
     */
    public void setApiDataList(List<ApiData> apiDataList) {
        this.apiDataList = apiDataList;
    }

    /**
     * API通信情報
     *
     * @version 1.0.0
     */
    public static class ApiData extends JsonEntity {

        /** API通信情報ID */
        @JsonProperty("seq_api_communication_data_id")
        private Long seqApiCommunicationDataId;
        /** トランザクションID */
        @JsonProperty("transaction_id")
        private String transactionId;
        /** API名 */
        @JsonProperty("api_name")
        private String apiName;
        /** HTTPメソッド */
        @JsonProperty("http_method")
        private String httpMethod;
        /** リクエストURL */
        @JsonProperty("url")
        private String url;
        /** リクエストBody */
        @JsonProperty("body")
        private String body;
        /** HTTPステータス */
        @JsonProperty("http_status")
        private String httpStatus;
        /** 詳細 */
        @JsonProperty("detail")
        private String detail;
        /** APIリクエスト日時 */
        @JsonProperty("request_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime requestDate;
        /** APIレスポンス日時 */
        @JsonProperty("response_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime responseDate;

        /**
         * API通信情報IDを返す
         *
         * @return seqApiCommunicationDataId
         */
        public Long getSeqApiCommunicationDataId() {
            return seqApiCommunicationDataId;
        }

        /**
         * API通信情報IDを設定する
         *
         * @param seqApiCommunicationDataId
         *     API通信情報ID
         */
        public void setSeqApiCommunicationDataId(Long seqApiCommunicationDataId) {
            this.seqApiCommunicationDataId = seqApiCommunicationDataId;
        }

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
         * API名を返す
         *
         * @return apiName
         */
        public String getApiName() {
            return apiName;
        }

        /**
         * API名を設定する
         *
         * @param apiName
         *     API名
         */
        public void setApiName(String apiName) {
            this.apiName = apiName;
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
         * リクエストURLを返す
         *
         * @return url
         */
        public String getUrl() {
            return url;
        }

        /**
         * リクエストURLを設定する
         *
         * @param url
         *     リクエストURL
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * リクエストBodyを返す
         *
         * @return body
         */
        public String getBody() {
            return body;
        }

        /**
         * リクエストBodyを設定する
         *
         * @param body
         *     リクエストBody
         */
        public void setBody(String body) {
            this.body = body;
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
         * 詳細を返す
         *
         * @return detail
         */
        public String getDetail() {
            return detail;
        }

        /**
         * 詳細を設定する
         *
         * @param detail
         *     詳細
         */
        public void setDetail(String detail) {
            this.detail = detail;
        }

        /**
         * APIリクエスト日時を返す
         *
         * @return requestDate
         */
        public LocalDateTime getRequestDate() {
            return requestDate;
        }

        /**
         * APIリクエスト日時を設定する
         *
         * @param requestDate
         *     APIリクエスト日時
         */
        public void setRequestDate(LocalDateTime requestDate) {
            this.requestDate = requestDate;
        }

        /**
         * APIリクエスト日時を返す
         *
         * @return responseDate
         */
        public LocalDateTime getResponseDate() {
            return responseDate;
        }

        /**
         * APIリクエスト日時を設定する
         *
         * @param responseDate
         *     APIリクエスト日時
         */
        public void setResponseDate(LocalDateTime responseDate) {
            this.responseDate = responseDate;
        }

    }

}
