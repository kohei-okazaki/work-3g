package jp.co.ha.common.util;

import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.file.excel.annotation.ExcelHeader;
import jp.co.ha.common.file.excel.annotation.ExcelSheet;

/**
 * Excel操作のUtilクラス<br>
 * インスタンスの生成を制限<br>
 */
public class ExcelUtil {

	private ExcelUtil() {
	}

	/**
	 * 指定されたシートのrow行目のcol番目のセルを返す<br>
	 *
	 * @param sheet
	 *            Sheet
	 * @param row
	 *            行数
	 * @param col
	 *            カラム位置
	 * @return cell
	 */
	public static Cell getCell(Sheet sheet, int row, int col) {

		// row取得
		Row sheetRow = sheet.getRow(row);
		if (Objects.isNull(sheetRow)) {
			sheetRow = sheet.createRow(row);
		}

		// cell取得
		Cell cell = sheetRow.getCell(col);
		if (Objects.isNull(cell)) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	/**
	 * 指定されたセルにtextを設定する<br>
	 *
	 * @param cell
	 *            Cell
	 * @param text
	 *            テキスト
	 */
	public static void setText(Cell cell, String text) {
		cell.setCellType(CellType.STRING);
		cell.setCellValue(text);
	}

	/**
	 * シート名を返す。<br>
	 *
	 * @param clazz
	 *            シートアノテーションのついたクラス型
	 * @return シート名
	 */
	public static String getSheetName(Class<?> clazz) {
		return getExcelSheetClass(clazz).value();
	}

	/**
	 * ヘッダ名のリストを返す<br>
	 *
	 * @param clazz
	 *            シートアノテーションのついたクラス型
	 * @return ヘッダー名
	 */
	public static List<String> getHeaderList(Class<?> clazz) {
		return List.of(getExcelHeaderClass(clazz).names());
	}

	/**
	 * 指定されたクラス型についてるExcelSheetアノテーションを返す<br>
	 *
	 * @param clazz
	 *            ExcelHeaderアノテーションのついたクラス型
	 * @return Excelheader
	 */
	public static ExcelSheet getExcelSheetClass(Class<?> clazz) {
		return clazz.getAnnotation(ExcelSheet.class);
	}

	/**
	 * 指定されたクラス型についてるExcelHeaderアノテーションを返す<br>
	 *
	 * @param clazz
	 *            ExcelHeaderアノテーションのついたクラス型
	 * @return
	 */
	public static ExcelHeader getExcelHeaderClass(Class<?> clazz) {
		return clazz.getAnnotation(ExcelHeader.class);
	}

}
