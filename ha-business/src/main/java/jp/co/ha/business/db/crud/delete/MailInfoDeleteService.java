package jp.co.ha.business.db.crud.delete;

/**
 * メール情報削除サービスインターフェース
 *
 * @since 1.0
 */
public interface MailInfoDeleteService {

	/**
	 * 指定されたユーザIDのメール情報を削除する
	 *
	 * @param userId
	 *     ユーザID
	 */
	void deleteByUserId(String userId);

}
