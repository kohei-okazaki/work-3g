package jp.co.ha.web.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jp.co.ha.common.web.BaseForm;

/**
 * アカウント作成画面フォームクラス<br>
 *
 */
public class AccountRegistForm implements BaseForm {

	/** ユーザID */
	@NotEmpty
	@Size(min = 2, max = 10)
	private String userId;
	/** パスワード */
	@NotEmpty
	@Size(min = 2, max = 10)
	private String password;
	/** 確認用パスワード */
	@NotEmpty
	@Size(min = 2, max = 10)
	private String confirmPassword;
	/** 備考 */
	@Size(max = 256)
	private String remarks;

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
	 * confirmPasswordを返す
	 * @return confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * confirmPasswordを設定する
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

}
