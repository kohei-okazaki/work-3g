package jp.co.ha.common.api;

/**
 * リザルトタイプ<br>
 * APIの結果コードの列挙<br>
 *
 */
public enum ResultType {

	SUCCESS("success"),
	FAILURE("failure");

	/** コード値 */
	private String code;

	/**
	 * コンストラクタ<br>
	 * @param code
	 */
	private ResultType(String code) {
		setCode(code);
	}

	/**
	 * codeを返す<br>
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する<br>
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
