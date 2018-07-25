package jp.co.ha.web.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.validate.annotation.Required;
import jp.co.ha.common.web.BaseForm;

/**
 * アカウント登録画面フォームクラス<br>
 *
 */
public class AccountRegistForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Required(message = "ユーザIDが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ユーザIDが半角英数でありません")
	@Size(min = 2, max = 16, message = "ユーザIDが範囲外の値です")
	private String userId;
	/** パスワード */
	@Mask
	@Required(message = "パスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "パスワードが半角英数でありません")
	@Size(min = 2, max = 16, message = "パスワードが範囲外の値です")
	private String password;
	/** 確認用パスワード */
	@Mask
	@Required(message = "確認用パスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "確認用パスワードが半角英数でありません")
	@Size(min = 2, max = 16, message = "確認用パスワードが範囲外の値です")
	private String confirmPassword;
	/** 備考 */
	@Size(max = 256, message = "備考が範囲外の値です")
	private String remarks;

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
	 * confirmPasswordを返す
	 *
	 * @return confirmPassword 確認用パスワード
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * confirmPasswordを設定する
	 *
	 * @param confirmPassword
	 *     確認用パスワード
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

}
