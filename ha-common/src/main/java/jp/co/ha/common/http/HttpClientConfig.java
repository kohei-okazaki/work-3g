package jp.co.ha.common.http;

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
	 * requestUrlを返す
	 *
	 * @return requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * requestUrlを設定する
	 *
	 * @param requestUrl
	 *     リクエストURL
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * timeoutを返す
	 *
	 * @return timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * timeoutを設定する
	 *
	 * @param timeout
	 *     タイムアウト
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * httpMethodを返す
	 *
	 * @return httpMethod
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * httpMethodを設定する
	 *
	 * @param httpMethod
	 *     HTTPステータス
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

}
