package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

public class Excel {

	private List<Sheet> sheetList;

	private Sheet currentSheet;

	public Excel() {
		this.sheetList = new ArrayList<>();
	}

	public List<Sheet> getSheetList() {
		return this.sheetList;
	}

	public Sheet getSheet(String sheetName) {
		return this.sheetList.stream()
					.filter(e -> e.getName().equals(sheetName))
					.findFirst()
					.orElse(null);
	}

	public void addSheet(Sheet sheet) {
		this.sheetList.add(sheet);
	}

	public void activeSheet(String sheetName) {
		this.currentSheet = getSheet(sheetName);
	}

	public List<Row> getRowList() {
		return this.currentSheet.getRowList();
	}
}
