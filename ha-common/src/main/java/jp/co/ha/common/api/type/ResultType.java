package jp.co.ha.common.api.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * APIの結果コードの列挙
 *
 */
public enum ResultType implements BaseEnum {

	/** SUCCESS */
	SUCCESS("success", "成功"),
	/** FAILURE */
	FAILURE("failure", "失敗");

	/** コード値 */
	private String value;
	/** メッセージ */
	private String message;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     コード値
	 * @param message
	 *     メッセージ
	 */
	private ResultType(String value, String message) {
		this.value = value;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean is(String value) {
		return this.value.equals(value);
	}

	/**
	 * messageを返す
	 *
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return ResultType
	 */
	public static ResultType of(String value) {
		return BaseEnum.of(ResultType.class, value);
	}

}
