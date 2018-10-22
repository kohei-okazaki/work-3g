package jp.co.ha.business.db.crud.update;

import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定更新サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingUpdateService {

	void update(HealthInfoFileSetting entity) throws BaseException;
}
