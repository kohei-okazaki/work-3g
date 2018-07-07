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
	public static final String FILE_ENCLOSURE_CHAR_FLAG = "FILE_ENCLOSURE_CHAR_FLAG";
	public static final String HEALTH_INFO_MASK_FLAG = "HEALTH_INFO_MASK_FLAG";
	public static final String UPDATE_DATE = "UPDATE_DATE";
	public static final String REG_DATE = "REG_DATE";

	/**
	 * 引数で指定されたユーザIDのアカウント情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return Account アカウント情報
	 */
	Account selectByUserId(String userId) throws DataBaseException;

	/**
	 * アカウント情報を作成する<br>
	 *
	 * @param account
	 *     アカウント情報
	 * @throws DuplicateKeyException
	 */
	void create(Account account) throws DuplicateKeyException, DataBaseException;

	/**
	 * 引数で指定されたアカウント情報を更新する
	 *
	 * @param account
	 *     アカウント情報
	 */
	void update(Account account) throws DataBaseException;

	/**
	 * 指定されたアカウント情報の削除を行う<br>
	 *
	 * @param userId
	 *     ユーザID
	 */
	void delete(String userId) throws DataBaseException;

}
