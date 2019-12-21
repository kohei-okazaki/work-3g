package jp.co.ha.dashboard.account.form;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseForm;

/**
 * アカウント登録画面フォームクラス
 * 
 * @since 1.0
 */
public class AccountRegistForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Required(message = "ユーザIDが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "ユーザIDは半角英数で入力してください")
	@Min(size = 2, message = "ユーザIDは2桁以上で入力してください")
	@Max(size = 16, message = "ユーザIDは16桁以下で入力してください")
	private String userId;
	/** パスワード */
	@Mask
	@Required(message = "パスワードが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "パスワードが半角英数で入力してください")
	@Min(size = 2, message = "パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "パスワードは16桁以下で入力してください")
	private String password;
	/** 確認用パスワード */
	@Mask
	@Required(message = "確認用パスワードが未入力です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "確認用パスワードが半角英数で入力してください")
	@Min(size = 2, message = "確認用パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "確認用パスワードは16桁以下で入力してください")
	private String confirmPassword;
	/** 備考 */
	@Max(size = 256, message = "備考は256桁以下で入力してください")
	private String remarks;

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
	 * confirmPasswordを返す
	 *
	 * @return confirmPassword
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

}
