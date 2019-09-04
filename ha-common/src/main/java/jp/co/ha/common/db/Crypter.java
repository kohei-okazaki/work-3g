package jp.co.ha.common.db;

/**
 * 暗号化インターフェース
 *
 */
public interface Crypter {

	/**
	 * 暗号化を行う
	 *
	 * @param str
	 *     暗号化したい文字列
	 * @return 暗号化の文字列
	 */
	String encrypt(String str);

	/**
	 * 復号処理を行う
	 *
	 * @param str
	 *     復号したい文字列
	 * @return 復号化の文字列
	 */
	String decrypt(String str);
}
