package jp.co.ha.business.db.crud.read;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;

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
	 * @return 健康情報ファイル設定
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfoFileSetting findByUserId(String userId) throws BaseException;
}
