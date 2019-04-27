package jp.co.ha.common.http;

import jp.co.ha.common.type.BaseEnum;

/**
 * HTTPステータス列挙
 */
public enum HttpStatus implements BaseEnum {

	/** OK */
	OK("200"),
	/** リクエストエラー */
	BAD_REQUEST("400");

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	private HttpStatus(String value) {
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
	 * @return HttpStatus
	 */
	public static HttpStatus of(String value) {
		return BaseEnum.of(HttpStatus.class, value);
	}

}
