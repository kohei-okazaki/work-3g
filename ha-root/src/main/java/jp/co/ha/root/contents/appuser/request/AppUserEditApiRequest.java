package jp.co.ha.root.contents.appuser.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jp.co.ha.common.validator.annotation.Future;
import jp.co.ha.common.validator.annotation.MailAddress;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * ユーザ情報編集APIリクエスト
 *
 * @version 1.0.0
 */
public class AppUserEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** メールアドレス */
    @JsonProperty("mail_address")
    @Required(message = "mail_address is required")
    @MailAddress(message = "invalid mail_address format")
    private String mailAddress;
    /** パスワード有効期限 */
    @JsonProperty("password_expire")
    @Required(message = "password_expire is required")
    @Future(message = "password_expire is must be future")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
    private LocalDate passwordExpire;

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

}
