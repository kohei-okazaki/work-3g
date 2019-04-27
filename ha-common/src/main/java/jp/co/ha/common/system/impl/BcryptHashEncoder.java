package jp.co.ha.common.system.impl;

import org.springframework.stereotype.Service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.HashEncoder;

/**
 * Bcryptハッシュ値エンコードクラス
 *
 */
@Service("bcryptHashEncoder")
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
