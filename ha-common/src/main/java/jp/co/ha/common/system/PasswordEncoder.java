package jp.co.ha.common.system;

import jp.co.ha.common.exception.AlgorithmException;

/**
 * パスワード作成処理インターフェース
 *
 */
public interface PasswordEncoder {

	/**
	 * パスワードを作成する
	 *
	 * @param password
	 *     パスワード
	 * @param salt
	 *     ソルト
	 * @return パスワード
	 * @throws AlgorithmException
	 *     アルゴリズム例外
	 */
	String execute(String password, String salt) throws AlgorithmException;

}
