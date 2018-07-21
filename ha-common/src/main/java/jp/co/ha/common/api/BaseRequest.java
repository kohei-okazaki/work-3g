package jp.co.ha.common.api;

/**
 * 基底リクエストクラス<br>
 *
 */
public abstract class BaseRequest {

	/** リクエスト種別 */
	private RequestType requestType;

	/**
	 * requestTypeを返す<br>
	 *
	 * @return requestType リクエスト種別
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * requestTypeを設定する<br>
	 *
	 * @param requestType
	 *     リクエスト種別
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
