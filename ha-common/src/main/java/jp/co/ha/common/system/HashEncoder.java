package jp.co.ha.common.system;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.system.type.Algorithm;
import jp.co.ha.common.type.Charset;

/**
 * ハッシュ化処理インターフェース
 *
 */
public interface HashEncoder {

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
	String encode(String password, String salt) throws AlgorithmException;

	/**
	 * デフォルトで指定した値からハッシュ化した値を返す
	 *
	 * @param password
	 *     パスワード
	 * @param salt
	 *     ソルト
	 * @param algorithm
	 *     アルゴリズム
	 * @return
	 * @throws NoSuchAlgorithmException
	 *     指定したアルゴリズムが存在しない場合
	 * @throws UnsupportedEncodingException
	 *     指定した文字コードが存在しない場合
	 */
	public default String getPasswordDefault(String password, String salt, Algorithm algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messsageDigest = MessageDigest.getInstance(algorithm.getValue());
		String str = password + salt;
		messsageDigest.update(str.getBytes(Charset.UTF_8.getValue()));
		byte[] cipherByte = messsageDigest.digest();
		StringBuilder sb = new StringBuilder(2 * cipherByte.length);
		for (byte b : cipherByte) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

}
