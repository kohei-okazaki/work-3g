package jp.co.ha.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * メール情報Entity<br>
 *
 */
@Entity
@Table(name = "MAIL_INFO")
public class MailInfo implements Serializable {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Id
	@Column(name = "USER_ID", nullable = false, length = 3)
	private String userId;

	/** メールアドレス */
	@Column(name = "MAIL_ADDRESS", nullable = false)
	private String mailAddress;

	/** メールパスワード */
	@Column(name = "MAIL_PASSWORD", nullable = false)
	private String mailPassword;

	/** 更新日時 */
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;

	/** 更新日時 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/**
	 * userIdを返す
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *            ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * mailAddressを返す
	 *
	 * @return mailAddress メールアドレス
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * mailAddressを設定する
	 *
	 * @param mailAddress
	 *            メールアドレス
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * mailPasswordを返す
	 *
	 * @return mailPassword メールパスワード
	 */
	public String getMailPassword() {
		return mailPassword;
	}

	/**
	 * mailPasswordを設定する
	 *
	 * @param mailPassword
	 *            メールパスワード
	 */
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	/**
	 * updateDateを返す
	 *
	 * @return updateDate 更新日時
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * updateDateを設定する
	 *
	 * @param updateDate
	 *            更新日時
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * regDateを返す
	 *
	 * @return regDate 更新日時
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 *
	 * @param regDate
	 *            更新日時
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}