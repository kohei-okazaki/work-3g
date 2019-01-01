package jp.co.ha.common.api.type;

/**
 * APIの結果コードの列挙
 *
 */
public enum ResultType {

	/** SUCCESS */
	SUCCESS("success", "成功"),
	/** FAILURE */
	FAILURE("failure", "失敗");

	/** コード値 */
	private String code;
	/** メッセージ */
	private String message;

	/**
	 * コンストラクタ
	 *
	 * @param code
	 *     コード
	 * @param message
	 *     メッセージ
	 */
	private ResultType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * codeを返す
	 *
	 * @return code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * messageを返す
	 *
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}

}
