package jp.co.ha.common.db;

import jp.co.ha.common.exception.BaseException;

/**
 * Entityの暗号/復号インターフェース
 *
 * @since 1.0
 */
public interface EntityCrypter {

	/**
	 * 指定されたEntityクラスの暗号化を行う
	 *
	 * @param entity
	 *     Entityクラス
	 * @throws BaseException
	 *     基底例外
	 */
	void encrypt(Object entity) throws BaseException;

	/**
	 * 指定されたEntityクラスの復号を行う
	 *
	 * @param entity
	 *     Entityクラス
	 * @throws BaseException
	 *     基底例外
	 */
	void decrypt(Object entity) throws BaseException;
}
