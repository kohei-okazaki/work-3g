package jp.co.ha.business.api.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * APIのリクエスト種別の列挙
 *
 * @since 1.0
 */
public enum RequestType implements BaseEnum {

	/** 健康情報登録 */
	HEALTH_INFO_REGIST("001", "健康情報登録"),
	/** 健康情報照会 */
	HEALTH_INFO_REFERENCE("002", "健康情報照会");

	/** 値 */
	private String value;
	/** 名前 */
	private String name;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 * @param name
	 *     名前
	 */
	private RequestType(String value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return RequestType
	 */
	public static RequestType of(String value) {
		return BaseEnum.of(RequestType.class, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * nameを返す
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

}
