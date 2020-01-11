package jp.co.ha.business.healthInfo.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * 健康情報ステータスの列挙
 *
 * @since 1.0
 */
public enum HealthInfoStatus implements BaseEnum {

	/** 減少 */
	DOWN("10", "healthInfoStatus.down"),
	/** 変化なし */
	EVEN("20", "healthInfoStatus.even"),
	/** 増加 */
	INCREASE("30", "healthInfoStatus.increase");

	/** 値 */
	private String value;
	/** メッセージ */
	private String message;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 * @param message
	 *     メッセージ
	 */
	private HealthInfoStatus(String value, String message) {
		this.value = value;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return value;
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
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return HealthInfoStatus
	 */
	public static HealthInfoStatus of(String value) {
		return BaseEnum.of(HealthInfoStatus.class, value);
	}

}
