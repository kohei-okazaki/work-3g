package jp.co.ha.web.form;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.util.RegixPattern;
import jp.co.ha.common.validate.annotation.Max;
import jp.co.ha.common.validate.annotation.Min;
import jp.co.ha.common.validate.annotation.Pattern;
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
	/** 確認用パスワード */
	@Mask
	@Required(message = "確認用パスワードが未入力です")
	@Pattern(regixPattern = RegixPattern.HALF_CHAR, message = "確認用パスワードが半角英数でありません")
	@Min(size = 2, message = "確認用パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "確認用パスワードは16桁以下で入力してください")
	private String confirmPassword;
	/** 備考 */
	@Max(size = 256, message = "備考が範囲外の値です")
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
