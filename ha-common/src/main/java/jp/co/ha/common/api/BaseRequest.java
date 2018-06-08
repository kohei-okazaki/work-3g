package jp.co.ha.common.api;

/**
 * 基底リクエストクラス<br>
 *
 */
public abstract class BaseRequest {

	/** リクエストタイプ */
	private RequestType requestType;

	/**
	 * requestTypeを返す<br>
	 * @return requestType リクエストタイプ
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * requestTypeを設定する<br>
	 * @param requestType リクエストタイプ
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
