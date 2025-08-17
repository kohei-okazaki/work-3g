package jp.co.ha.root.contents.account.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * ユーザ情報一覧APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class AccountListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** ユーザ情報リスト */
    @JsonProperty("account_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AccountResponse> accountResponseList;

    /**
     * ユーザ情報リストを返す
     *
     * @return accountResponseList
     */
    public List<AccountResponse> getAccountResponseList() {
        return accountResponseList;
    }

    /**
     * ユーザ情報リストを設定する
     *
     * @param accountResponseList
     *     ユーザ情報リスト
     */
    public void setAccountResponseList(List<AccountResponse> accountResponseList) {
        this.accountResponseList = accountResponseList;
    }

    /**
     * ユーザ情報
     *
     * @version 1.0.0
     */
    public static class AccountResponse extends JsonEntity {

        /** ユーザID */
        @JsonProperty("seq_user_id")
        private Long seqUserId;
        /** メールアドレス */
        @JsonProperty("mail_address")
        private String mailAddress;
        /** 削除フラグ */
        @JsonProperty("delete_flag")
        private String deleteFlag;
        /** パスワード有効期限 */
        @JsonProperty("password_expire")
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
        private LocalDate passwordExpire;
        /** 備考 */
        @JsonProperty("remarks")
        private String remarks;
        /** APIキー */
        @JsonProperty("api_key")
        private String apiKey;
        /** 登録日時 */
        @JsonProperty("reg_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime regDate;
        /** 更新日時 */
        @JsonProperty("update_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime updateDate;
        /** ヘッダ利用有無フラグ */
        @JsonProperty("header_flag")
        private String headerFlag;
        /** フッタ利用有無フラグ */
        @JsonProperty("footer_flag")
        private String footerFlag;
        /** マスク利用有無フラグ */
        @JsonProperty("mask_flag")
        private String maskFlag;
        /** 囲み文字利用有無フラグ */
        @JsonProperty("enclosure_char_flag")
        private String enclosureCharFlag;

        /**
         * ユーザIDを返す
         *
         * @return seqUserId
         */
        public Long getSeqUserId() {
            return seqUserId;
        }

        /**
         * ユーザIDを設定する
         *
         * @param seqUserId
         *     ユーザID
         */
        public void setSeqUserId(Long seqUserId) {
            this.seqUserId = seqUserId;
        }

        /**
         * メールアドレスを返す
         *
         * @return mailAddress
         */
        public String getMailAddress() {
            return mailAddress;
        }

        /**
         * メールアドレスを設定する
         *
         * @param mailAddress
         *     メールアドレス
         */
        public void setMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
        }

        /**
         * 削除フラグを返す
         *
         * @return deleteFlag
         */
        public String getDeleteFlag() {
            return deleteFlag;
        }

        /**
         * 削除フラグを設定する
         *
         * @param deleteFlag
         *     削除フラグ
         */
        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        /**
         * パスワード有効期限を返す
         *
         * @return passwordExpire
         */
        public LocalDate getPasswordExpire() {
            return passwordExpire;
        }

        /**
         * パスワード有効期限を設定する
         *
         * @param passwordExpire
         *     パスワード有効期限
         */
        public void setPasswordExpire(LocalDate passwordExpire) {
            this.passwordExpire = passwordExpire;
        }

        /**
         * 備考を返す
         *
         * @return remarks
         */
        public String getRemarks() {
            return remarks;
        }

        /**
         * 備考を設定する
         *
         * @param remarks
         *     備考
         */
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        /**
         * APIキーを返す
         *
         * @return apiKey
         */
        public String getApiKey() {
            return apiKey;
        }

        /**
         * APIキーを設定する
         *
         * @param apiKey
         *     APIキー
         */
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        /**
         * 登録日時を返す
         *
         * @return regDate
         */
        public LocalDateTime getRegDate() {
            return regDate;
        }

        /**
         * 登録日時を設定する
         *
         * @param regDate
         *     登録日時
         */
        public void setRegDate(LocalDateTime regDate) {
            this.regDate = regDate;
        }

        /**
         * 更新日時を返す
         *
         * @return updateDate
         */
        public LocalDateTime getUpdateDate() {
            return updateDate;
        }

        /**
         * 更新日時を設定する
         *
         * @param updateDate
         *     更新日時
         */
        public void setUpdateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
        }

        /**
         * ヘッダ利用有無フラグを返す
         *
         * @return headerFlag
         */
        public String getHeaderFlag() {
            return headerFlag;
        }

        /**
         * ヘッダ利用有無フラグを設定する
         *
         * @param headerFlag
         *     ヘッダ利用有無フラグ
         */
        public void setHeaderFlag(String headerFlag) {
            this.headerFlag = headerFlag;
        }

        /**
         * フッタ利用有無フラグを返す
         *
         * @return footerFlag
         */
        public String getFooterFlag() {
            return footerFlag;
        }

        /**
         * フッタ利用有無フラグを設定する
         *
         * @param footerFlag
         *     フッタ利用有無フラグ
         */
        public void setFooterFlag(String footerFlag) {
            this.footerFlag = footerFlag;
        }

        /**
         * マスク利用有無フラグを返す
         *
         * @return maskFlag
         */
        public String getMaskFlag() {
            return maskFlag;
        }

        /**
         * マスク利用有無フラグを設定する
         *
         * @param maskFlag
         *     マスク利用有無フラグ
         */
        public void setMaskFlag(String maskFlag) {
            this.maskFlag = maskFlag;
        }

        /**
         * 囲み文字利用有無フラグを返す
         *
         * @return enclosureCharFlag
         */
        public String getEnclosureCharFlag() {
            return enclosureCharFlag;
        }

        /**
         * 囲み文字利用有無フラグを設定する
         *
         * @param enclosureCharFlag
         *     囲み文字利用有無フラグ
         */
        public void setEnclosureCharFlag(String enclosureCharFlag) {
            this.enclosureCharFlag = enclosureCharFlag;
        }

    }

}
