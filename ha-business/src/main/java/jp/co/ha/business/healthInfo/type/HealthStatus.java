package jp.co.ha.business.healthInfo.type;

import java.util.stream.Stream;

/**
 * 健康ステータス
 *
 */
public enum HealthStatus {

	/** 減少 */
	DOWN("10", "減りました"),
	/** 変化なし */
	EVEN("20", "変化ありません"),
	/** 増加 */
	INCREASE("30", "増加しました");

	/** コード値 */
	private String code;
	/** メッセージ */
	private String message;

	/**
	 * コンストラクタ
	 *
	 * @param code
	 *     コード値
	 * @param message
	 *     メッセージ
	 */
	private HealthStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * codeを返す
	 *
	 * @return code コード値
	 */
	public String getCode() {
		return code;
	}

	/**
	 * messageを返す
	 *
	 * @return message メッセージ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * コード値から健康ステータスを返す
	 *
	 * @param code
	 *     コード値
	 * @return HealthStatus
	 */
	public static HealthStatus of(String code) {
		return Stream.of(HealthStatus.class.getEnumConstants())
				.filter(e -> e.getCode().equals(code))
				.findFirst()
				.orElse(null);
	}

}
