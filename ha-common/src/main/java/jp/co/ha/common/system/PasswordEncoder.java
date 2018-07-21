package jp.co.ha.common.system;

public interface PasswordEncoder {

	/**
	 * パスワードを作成する
	 *
	 * @param password
	 *     パスワード
	 * @param salt
	 *     ソルト
	 * @return
	 */
	String execute(String password, String salt);

}
