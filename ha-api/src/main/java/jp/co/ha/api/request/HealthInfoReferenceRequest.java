package jp.co.ha.api.request;

import jp.co.ha.common.api.request.BaseRequest;

/**
 * 健康情報照会リクエストクラス<br>
 *
 */
public class HealthInfoReferenceRequest extends BaseRequest {

	/** ユーザID */
	private String userId;
	/** 健康情報ID */
	private Integer healthInfoId;

	/**
	 * userIdを返す<br>
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する<br>
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
