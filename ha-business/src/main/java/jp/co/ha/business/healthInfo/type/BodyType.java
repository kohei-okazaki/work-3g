package jp.co.ha.business.healthInfo.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * 体型の列挙
 *
 */
public enum BodyType implements BaseEnum {

	/** 痩せ型 */
	SLIM("0"),
	/** 普通 */
	NORMAL("1"),
	/** 太っている */
	FAT("2");

	/** 値 */
	private String value;

	private BodyType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return BodyType
	 */
	public static BodyType of(String value) {
		return BaseEnum.of(BodyType.class, value);
	}

}
