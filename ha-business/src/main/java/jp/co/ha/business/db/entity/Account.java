package jp.co.ha.business.db.entity;

import java.io.Serializable;
import java.util.Date;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * アカウント情報Entity<br>
 *
 */
public class Account implements Serializable {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	private String userId;
	/** パスワード */
	@Mask
	private String password;
	/** 削除フラグ */
	private String deleteFlag;
	/** パスワード有効期限 */
	private Date passwordExpire;
	/** 備考 */
	private String remarks;
	/** APIキー */
	@Mask
	private String apiKey;
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
	 * passwordを返す
	 *
	 * @return password パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * passwordを設定する
	 *
	 * @param password
	 *     パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * deleteFlagを返す
	 *
	 * @return deleteFlag 削除フラグ
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * deleteFlagを設定する
	 *
	 * @param deleteFlag
	 *     削除フラグ
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * passwordExpireを返す
	 *
	 * @return passwordExpire パスワード有効期限
	 */
	public Date getPasswordExpire() {
		return passwordExpire;
	}

	/**
	 * passwordExpireを設定する
	 *
	 * @param passwordExpire
	 *     パスワード有効期限
	 */
	public void setPasswordExpire(Date passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

	/**
	 * remarksを返す
	 *
	 * @return remarks 備考
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * remarksを設定する
	 *
	 * @param remarks
	 *     備考
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * apiKeyを返す<br>
	 *
	 * @return apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * apiKeyを設定する<br>
	 *
	 * @param apiKey
	 *     APIキー
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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
