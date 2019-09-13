package jp.co.ha.tool.build;

import java.util.StringJoiner;
import java.util.function.Predicate;

import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.type.CellPositionType;

/**
 * SQLのビルダー
 *
 */
public abstract class BaseSqlSourceBuilder extends BaseBuilder {

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

	protected boolean isSequence(Row row) {
		Predicate<Row> function = e -> CommonFlag.TRUE.is(e.getCell(CellPositionType.SEQUENCE).getValue());
		return function.test(row);
	}

	protected boolean isPrimaryKey(Row row) {
		Predicate<Row> function = e -> CommonFlag.TRUE.is(e.getCell(CellPositionType.PRIMARY_KEY).getValue());
		return function.test(row);
	}

	protected String getSize(Row row) {
		String size = row.getCell(CellPositionType.COLUMN_SIZE).getValue();
		return StringUtil.isBrank(size) ? StringUtil.EMPTY : "(" + size + ")";
	}

	protected String getTableComment(String tableLogicalName) {
		return "-- " + tableLogicalName;
	}

}
