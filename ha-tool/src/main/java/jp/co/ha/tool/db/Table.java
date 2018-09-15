package jp.co.ha.tool.db;

import java.util.ArrayList;
import java.util.List;

public class Table {

	/** 論理名 */
	private String logicalName;
	/** 物理名 */
	private String physicalName;
	/** カラムリスト */
	private List<Column> columnList;

	public Table() {
		this.columnList = new ArrayList<>();
	}

	public String getLogicalName() {
		return logicalName;
	}

	public String getPhysicalName() {
		return physicalName;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}



}
