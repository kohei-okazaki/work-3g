package jp.co.ha.business.api.request;

import jp.co.ha.business.api.request.BaseApiRequest;

/**
 * 健康情報照会リクエストクラス
 *
 */
public class HealthInfoReferenceRequest extends BaseApiRequest {

	/** 健康情報ID */
	private Integer healthInfoId;

	/**
	 * healthInfoIdを返す
	 *
	 * @return healthInfoId
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

}
