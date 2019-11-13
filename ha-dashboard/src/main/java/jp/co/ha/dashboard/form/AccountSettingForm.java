package jp.co.ha.dashboard.form;

import javax.validation.constraints.Email;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseForm;

/**
 * アカウント設定画面フォームクラス
 *
 * @since 1.0
 */
public class AccountSettingForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Required(message = "ユーザIDが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "ユーザIDが半角英数でありません")
	@Min(size = 2, message = "ユーザIDは2桁以上で入力してください")
	@Max(size = 16, message = "ユーザIDは16桁以下で入力してください")
	private String userId;
	/** パスワード */
	@Mask
	@Required(message = "パスワードが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "パスワードが半角英数でありません")
	@Min(size = 2, message = "パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "パスワードは16桁以下で入力してください")
	private String password;
	/** 削除フラグ */
	@Required(message = "削除フラグが未入力です")
	@Pattern(regixPattern = RegixType.HALF_NUMBER, message = "削除フラグが半角数字でありません")
	@Flag(message = "削除フラグの値が不正です")
	private String deleteFlag;
	/** 備考 */
	@Max(size = 256, message = "備考は256桁以下で入力してください")
	private String remarks;
	/** メールアドレス */
	@Mask
	@Required(message = "メールアドレスが未入力です")
	@Email(message = "メールアドレス形式ではありません")
	private String mailAddress;
	/** メールパスワード */
	@Mask
	@Required(message = "メールパスワードが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "メールパスワードが半角英数でありません")
	private String mailPassword;
	/** APIキー */
	@Mask
	@Required(message = "APIキーが未入力です")
	private String apiKey;
	/** パスワード有効期限 */
	@Required(message = "パスワード有効期限が未入力です")
	private String passwordExpire;

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
	 * passwordを返す
	 *
	 * @return password
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
	 * @return deleteFlag
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
	 * remarksを返す
	 *
	 * @return remarks
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
	 * mailPasswordを返す
	 *
	 * @return mailPassword
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

	/**
	 * passwordExpireを返す
	 *
	 * @return passwordExpire
	 */
	public String getPasswordExpire() {
		return passwordExpire;
	}

	/**
	 * passwordExpireを設定する
	 *
	 * @param passwordExpire
	 *     パスワード有効期限日
	 */
	public void setPasswordExpire(String passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

}
