package jp.co.ha.business.find;

import jp.co.ha.common.entity.MailInfo;

/**
 * メール情報検索サービスインターフェース<br>
 *
 */
public interface MailInfoSearchService {

	/**
	 * ユーザIDからメール情報を取得する
	 * @param userId
	 * @return メール情報
	 */
	MailInfo findMailInfoByUserId(String userId);

}
