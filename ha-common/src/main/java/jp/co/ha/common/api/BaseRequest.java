package jp.co.ha.common.api;

/**
 * 基底リクエストクラス<br>
 *
 */
public abstract class BaseRequest {

	/** リクエストタイプ */
	private RequestType requestType;

	/**
	 * ｒequestTypeを返す<br>
	 * @return ｒequestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * ｒequestTypeを設定する<br>
	 * @param ｒequestType
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
