package jp.co.ha.common.api;

/**
 * リザルトタイプ<br>
 * APIの結果コードの列挙<br>
 *
 */
public enum ResultType {

	SUCCESS("success", "成功"),
	FAILURE("failure", "失敗");

	/** コード値 */
	private String code;
	/** メッセージ*/
	private String message;

	/**
	 * コンストラクタ<br>
	 * @param code
	 */
	private ResultType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * codeを返す<br>
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * messageを返す<br>
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

}
