package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * Propertyの列挙
 *
 */
public enum PropertyType implements BaseEnum {

	/** 対象テーブル */
	TARGET_TABLE("targetTable"),
	/** 基底ディレクトリ */
	BASE_DIR("baseDir");

	private PropertyType(String value) {
		this.value = value;
	}

	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}
}
