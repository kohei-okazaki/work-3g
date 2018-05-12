package jp.co.ha.web.form;

import jp.co.ha.common.web.BaseForm;

/**
 * アカウント設定画面フォームクラス<br>
 *
 */
public class AccountSettingForm implements BaseForm {

	/** ユーザID */
	private String userId;
	/** パスワード */
	private String password;
	/** 削除フラグ */
	private String deleteFlag;
	/** ファイル囲い文字利用フラグ */
	private String fileEnclosureCharFlag;
	/** 健康情報マスク利用フラグ */
	private String healthInfoMaskFlag;
	/** 備考 */
	private String remarks;
	/** メールアドレス */
	private String mailAddress;
	/** メールパスワード */
	private String mailPassword;

	/**
	 * userIdを返す
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * userIdを設定する
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * passwordを返す
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * passwordを設定する
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * deleteFlagを返す
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * deleteFlagを設定する
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * fileEnclosureCharFlagを返す
	 * @return fileEnclosureCharFlag
	 */
	public String getFileEnclosureCharFlag() {
		return fileEnclosureCharFlag;
	}
	/**
	 * fileEnclosureCharFlagを設定する
	 * @param fileEnclosureCharFlag
	 */
	public void setFileEnclosureCharFlag(String fileEnclosureCharFlag) {
		this.fileEnclosureCharFlag = fileEnclosureCharFlag;
	}
	/**
	 * healthInfoMaskFlagを返す<br>
	 * @return healthInfoMaskFlag
	 */
	public String getHealthInfoMaskFlag() {
		return healthInfoMaskFlag;
	}
	/**
	 * healthInfoMaskFlagを設定する<br>
	 * @param healthInfoMaskFlag
	 */
	public void setHealthInfoMaskFlag(String healthInfoMaskFlag) {
		this.healthInfoMaskFlag = healthInfoMaskFlag;
	}
	/**
	 * remarksを返す
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * remarksを設定する
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * mailAddressを返す
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * mailAddressを設定する
	 * @param mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	/**
	 * mailPasswordを返す
	 * @return mailPassword
	 */
	public String getMailPassword() {
		return mailPassword;
	}
	/**
	 * mailPasswordを設定する
	 * @param mailPassword
	 */
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

}
