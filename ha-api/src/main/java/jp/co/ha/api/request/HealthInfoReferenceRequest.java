package jp.co.ha.api.request;

import jp.co.ha.common.api.BaseRequest;

/**
 * 健康情報照会リクエストクラス<br>
 *
 */
public class HealthInfoReferenceRequest extends BaseRequest {

	/** ユーザID */
	private String userId;
	/** データID */
	private String dataId;

	/**
	 * userIdを返す<br>
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * userIdを設定する<br>
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * dataIdを返す<br>
	 * @return dataId
	 */
	public String getDataId() {
		return dataId;
	}
	/**
	 * dataIdを設定する<br>
	 * @param dataId
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

}
