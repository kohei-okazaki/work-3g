package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.MailInfo;

/**
 * メール情報更新サービスインターフェース
 *
 * @since 1.0
 */
public interface MailInfoUpdateService {

	/**
	 * メール情報を更新する
	 *
	 * @param entity
	 *     メール情報
	 */
	void update(MailInfo entity);
}
