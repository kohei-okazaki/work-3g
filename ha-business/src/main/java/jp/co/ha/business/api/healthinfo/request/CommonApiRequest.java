package jp.co.ha.business.api.healthinfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * API共通リクエスト
 *
 * @version 1.0.0
 */
public abstract class CommonApiRequest extends BaseApiRequest {

    /** アカウント */
    @Required
    @JsonProperty("account")
    private Account account;

    /**
     * APIリクエストの共通情報-account
     *
     * @version 1.0.0
     */
    public static class Account {

        /** ユーザID */
        @Required(message = "userIdが未設定です")
        @Pattern(regixPattern = RegexType.HALF_CHAR, message = "userIdが半角英数でありません")
        @Min(size = 2, message = "userIdが2byte未満です")
        @Max(size = 16, message = "userIdが16byte以上です")
        @JsonProperty("userId")
        private String userId;

        /** APIキー */
        @Mask
        @Required(message = "apiKeyが未設定です")
        @Length(length = 64, message = "apiKeyが64byteではありません")
        @JsonProperty("apiKey")
        private String apiKey;

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

        /**
         * apiKeyを返す
         *
         * @return apiKey
         */
        public String getApiKey() {
            return apiKey;
        }

        /**
         * apiKeyを設定する
         *
         * @param apiKey
         *     APIキー
         */
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
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
     *     アカウント
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
