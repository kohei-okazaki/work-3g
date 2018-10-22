package jp.co.ha.business.db.crud.create;

import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定作成サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingCreateService {

	void create(HealthInfoFileSetting entity) throws BaseException;
}
