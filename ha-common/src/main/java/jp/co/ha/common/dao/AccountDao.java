package jp.co.ha.common.dao;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.Account;

/**
 * アカウント情報のDaoインターフェイス
 *
 */
public interface AccountDao extends BaseDao {

	/** 保存先シート名 */
	public static final String SHEET = "ACCOUNT";

	/**
	 * 引数で指定されたユーザIDのアカウント情報を取得する
	 *
	 * @param userId
	 *            ユーザID
	 * @return Account アカウント情報
	 */
	Account findByUserId(String userId);

	/**
	 * アカウント情報を作成する<br>
	 *
	 * @param account
	 *            アカウント情報
	 * @throws DuplicateKeyException
	 */
	void create(Account account) throws DuplicateKeyException;

	/**
	 * 引数で指定されたアカウント情報を更新する
	 *
	 * @param account
	 *            アカウント情報
	 */
	void updateAccount(Account account);

	/**
	 * 指定されたアカウント情報の削除を行う<br>
	 *
	 * @param userId
	 *            ユーザID
	 */
	void deleteAccount(String userId);

}
