package jp.co.ha.business.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.util.HttpStatusDeserializer;
import jp.co.ha.common.util.HttpStatusSerializer;

/**
 * API通信ログのSQS-Payload
 * 
 * @version 1.0.0
 */
public class ApiLogQueuePayload {

    /* リクエスト項目 */
    /** トランザクションID */
    @JsonProperty("transaction_id")
    private String transactionId;
    /** API名 */
    @JsonProperty("api_name")
    private String apiName;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
    private LocalDateTime requestDate;

    /* レスポンス項目 */
    /** HTTPステータス */
    @JsonProperty("http_status")
    @JsonSerialize(using = HttpStatusSerializer.class)
    @JsonDeserialize(using = HttpStatusDeserializer.class)
    private HttpStatus httpStatus;
    /** 詳細 */
    @JsonProperty("detail")
    private String detail;
    /** 応答日時 */
    @JsonProperty("response_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
    private LocalDateTime responseDate;

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
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * 要求日時を設定する
     * 
     * @param requestDate
     *     要求日時
     */
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * HTTPステータスを返す
     * 
     * @return httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * HTTPステータスを設定する
     * 
     * @param httpStatus
     *     HTTPステータス
     */
    public void setHttpStatus(HttpStatus httpStatus) {
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
     * 応答日時を返す
     * 
     * @return responseDate
     */
    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    /**
     * 応答日時を設定する
     * 
     * @param responseDate
     *     応答日時
     */
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }

}
