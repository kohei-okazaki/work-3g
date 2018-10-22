package jp.co.ha.tool.build;

import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.reader.ExcelReader;
import jp.co.ha.tool.type.CellPositionType;

public abstract class CommonBuilder extends BaseBuilder {

	protected ExcelReader reader;

	public CommonBuilder() {
		this.reader = new ExcelReader(getExcelConfig());
	}

	/**
	 * 対象のテーブルかどうか判定<br>
	 *
	 * @param row
	 *     excelの行情報
	 * @param tableName
	 *     テーブル名
	 * @return
	 */
	protected boolean isTargetTable(Row row, String tableName) {
		Cell cell = row.getCell(CellPositionType.PHYSICAL_NAME);
		return tableName.equals(cell.getValue());
	}

}
