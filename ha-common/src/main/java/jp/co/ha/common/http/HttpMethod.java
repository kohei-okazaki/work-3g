package jp.co.ha.common.http;

import jp.co.ha.common.type.BaseEnum;

/**
 * HTTPメソッドの列挙
 */
public enum HttpMethod implements BaseEnum {

	/** GET */
	GET("GET"),
	/** POST */
	POST("POST"),
	/** DELETE */
	DELETE("DELETE"),
	/** PUT */
	PUT("PUT");

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	private HttpMethod(String value) {
		this.value = value;
	}

	/** 値 */
	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return HttpMethod
	 */
	public static HttpMethod of(String value) {
		return BaseEnum.of(HttpMethod.class, value);
	}
}
