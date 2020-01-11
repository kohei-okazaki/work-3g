package jp.co.ha.common.io.encodeanddecode;

import org.springframework.stereotype.Component;

import jp.co.ha.common.exception.BaseException;

/**
 * Bcryptハッシュ値エンコードクラス
 *
 * @since 1.0
 */
@Component("bcryptHashEncoder")
public class BcryptHashEncoder implements HashEncoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(String password, String salt) throws BaseException {
		// TODO
		return null;
	}

}
