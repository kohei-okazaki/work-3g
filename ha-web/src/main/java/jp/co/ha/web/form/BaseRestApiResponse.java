package jp.co.ha.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RestAPIの基底レスポンス
 *
 * @version 1.0.0
 */
public class BaseRestApiResponse extends BaseApiResponse {

    /** アカウント情報 */
    @JsonProperty("account")
    private Account account;

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
