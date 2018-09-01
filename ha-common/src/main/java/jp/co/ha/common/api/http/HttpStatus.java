package jp.co.ha.common.api.http;

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
}
