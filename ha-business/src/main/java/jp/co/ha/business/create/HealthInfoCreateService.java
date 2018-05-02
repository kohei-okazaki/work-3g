package jp.co.ha.business.create;

import jp.co.ha.common.entity.HealthInfo;

public interface HealthInfoCreateService {

	/**
	 * 健康情報を作成する<br>
	 * @param healthInfo
	 */
	void create(HealthInfo healthInfo);

}
