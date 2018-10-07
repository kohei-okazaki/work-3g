package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;
import jp.co.ha.tool.type.FileType;

public class DropBuilder extends CommonBuilder {

	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		List<Table> tableList = getTableList(excel.getRowList());
		StringJoiner body = new StringJoiner("\r\n");
		tableList.stream().forEach(e -> {
			body.add(buildComment(e.getLogicalName()));
			body.add(buildDropSql(e.getPhysicalName()));
		});
		FileConfig fileConf = getFileConfig(ExecuteType.DROP);
		fileConf.setFileName("DROP"  + FileType.SQL.getSuffix());
		fileConf.setData(body.toString());
		FileFactory.create(fileConf);
	}

	private List<Table> getTableList(List<Row> rowList) {
		List<Table> tableList = new ArrayList<>();
		// header行を除外
		rowList.remove(0);
		List<String> existTableList = new ArrayList<>();
		for (Row row : rowList) {
			String logicalName = row.getCell(CellPositionType.LOGICAL_NAME).getValue();
			String physicalName = row.getCell(CellPositionType.PHYSICAL_NAME).getValue();
			if (!containsTable(existTableList, physicalName) && !"".equals(physicalName)) {
				existTableList.add(physicalName);
				Table table = new Table();
				table.setLogicalName(logicalName);
				table.setPhysicalName(physicalName);
				tableList.add(table);
			}
		}
		return tableList;
	}

	private boolean containsTable(List<String> tableList, String tblName) {
		return tableList.contains(tblName);
	}

	private String buildDropSql(String physicalName) {
		String prefix = "DROP TABLE ";
		String suffix = ";";
		return prefix + physicalName + suffix;
	}

	private String buildComment(String logicalName) {
		String prefix = "-- ";
		return prefix + logicalName;
	}

}
