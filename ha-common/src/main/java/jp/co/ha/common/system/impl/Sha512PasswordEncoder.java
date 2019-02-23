package jp.co.ha.common.system.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.system.HashEncoder;
import jp.co.ha.common.system.type.Algorithm;
import jp.co.ha.common.type.Charset;

/**
 * SHA512パスワード作成クラス
 *
 */
public class Sha512PasswordEncoder implements HashEncoder {

	/** HASH化アルゴリズム */
	private static final Algorithm HASH_ALGORITHM = Algorithm.SHA_512;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(String password, String salt) throws AlgorithmException {
		try {
			return getPasswordDefault(password, salt, HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException(CommonErrorCode.ALGORITH_ERROR, HASH_ALGORITHM.getValue() + "でのハッシュ化に失敗しました");
		} catch (UnsupportedEncodingException e) {
			throw new AlgorithmException(CommonErrorCode.ALGORITH_ERROR, "指定した文字コードが不正です。文字コード：" + Charset.UTF_8.getValue());
		}
	}
}
