package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.tool.type.CellPositionType;

/**
 * エクセル行情報保持クラス
 *
 */
public class Row {

	/** セルリスト */
	private List<Cell> cellList;

	/**
	 * コンストラクタ
	 */
	public Row() {
		this.cellList = new ArrayList<>();
	}

	/**
	 * セルを追加する
	 *
	 * @param cell
	 *     セル
	 */
	public void addCell(Cell cell) {
		this.cellList.add(cell);
	}

	/**
	 * セルリストを返す
	 *
	 * @return cellList
	 */
	public List<Cell> getCellList() {
		return cellList;
	}

	/**
	 * セルリストから指定されたセル位置に対応するセルを返す
	 *
	 * @param type
	 *     セル位置
	 * @return セル
	 */
	public Cell getCell(CellPositionType type) {
		return this.cellList.get(type.getPosition());
	}
}
