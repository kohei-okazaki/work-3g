package jp.co.ha.common.dao;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.MailInfo;

/**
 * メール情報のDaoインターフェース
 *
 */
public interface MailInfoDao extends BaseDao {

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
	 *            ユーザID
	 * @return MailInfo メール情報
	 */
	MailInfo selectByUserId(String userId);

	/**
	 * 引数で指定されたメール情報を更新する
	 *
	 * @param mailInfo
	 *            メール情報
	 */
	void update(MailInfo mailInfo);

	/**
	 * 引数で指定されたメール情報を登録する<br>
	 *
	 * @param mailInfo
	 *            メール情報
	 */
	void create(MailInfo mailInfo) throws DuplicateKeyException;
}
