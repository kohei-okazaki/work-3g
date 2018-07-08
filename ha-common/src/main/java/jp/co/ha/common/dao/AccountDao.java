package jp.co.ha.common.dao;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.DataBaseException;

/**
 * アカウント情報のDaoインターフェイス
 *
 */
public interface AccountDao {

	public static final String TABLE_NAME = "ACCOUNT";

	public static final String USER_ID = "USER_ID";
	public static final String PASSWORD = "PASSWORD";
	public static final String DELETE_FLAG = "DELETE_FLAG";
	public static final String PASSWORD_EXPIRE = "PASSWORD_EXPIRE";
	public static final String REMARKS = "REMARKS";
	public static final String UPDATE_DATE = "UPDATE_DATE";
	public static final String REG_DATE = "REG_DATE";

	/**
	 * 引数で指定されたユーザIDのアカウント情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return Account アカウント情報
	 * @throws DataBaseException
	 *     DBエラー
	 */
	Account selectByUserId(String userId) throws DataBaseException;

	/**
	 * アカウント情報を作成する<br>
	 *
	 * @param account
	 *     アカウント情報
	 * @throws DuplicateKeyException
	 * @throws DataBaseException
	 *     DBエラー
	 */
	void create(Account account) throws DuplicateKeyException, DataBaseException;

	/**
	 * 引数で指定されたアカウント情報を更新する
	 *
	 * @param account
	 *     アカウント情報
	 * @throws DataBaseException
	 *     DBエラー
	 */
	void update(Account account) throws DataBaseException;

	/**
	 * 指定されたアカウント情報の削除を行う<br>
	 *
	 * @param userId
	 *     ユーザID
	 *
	 * @throws DataBaseException
	 *     DBエラー
	 */
	void delete(String userId) throws DataBaseException;

}
