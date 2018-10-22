package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報作成サービスインターフェース<br>
 *
 */
public interface HealthInfoCreateService {

	public void create(HealthInfo entity) throws BaseException;
}
