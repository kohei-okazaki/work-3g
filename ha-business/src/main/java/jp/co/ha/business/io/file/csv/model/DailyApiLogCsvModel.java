package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

/**
 * 日次API通信ログデータ分析連携CSV Model
 *
 * @version 1.0.0
 */
public class DailyApiLogCsvModel implements BaseCsvModel {

    /** API通信ログID */
    private Long seqApiLogId;
    /** トランザクションID */
    private String transactionId;
    /** HTTPメソッド */
    private String httpMethod;
    /** URL */
    private String url;
    /** リクエストボディ */
    private String body;
    /** 要求日時 */
    private String requestDate;
    /** HTTPステータス */
    private String httpStatus;
    /** 応答日時 */
    private String responseDate;

    /**
     * API通信ログIDを返す
     * 
     * @return seqApiLogId
     */
    public Long getSeqApiLogId() {
        return seqApiLogId;
    }

    /**
     * API通信ログIDを設定する
     * 
     * @param seqApiLogId
     *     API通信ログID
     */
    public void setSeqApiLogId(Long seqApiLogId) {
        this.seqApiLogId = seqApiLogId;
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
