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
	 * dataIdを返す<br>
	 *
	 * @return dataId データID
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * dataIdを設定する<br>
	 *
	 * @param dataId
	 *            データID
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

}
