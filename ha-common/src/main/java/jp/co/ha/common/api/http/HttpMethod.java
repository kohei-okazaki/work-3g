package jp.co.ha.common.api.http;

/**
 * HTTPメソッドの列挙クラス<br>
 *
 */
public enum HttpMethod {

	/** GET */
	GET("GET"),
	/** POST */
	POST("POST");

	private HttpMethod(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
