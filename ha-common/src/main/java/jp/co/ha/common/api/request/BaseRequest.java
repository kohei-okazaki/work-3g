package jp.co.ha.common.api.request;

import jp.co.ha.common.api.type.RequestType;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 基底リクエストクラス<br>
 *
 */
public abstract class BaseRequest {

	/** リクエスト種別 */
	private RequestType requestType;
	/** APIキー */
	@Mask
	private String apiKey;

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

	/**
	 * apiKeyを返す<br>
	 *
	 * @return apiKey APIキー
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * apiKeyを設定する<br>
	 *
	 * @param apiKey
	 *     APIキー
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
