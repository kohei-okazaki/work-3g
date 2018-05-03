package jp.co.ha.business.update;

import jp.co.ha.common.entity.MailInfo;

/**
 * メール情報更新サービスインターフェース<br>
 *
 */
public interface MailInfoUpdateService {

	/**
	 * メール情報を更新する<br>
	 * @param mailInfo
	 */
	void update(MailInfo mailInfo);
}
