package jp.co.ha.common.validator;

/**
 * 妥当性チェックエラー
 */
public class ValidateError {

	/** フィールド名 */
	private String name;
	/** エラーメッセージ */
	private String message;
	/** 値 */
	private String value;

	/**
	 * nameを返す
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * messageを返す
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * messageを設定する
	 *
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * valueを返す
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * valueを設定する
	 *
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
