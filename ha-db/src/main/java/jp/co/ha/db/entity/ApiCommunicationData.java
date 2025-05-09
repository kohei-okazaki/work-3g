package jp.co.ha.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * API通信情報Entity
 *
 * @version 1.0.0
 */
@Entity
public class ApiCommunicationData extends ApiCommunicationDataKey
        implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.TRANSACTION_ID
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private Long transactionId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.API_NAME
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String apiName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.HTTP_METHOD
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String httpMethod;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.URL
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String url;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.BODY
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String body;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.HTTP_STATUS
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String httpStatus;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.DETAIL
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private String detail;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.REQUEST_DATE
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private LocalDateTime requestDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.RESPONSE_DATE
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private LocalDateTime responseDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.UPDATE_DATE
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private LocalDateTime updateDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column api_communication_data.REG_DATE
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    private LocalDateTime regDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table api_communication_data
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.TRANSACTION_ID
     * 
     * @return the value of api_communication_data.TRANSACTION_ID
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.TRANSACTION_ID
     * 
     * @param transactionId
     *     the value for api_communication_data.TRANSACTION_ID
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.API_NAME
     * 
     * @return the value of api_communication_data.API_NAME
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.API_NAME
     * 
     * @param apiName
     *     the value for api_communication_data.API_NAME
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.HTTP_METHOD
     * 
     * @return the value of api_communication_data.HTTP_METHOD
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.HTTP_METHOD
     * 
     * @param httpMethod
     *     the value for api_communication_data.HTTP_METHOD
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.URL
     * 
     * @return the value of api_communication_data.URL
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.URL
     * 
     * @param url
     *     the value for api_communication_data.URL
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.BODY
     * 
     * @return the value of api_communication_data.BODY
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getBody() {
        return body;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.BODY
     * 
     * @param body
     *     the value for api_communication_data.BODY
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.HTTP_STATUS
     * 
     * @return the value of api_communication_data.HTTP_STATUS
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getHttpStatus() {
        return httpStatus;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.HTTP_STATUS
     * 
     * @param httpStatus
     *     the value for api_communication_data.HTTP_STATUS
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.DETAIL
     * 
     * @return the value of api_communication_data.DETAIL
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.DETAIL
     * 
     * @param detail
     *     the value for api_communication_data.DETAIL
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.REQUEST_DATE
     * 
     * @return the value of api_communication_data.REQUEST_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.REQUEST_DATE
     * 
     * @param requestDate
     *     the value for api_communication_data.REQUEST_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.RESPONSE_DATE
     * 
     * @return the value of api_communication_data.RESPONSE_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.RESPONSE_DATE
     * 
     * @param responseDate
     *     the value for api_communication_data.RESPONSE_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.UPDATE_DATE
     * 
     * @return the value of api_communication_data.UPDATE_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.UPDATE_DATE
     * 
     * @param updateDate
     *     the value for api_communication_data.UPDATE_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column api_communication_data.REG_DATE
     * 
     * @return the value of api_communication_data.REG_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column api_communication_data.REG_DATE
     * 
     * @param regDate
     *     the value for api_communication_data.REG_DATE
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transactionId=").append(transactionId);
        sb.append(", apiName=").append(apiName);
        sb.append(", httpMethod=").append(httpMethod);
        sb.append(", url=").append(url);
        sb.append(", body=").append(body);
        sb.append(", httpStatus=").append(httpStatus);
        sb.append(", detail=").append(detail);
        sb.append(", requestDate=").append(requestDate);
        sb.append(", responseDate=").append(responseDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", regDate=").append(regDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApiCommunicationData other = (ApiCommunicationData) that;
        return (this.getSeqApiCommunicationDataId() == null
                ? other.getSeqApiCommunicationDataId() == null
                : this.getSeqApiCommunicationDataId()
                        .equals(other.getSeqApiCommunicationDataId()))
                && (this.getTransactionId() == null ? other.getTransactionId() == null
                        : this.getTransactionId().equals(other.getTransactionId()))
                && (this.getApiName() == null ? other.getApiName() == null
                        : this.getApiName().equals(other.getApiName()))
                && (this.getHttpMethod() == null ? other.getHttpMethod() == null
                        : this.getHttpMethod().equals(other.getHttpMethod()))
                && (this.getUrl() == null ? other.getUrl() == null
                        : this.getUrl().equals(other.getUrl()))
                && (this.getBody() == null ? other.getBody() == null
                        : this.getBody().equals(other.getBody()))
                && (this.getHttpStatus() == null ? other.getHttpStatus() == null
                        : this.getHttpStatus().equals(other.getHttpStatus()))
                && (this.getDetail() == null ? other.getDetail() == null
                        : this.getDetail().equals(other.getDetail()))
                && (this.getRequestDate() == null ? other.getRequestDate() == null
                        : this.getRequestDate().equals(other.getRequestDate()))
                && (this.getResponseDate() == null ? other.getResponseDate() == null
                        : this.getResponseDate().equals(other.getResponseDate()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null
                        : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getRegDate() == null ? other.getRegDate() == null
                        : this.getRegDate().equals(other.getRegDate()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     * 
     * @mbg.generated Sat Dec 21 11:02:03 GMT+09:00 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqApiCommunicationDataId() == null) ? 0
                : getSeqApiCommunicationDataId().hashCode());
        result = prime * result
                + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getApiName() == null) ? 0 : getApiName().hashCode());
        result = prime * result
                + ((getHttpMethod() == null) ? 0 : getHttpMethod().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        result = prime * result
                + ((getHttpStatus() == null) ? 0 : getHttpStatus().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        result = prime * result
                + ((getRequestDate() == null) ? 0 : getRequestDate().hashCode());
        result = prime * result
                + ((getResponseDate() == null) ? 0 : getResponseDate().hashCode());
        result = prime * result
                + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRegDate() == null) ? 0 : getRegDate().hashCode());
        return result;
    }
}