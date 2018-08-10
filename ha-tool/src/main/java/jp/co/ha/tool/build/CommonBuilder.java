package jp.co.ha.tool.build;

import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.type.CellPositionType;

public abstract class CommonBuilder extends BaseBuilder {

	protected boolean isTargetTable(Row row, String table) {
		Cell cell = row.getCell(CellPositionType.TABLE_NAME);
		return table.equals(cell.getValue());
	}

}
