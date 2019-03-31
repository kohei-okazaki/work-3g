package jp.co.ha.common.http;

import java.util.HashMap;
import java.util.Map;

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
	/** ヘッダー */
	private Map<String, String> headerMap;
	/** ボディ */
	private Map<String, String> bodyMap;

	public HttpClientConfig() {
		this.headerMap = new HashMap<>();
		this.bodyMap = new HashMap<>();
	}

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

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public Map<String, String> getBodyMap() {
		return bodyMap;
	}

	public void setBodyMap(Map<String, String> bodyMap) {
		this.bodyMap = bodyMap;
	}

}
