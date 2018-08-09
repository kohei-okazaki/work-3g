package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

public class Sheet {

	private String name;
	private List<Row> rowList;

	public Sheet() {
		this.rowList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Row> getRowList() {
		return rowList;
	}

	public void setRowList(List<Row> rowList) {
		this.rowList = rowList;
	}

	public void addRow(Row row) {
		this.rowList.add(row);
	}

}
