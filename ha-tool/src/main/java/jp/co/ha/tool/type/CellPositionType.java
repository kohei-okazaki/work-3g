package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * セル位置列挙
 *
 */
public enum CellPositionType implements BaseEnum {

	/** 論理名 */
	LOGICAL_NAME(0, "logicalName"),
	/** 物理名 */
	PHYSICAL_NAME(1, "physicalName"),
	/** primary key */
	PRIMARY_KEY(2, "primaryKey"),
	/** シーケンス */
	SEQUENCE(3, "sequence"),
	/** カラム名(コメント) */
	COLUMN_NAME_COMMENT(4, "columnNameComment"),
	/** カラム名 */
	COLUMN_NAME(5, "columnName"),
	/** カラムタイプ */
	COLUMN_TYPE(6, "columnType"),
	/** カラムサイズ */
	COLUMN_SIZE(7, "columnSize"),
	/** 備考 */
	REMARKS(8, "remarks"),
	/** 追加フラグ */
	ADD_FLG(9, "addFlg");

	/** 位置 */
	private int position;
	/** ヘッダ名 */
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param position
	 *     位置
	 * @param value
	 *     ヘッダ名
	 */
	private CellPositionType(int position, String value) {
		this.position = position;
		this.value = value;
	}

	/**
	 * positionを返す
	 *
	 * @return 位置
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}
}
