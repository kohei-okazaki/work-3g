package jp.co.ha.common.api.http;

import java.util.stream.Stream;

/**
 * HTTPステータス列挙
 *
 */
public enum HttpStatus {

	/** OK */
	OK(200),
	/** リクエストエラー */
	BAD_REQUEST(400);

	private HttpStatus(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return this.value;
	}

	public static HttpStatus of(int value) {
		return Stream.of(HttpStatus.class.getEnumConstants())
					.filter(e -> e.getValue() == value)
					.findFirst()
					.orElse(null);
	}
}
