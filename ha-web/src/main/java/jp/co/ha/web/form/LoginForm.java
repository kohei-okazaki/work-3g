package jp.co.ha.web.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ha.common.web.BaseForm;

/**
 * ログインフォームクラス
 *
 */
public class LoginForm implements BaseForm {

	/** ユーザID */
	@NotEmpty(message = "ユーザIDが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ユーザIDが半角英数でありません")
	@Size(min = 2, max = 10, message = "ユーザIDが範囲外の値です")
	private String userId;
	/** パスワード */
	@NotEmpty(message = "パスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "パスワードが半角英数でありません")
	@Size(min = 2, max = 10, message = "パスワードが範囲外の値です")
	private String password;

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

}
