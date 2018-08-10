package jp.co.ha.business.db.find;

import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定検索サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingSearchService {

	/**
	 * 健康情報ファイル設定を検索する<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws BaseException
	 */
	HealthInfoFileSetting findByUserId(String userId) throws BaseException;
}
