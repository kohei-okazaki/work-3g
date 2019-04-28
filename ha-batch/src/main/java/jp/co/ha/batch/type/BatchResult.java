package jp.co.ha.batch.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * Batch結果列挙
 *
 */
public enum BatchResult implements BaseEnum {

	/** 正常終了 */
	SUCCESS("0", "batch.result.success"),
	/** 異常終了 */
	FAILURE("1", "batch.result.failure");

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 * @param comment
	 *     コメント
	 */
	private BatchResult(String value, String comment) {
		this.value = value;
		this.comment = comment;
	}

	/** 値 */
	private String value;
	/** コメント */
	private String comment;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	public String getComment() {
		return this.comment;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return BatchResult
	 */
	public static BatchResult of(String value) {
		return BaseEnum.of(BatchResult.class, value);
	}

}
