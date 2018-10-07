package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.tool.type.CellPositionType;

public class Row {

	private List<Cell> cellList;

	public Row() {
		this.cellList = new ArrayList<>();
	}

	public void addCell(Cell cell) {
		this.cellList.add(cell);
	}

	public List<Cell> getCellList() {
		return cellList;
	}

	public Cell getCell(CellPositionType type) {
		return this.cellList.get(type.getPosition());
	}
}
