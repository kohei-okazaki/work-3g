package jp.co.ha.root.contents.account.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * アカウント情報編集APIリクエスト
 *
 * @version 1.0.0
 */
public class AccountEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** メールアドレス */
    @JsonProperty("mail_address")
    private String mailAddress;
    /** パスワード有効期限 */
    @JsonProperty("password_expire")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
    private LocalDate passwordExpire;

    /**
     * mailAddressを返す
     *
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * mailAddressを設定する
     *
     * @param mailAddress
     *     メールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * passwordExpireを返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

}
