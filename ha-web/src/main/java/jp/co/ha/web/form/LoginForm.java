package jp.co.ha.web.form;

import javax.validation.constraints.Pattern;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.validate.annotation.Max;
import jp.co.ha.common.validate.annotation.Min;
import jp.co.ha.common.validate.annotation.Required;
import jp.co.ha.common.web.BaseForm;

/**
 * ログインフォームクラス
 *
 */
public class LoginForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Required(message = "ユーザIDが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "ユーザIDが半角英数でありません")
	@Min(size = 2, message = "ユーザIDは2桁以上で入力してください")
	@Max(size = 16, message = "ユーザIDは16桁以下で入力してください")
	private String userId;
	/** パスワード */
	@Mask
	@Required(message = "パスワードが未入力です")
	@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "パスワードが半角英数でありません")
	@Min(size = 2, message = "パスワードは2桁以上で入力してください")
	@Max(size = 16, message = "パスワードは16桁以下で入力してください")
	private String password;

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

}
