package jp.co.ha.tool.build;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.type.CellPositionType;

/**
 * SQLのビルダー
 *
 * @since 1.0
 */
public abstract class BaseSqlSourceBuilder extends BaseBuilder {

	/**
	 * カラムタイプを返す
	 *
	 * @param row
	 *     行
	 * @return カラムタイプ
	 */
	protected String getColumnType(Row row) {
		StringJoiner body = new StringJoiner(StringUtil.SPACE);
		String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
		String size = getSize(row);
		body.add(columnType + size);
		if (isSequence(row)) {
			body.add("AUTO_INCREMENT");
		}
		if (isPrimaryKey(row)) {
			body.add("NOT NULL PRIMARY KEY");
		}
		return body.toString();
	}

	/**
	 * カラム名を返す
	 *
	 * @param row
	 *     行
	 * @return カラム名
	 */
	protected String getColumnName(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME).getValue();
	}

	/**
	 * カラムコメントを返す
	 *
	 * @param row
	 *     行
	 * @return カラムコメント
	 */
	protected String getColumnComment(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME_COMMENT).getValue();
	}

	/**
	 * シーケンスであるかどうかを返す
	 *
	 * @param row
	 *     行
	 * @return シーケンスであるかどうか
	 */
	protected boolean isSequence(Row row) {
		Predicate<Row> function = e -> CommonFlag.TRUE.is(e.getCell(CellPositionType.SEQUENCE).getValue());
		return function.test(row);
	}

	/**
	 * PrimaryKeyであるかどうかを返す
	 *
	 * @param row
	 *     行
	 * @return PrimaryKeyであるかどうか
	 */
	protected boolean isPrimaryKey(Row row) {
		Predicate<Row> function = e -> CommonFlag.TRUE.is(e.getCell(CellPositionType.PRIMARY_KEY).getValue());
		return function.test(row);
	}

	/**
	 * サイズを返す
	 *
	 * @param row
	 *     行
	 * @return サイズ
	 */
	protected String getSize(Row row) {
		String size = row.getCell(CellPositionType.COLUMN_SIZE).getValue();
		return StringUtil.isBrank(size) ? StringUtil.EMPTY : "(" + size + ")";
	}

	/**
	 * コメント形式のテーブル名を返す
	 *
	 * @param tableLogicalName
	 *     テーブル名
	 * @return コメント形式のテーブル名
	 */
	protected String getTableComment(String tableLogicalName) {
		return "-- " + tableLogicalName;
	}

	/**
	 * テーブル名が含まれるかどうかを返す
	 *
	 * @param tableList
	 *     テーブルリスト
	 * @param tblName
	 *     テーブル名
	 * @return テーブル名が含まれるかどうか
	 */
	protected boolean containsTable(List<String> tableList, String tblName) {
		return tableList.contains(tblName);
	}

}
