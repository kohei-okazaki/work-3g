package jp.co.ha.common.dao;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.entity.MailInfo;

/**
 * メール情報のDaoインターフェース
 *
 */
public interface MailInfoDao extends BaseDao {

	/** 保存先シート名 */
	public static final String SHEET = "MAIL_INFO";

	/**
	 * 引数で指定されたユーザIDのメール情報を取得する
	 * @param userId
	 * @return AccountDto
	 */
	MailInfo getMailInfoByUserId(String userId);

	/**
	 * 引数で指定されたメール情報を更新する
	 * @param mailInfoDto
	 */
	void updateMailInfo(MailInfo mailInfo);

	/**
	 * 引数で指定されたメール情報を登録する<br>
	 * @param mailInfoDto
	 */
	void registMailInfo(MailInfo mailInfo) throws DuplicateKeyException;
}
