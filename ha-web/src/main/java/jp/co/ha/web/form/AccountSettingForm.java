package jp.co.ha.web.form;

import javax.validation.constraints.Email;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.util.RegixPattern;
import jp.co.ha.common.validate.annotation.Flag;
import jp.co.ha.common.validate.annotation.Max;
import jp.co.ha.common.validate.annotation.Min;
import jp.co.ha.common.validate.annotation.Pattern;
import jp.co.ha.common.validate.annotation.Required;
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
	@Required(message = "ユーザIDが未入力です")
	@Pattern(regixPattern = RegixPattern.HALF_CHAR, message = "ユーザIDが半角英数でありません")
	@Min(size = 2, message = "ユーザIDは2桁以上で入力してください")
	@Max(size = 16, message = "ユーザIDは16桁以下で入力してください")
	private String userId;
	/** パスワード */
	@Mask
	@Required(message = "パスワードが未入力です")
	@Pattern(regixPattern = RegixPattern.HALF_CHAR, message = "パスワードが半角英数でありません")
	@Min(size = 2, message = "パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "パスワードは16桁以下で入力してください")
	private String password;
	/** 削除フラグ */
	@Required
	@Pattern(regixPattern = RegixPattern.HALF_NUMBER, message = "削除フラグが半角数字でありません")
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
	@Pattern(regixPattern = RegixPattern.HALF_CHAR, message = "メールパスワードが半角英数でありません")
	private String mailPassword;
	/** APIキー */
	@Mask
	@Required(message = "APIキーが未入力です")
	private String apiKey;

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
	 * apiKeyを返す
	 *
	 * @return apiKey APIキー
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
