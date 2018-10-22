package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定作成サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingCreateService {

	void create(HealthInfoFileSetting entity) throws BaseException;
}
