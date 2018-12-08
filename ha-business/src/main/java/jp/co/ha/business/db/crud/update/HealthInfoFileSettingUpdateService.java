package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定更新サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingUpdateService {

	/**
	 * 指定した健康情報ファイル設定を更新する
	 *
	 * @param entity
	 *     健康情報ファイル設定
	 * @throws BaseException
	 */
	void update(HealthInfoFileSetting entity) throws BaseException;
}
