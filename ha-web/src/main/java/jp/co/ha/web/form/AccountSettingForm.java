package jp.co.ha.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ha.common.log.Ignore;
import jp.co.ha.common.log.Mask;
import jp.co.ha.common.web.BaseForm;

/**
 * アカウント設定画面フォームクラス<br>
 *
 */
public class AccountSettingForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@NotEmpty(message = "ユーザIDが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ユーザIDが半角英数でありません")
	@Size(min = 2, max = 16, message = "ユーザIDが範囲外の値です")
	private String userId;
	/** パスワード */
	@Mask
	@NotEmpty(message = "パスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "パスワードが半角英数でありません")
	@Size(min = 2, max = 16, message = "パスワードが範囲外の値です")
	private String password;
	/** 削除フラグ */
	@NotEmpty
	@Pattern(regexp = "^[0-9]*$", message = "削除フラグが半角数字でありません")
	@Size(min = 1, max = 1)
	private String deleteFlag;
	/** ファイル囲い文字利用フラグ */
	@NotEmpty
	@Pattern(regexp = "^[0-9]*$", message = "ファイル囲い文字利用フラグが半角数字でありません")
	@Size(min = 1, max = 1)
	private String fileEnclosureCharFlag;
	/** 健康情報マスク利用フラグ */
	@NotEmpty
	@Pattern(regexp = "^[0-9]*$", message = "健康情報マスク利用フラグが半角数字でありません")
	@Size(min = 1, max = 1)
	private String healthInfoMaskFlag;
	/** 備考 */
	@Size(max = 256)
	private String remarks;
	/** メールアドレス */
	@Mask
	@NotEmpty(message = "メールアドレスが未入力です")
	@Email(message = "メールアドレス形式ではありません")
	private String mailAddress;
	/** メールパスワード */
	@Mask
	@NotEmpty(message = "メールパスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "メールパスワードが半角英数でありません")
	private String mailPassword;

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
	 *            パスワード
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
	 *            削除フラグ
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * fileEnclosureCharFlagを返す
	 *
	 * @return fileEnclosureCharFlag ファイル囲い文字利用フラグ
	 */
	public String getFileEnclosureCharFlag() {
		return fileEnclosureCharFlag;
	}

	/**
	 * fileEnclosureCharFlagを設定する
	 *
	 * @param fileEnclosureCharFlag
	 *            ファイル囲い文字利用フラグ
	 */
	public void setFileEnclosureCharFlag(String fileEnclosureCharFlag) {
		this.fileEnclosureCharFlag = fileEnclosureCharFlag;
	}

	/**
	 * healthInfoMaskFlagを返す<br>
	 *
	 * @return healthInfoMaskFlag 健康情報マスク利用フラグ
	 */
	public String getHealthInfoMaskFlag() {
		return healthInfoMaskFlag;
	}

	/**
	 * healthInfoMaskFlagを設定する<br>
	 *
	 * @param healthInfoMaskFlag
	 *            健康情報マスク利用フラグ
	 */
	public void setHealthInfoMaskFlag(String healthInfoMaskFlag) {
		this.healthInfoMaskFlag = healthInfoMaskFlag;
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
	 *            備考
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
