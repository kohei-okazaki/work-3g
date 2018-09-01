package jp.co.ha.common.api.http;

/**
 * HTTPクライアントの設定情報保持クラス
 *
 */
public class HttpClientConfig {

	/** リクエストURL */
	private String requestUrl;
	/** タイムアウトまでの時間 */
	private int timeout;
	/** HTTPメソッド */
	private HttpMethod httpMethod;

	/**
	 * requestUrlを返す<br>
	 *
	 * @return requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * requestUrlを設定する<br>
	 *
	 * @param requestUrl
	 *     リクエストURL
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * timeoutを返す<br>
	 *
	 * @return timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * timeoutを設定する<br>
	 *
	 * @param timeout
	 *     タイムアウト
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * httpMethodを返す<br>
	 *
	 * @return httpMethod
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * httpMethodを設定する<br>
	 *
	 * @param httpMethod
	 *     HTTPステータス
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

}
