package jp.co.ha.common.system.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.system.PasswordEncoder;
import jp.co.ha.common.system.type.Algorithm;

/**
 * SHA-256パスワード作成クラス<br>
 *
 */
public class Sha256PasswordEncoder implements PasswordEncoder {

	/** パスワードを安全にするためのアルゴリズム */
	private static final String PASSWORD_ALGORITHM = "PBKDF2WithHmacSHA256";
	/** ハッシュアルゴリズム */
	private static final Algorithm HASH_ALGORITHM = Algorithm.SHA_256;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(String password, String salt) throws AlgorithmException {

		char[] passCharAry = password.toCharArray();
		byte[] hashedSalt = getHashedSalt(salt);

		PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

		SecretKey secretKey;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PASSWORD_ALGORITHM);
			secretKey = skf.generateSecret(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException(ErrorCode.ALGORITH_ERROR, "アルゴリズムが存在しません、ハッシュアルゴリズム：" + PASSWORD_ALGORITHM);
		} catch (InvalidKeySpecException e) {
			throw new AlgorithmException(ErrorCode.ALGORITH_ERROR, "ハッシュアルゴリズム：" + PASSWORD_ALGORITHM);
		}

		byte[] passByteAry = secretKey.getEncoded();

		// 生成されたバイト配列を16進数の文字列に変換
		StringBuilder sb = new StringBuilder(64);
		for (byte b : passByteAry) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * ソルトをハッシュ化して返却<br>
	 * ハッシュアルゴリズムはSHA-256を使用<br>
	 *
	 * @param salt
	 *     ソルト
	 * @return ハッシュ化されたバイト配列のソルト
	 * @throws AlgorithmException
	 *     アルゴリズム例外
	 */
	private static byte[] getHashedSalt(String salt) throws AlgorithmException {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(HASH_ALGORITHM.getValue());
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException(ErrorCode.ALGORITH_ERROR, "ソルトのハッシュ化に失敗しました、ハッシュアルゴリズム：" + HASH_ALGORITHM);
		}
		messageDigest.update(salt.getBytes());
		return messageDigest.digest();
	}
}
