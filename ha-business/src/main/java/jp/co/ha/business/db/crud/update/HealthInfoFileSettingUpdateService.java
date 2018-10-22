package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定更新サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingUpdateService {

	void update(HealthInfoFileSetting entity) throws BaseException;
}
