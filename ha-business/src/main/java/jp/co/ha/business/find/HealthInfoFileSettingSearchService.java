package jp.co.ha.business.find;

import jp.co.ha.common.entity.HealthInfoFileSetting;

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
	 */
	HealthInfoFileSetting findByUserId(String userId);
}
