package jp.co.ha.common.api;

/**
 * 基底リクエストクラス<br>
 *
 */
public abstract class BaseRequest {

	/** リクエストID */
	private String requestId;

	/**
	 * requestIdを返す<br>
	 * @return requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * requestIdを設定する<br>
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
