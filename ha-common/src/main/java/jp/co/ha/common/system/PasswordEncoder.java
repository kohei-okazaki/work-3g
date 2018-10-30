package jp.co.ha.common.system;

import jp.co.ha.common.exception.AlgorithmException;

/**
 * パスワード作成サービスインターフェース<br>
 *
 */
public interface PasswordEncoder {

	/** ストレッチング回数 */
	public static final int ITERATION_COUNT = 10000;
	/** 生成される鍵の長さ */
	public static final int KEY_LENGTH = 256;

	/**
	 * パスワードを作成する
	 *
	 * @param password
	 *     パスワード
	 * @param salt
	 *     ソルト
	 * @return
	 * @throws AlgorithmException
	 *     アルゴリズム例外
	 */
	String execute(String password, String salt) throws AlgorithmException;

}
