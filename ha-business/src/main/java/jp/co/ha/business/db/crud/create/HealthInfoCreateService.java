package jp.co.ha.business.db.crud.create;

import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報作成サービスインターフェース<br>
 *
 */
public interface HealthInfoCreateService {

	public void create(HealthInfo entity) throws BaseException;
}
