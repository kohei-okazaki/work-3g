package jp.co.ha.business.io.file.excel.model;

import jp.co.ha.common.io.file.excel.model.BaseExcelComponent;
import jp.co.ha.db.entity.HealthInfo;

public class HealthInfoExcelComponent implements BaseExcelComponent {

	/** 健康情報 */
	private HealthInfo healthInfo;

	/**
	 * healthInfoを返す
	 *
	 * @return healthInfo
	 */
	public HealthInfo getHealthInfo() {
		return healthInfo;
	}

	/**
	 * healthInfoを設定する
	 *
	 * @param healthInfo
	 *     健康情報
	 */
	public void setHealthInfo(HealthInfo healthInfo) {
		this.healthInfo = healthInfo;
	}

}
