package jp.co.ha.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ユーザ認証の必要なRestAPIの基底レスポンス
 * 
 * @version 1.0.0
 */
public abstract class BaseUserAuthApiResponse extends BaseApiResponse {

    /** アカウント情報 */
    @JsonProperty("account")
    private Account account;
    /** エラー情報 */
    @JsonProperty("error")
    private ErrorInfo errorInfo;

    /**
     * APIレスポンスの共通情報-account
     *
     * @version 1.0.0
     */
    public static class Account {

        /** ユーザID */
        @JsonProperty("userId")
        private String userId;

        /**
         * userIdを返す
         *
         * @return userId
         */
        public String getUserId() {
            return userId;
        }

        /**
         * userIdを設定する
         *
         * @param userId
         *     ユーザID
         */
        public void setUserId(String userId) {
            this.userId = userId;
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
         */
        public void setDetail(String detail) {
            this.detail = detail;
        }

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

}
