package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * エクセル情報保持クラス
 *
 * @since 1.0
 */
public class Excel {

	/** シートリスト */
	private List<Sheet> sheetList = new ArrayList<>();
	/** アクティブなシート */
	private Sheet currentSheet;

	/**
	 * シートを返す
	 *
	 * @param sheetName
	 *     シート名
	 * @return Sheet
	 */
	private Sheet getSheet(String sheetName) {
		return this.sheetList.stream()
				.filter(e -> e.getName().equals(sheetName))
				.findFirst()
				.orElse(null);
	}

	/**
	 * シートを追加する
	 *
	 * @param sheet
	 *     シート
	 */
	public void addSheet(Sheet sheet) {
		this.sheetList.add(sheet);
	}

	/**
	 * シートをアクティブにする
	 *
	 * @param sheetName
	 *     シート名
	 */
	public void activeSheet(String sheetName) {
		this.currentSheet = getSheet(sheetName);
	}

	/**
	 * 現在のリストを全行返す
	 *
	 * @return 全行リスト
	 */
	public List<Row> getRowList() {
		return this.currentSheet.getRowList();
	}
}
