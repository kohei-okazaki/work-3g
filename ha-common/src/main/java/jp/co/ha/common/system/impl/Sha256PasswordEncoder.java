package jp.co.ha.common.system.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.system.PasswordEncoder;
import jp.co.ha.common.system.type.Algorithm;

/**
 * SHA-256パスワードエンコードクラス
 *
 */
public class Sha256PasswordEncoder implements PasswordEncoder {

	/** アルゴリズム */
	private static final Algorithm HASH_ALGORITHM = Algorithm.SHA_256;
	/** パスワードを安全にするためのアルゴリズム */
	private static final String PASSWORD_ALGORITHM = "PBKDF2WithHmacSHA256";
	/** ストレッチング回数 */
	private static final int ITERATION_COUNT = 10000;
	/** 生成される鍵の長さ */
	private static final int KEY_LENGTH = 256;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(String password, String salt) throws AlgorithmException {

		SecretKey secretKey;
		try {
			char[] passCharAry = password.toCharArray();
			byte[] hashedSalt = getHashedSalt(salt);
			PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);
			secretKey = SecretKeyFactory.getInstance(PASSWORD_ALGORITHM).generateSecret(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException(CommonErrorCode.ALGORITH_ERROR, "アルゴリズムが存在しません、ハッシュアルゴリズム：" + PASSWORD_ALGORITHM);
		} catch (InvalidKeySpecException e) {
			throw new AlgorithmException(CommonErrorCode.ALGORITH_ERROR, "ハッシュアルゴリズム：" + PASSWORD_ALGORITHM);
		}

		// 生成されたバイト配列を16進数の文字列に変換
		StringBuilder sb = new StringBuilder(64);
		for (byte b : secretKey.getEncoded()) {
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
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM.getValue());
			messageDigest.update(salt.getBytes());
			return messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException(CommonErrorCode.ALGORITH_ERROR, "指定したアルゴリズムが存在しません。アルゴリズム：" + HASH_ALGORITHM);
		}
	}
}
