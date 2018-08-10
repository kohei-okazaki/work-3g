package jp.co.ha.business.db.dao;

import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.common.exception.DataBaseException;

/**
 * メール情報のDaoインターフェース
 *
 */
public interface MailInfoDao {

	/** テーブル名 */
	public static final String TABLE_NAME = "MAIL_INFO";

	public static final String USER_ID = "USER_ID";
	public static final String MAIL_ADDRESS = "MAIL_ADDRESS";
	public static final String MAIL_PASSWORD = "MAIL_PASSWORD";
	public static final String UPDATE_DATE = "UPDATE_DATE";
	public static final String REG_DATE = "REG_DATE";

	/**
	 * 引数で指定されたユーザIDのメール情報を取得する
	 *
	 * @param userId
	 *     ユーザID
	 * @return MailInfo メール情報
	 * @throws DataBaseException
	 *     DBアクセスエラー
	 */
	MailInfo selectByUserId(String userId) throws DataBaseException;

	/**
	 * 引数で指定されたメール情報を更新する
	 *
	 * @param mailInfo
	 *     メール情報
	 * @throws DataBaseException
	 *     DBアクセスエラー
	 */
	void update(MailInfo mailInfo) throws DataBaseException;

	/**
	 * 引数で指定されたメール情報を登録する<br>
	 *
	 * @param mailInfo
	 *     メール情報
	 * @throws DataBaseException
	 *     DBアクセスエラー
	 */
	void create(MailInfo mailInfo) throws DataBaseException;
}
