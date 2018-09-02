package jp.co.ha.business.db.entity;

import java.io.Serializable;
import java.util.Date;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * メール情報Entity<br>
 *
 */
public class MailInfo implements Serializable {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	private String userId;
	/** メールアドレス */
	@Mask
	private String mailAddress;
	/** メールパスワード */
	@Mask
	private String mailPassword;
	/** 更新日時 */
	private Date updateDate;
	/** 登録日時 */
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
	 *     ユーザID
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
	 *     メールアドレス
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
	 *     メールパスワード
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
	 *     更新日時
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * regDateを返す
	 *
	 * @return regDate 登録日時
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 *
	 * @param regDate
	 *     登録日時
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}