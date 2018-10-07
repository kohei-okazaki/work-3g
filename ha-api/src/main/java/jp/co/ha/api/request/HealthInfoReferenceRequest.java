package jp.co.ha.api.request;

import jp.co.ha.business.api.request.BaseApiRequest;

/**
 * 健康情報照会リクエストクラス<br>
 *
 */
public class HealthInfoReferenceRequest extends BaseApiRequest {

	/** 健康情報ID */
	private Integer healthInfoId;

	/**
	 * healthInfoIdを返す<br>
	 *
	 * @return healthInfoId 健康情報ID
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

}
