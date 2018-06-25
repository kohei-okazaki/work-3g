package jp.co.ha.api.request;

import jp.co.ha.common.api.BaseRequest;

/**
 * 健康情報照会リクエストクラス<br>
 *
 */
public class HealthInfoReferenceRequest extends BaseRequest {

	/** ユーザID */
	private String userId;
	/** 健康情報ID */
	private String healthInfoId;

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
	 *            ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * healthInfoIdを返す<br>
	 *
	 * @return healthInfoId healthInfoId
	 */
	public String getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 *            healthInfoId
	 */
	public void setHealthInfoId(String healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

}
