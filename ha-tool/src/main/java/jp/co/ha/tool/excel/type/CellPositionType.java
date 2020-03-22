package jp.co.ha.tool.excel.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * セル位置の列挙
 *
 * @since 1.0
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
    /** 暗号化 */
    CRYPT(4, "crypt"),
    /** NotNULL制約 */
    NOT_NULL(5, "notNull"),
    /** カラム名(コメント) */
    COLUMN_NAME_COMMENT(6, "columnNameComment"),
    /** カラム名 */
    COLUMN_NAME(7, "columnName"),
    /** カラムタイプ */
    COLUMN_TYPE(8, "columnType"),
    /** カラムサイズ */
    COLUMN_SIZE(9, "columnSize"),
    /** 備考 */
    REMARKS(10, "remarks"),
    /** 追加フラグ */
    ADD_FLG(11, "addFlg");

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

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return CellPositionType
     */
    public static CellPositionType of(String value) {
        return BaseEnum.of(CellPositionType.class, value);
    }
}
