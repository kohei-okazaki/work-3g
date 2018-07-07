package jp.co.ha.web.service;

import jp.co.ha.web.form.LoginForm;

/**
 * ログインサービスインターフェース<br>
 *
 */
public interface LoginService {

	/**
	 * ログイン情報と入力情報を照合する
	 *
	 * @param loginForm
	 *     ログインフォーム
	 * @return 判定結果
	 */
	boolean invalidPassword(LoginForm loginForm);

	/**
	 * アカウント情報が存在するかチェック<br>
	 * 存在しないtrue, 存在する場合falseを返す<br>
	 *
	 * @param loginForm
	 *     ログインフォーム
	 * @return
	 */
	boolean noExistAccount(LoginForm loginForm);

	/**
	 * アカウント情報が有効かどうかチェック<br>
	 * 有効でない場合true, そうでない場合false<br>
	 *
	 * @param loginForm
	 *     ログインフォーム
	 * @return
	 */
	boolean invalidAccount(LoginForm loginForm);

}
