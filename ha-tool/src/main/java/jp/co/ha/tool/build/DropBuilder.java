package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.source.Table;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;
import jp.co.ha.tool.type.FileType;

public class DropBuilder extends CommonBuilder {

	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		List<Table> tableList = new ArrayList<>();
		List<String> tableNameList = new ArrayList<>();
		List<Row> rowList = excel.getRowList();
		rowList.remove(0);
		for (Row row : rowList) {
			String logicalName = row.getCell(CellPositionType.LOGICAL_NAME).getValue();
			String physicalName = row.getCell(CellPositionType.PHYSICAL_NAME).getValue();
			if (!containsTable(tableNameList, physicalName) && !"".equals(physicalName)) {
				tableNameList.add(physicalName);
				tableList.add(new Table(logicalName, physicalName));
			}
		}
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
