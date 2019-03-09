package jp.co.ha.tool.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.type.CellPositionType;

/**
 * エクセル読込クラス
 *
 */
public class ExcelReader extends BaseFileReader {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(ExcelReader.class);

	private ExcelConfig conf;

	/**
	 * コンストラクタ
	 *
	 * @param conf
	 *     Excel設定情報
	 */
	public ExcelReader(ExcelConfig conf) {
		this.conf = conf;
	}

	/**
	 * 読み込みを行う
	 *
	 * @return Excel
	 */
	public Excel read() {

		Iterator<Sheet> sheetIte;
		try (Workbook wb = WorkbookFactory.create(getFile(conf.getFilePath()))) {
			sheetIte = wb.sheetIterator();
		} catch (EncryptedDocumentException | IOException e) {
			LOG.error("excelファイル読込エラー", e);
			return null;
		}

		Excel excel = new Excel();
		while (sheetIte.hasNext()) {
			jp.co.ha.tool.excel.Sheet excelSheet = new jp.co.ha.tool.excel.Sheet();
			Sheet sheet = sheetIte.next();
			excelSheet.setName(sheet.getSheetName());
			Iterator<Row> rowIte = sheet.iterator();
			while (rowIte.hasNext()) {
				jp.co.ha.tool.excel.Row excelRow = new jp.co.ha.tool.excel.Row();
				Row row = rowIte.next();
				Arrays.asList(CellPositionType.class.getEnumConstants()).stream().forEach(e -> {
					Cell cell = getCell(row, e);
					excelRow.addCell(cell);
				});
				excelSheet.addRow(excelRow);
			}
			excel.addSheet(excelSheet);
		}
		return excel;
	}

	private Cell getCell(Row row, CellPositionType type) {
		String cellValue = row.getCell(type.getPosition()).getStringCellValue();
		return new Cell(cellValue);
	}

}
