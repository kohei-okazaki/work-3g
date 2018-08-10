package jp.co.ha.tool.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.type.CellPositionType;

public class ExcelReader extends BaseFileReader {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private ExcelConfig conf;

	public ExcelReader(ExcelConfig conf) {
		this.conf = conf;
	}

	private Cell getCell(Row row, CellPositionType type) {
		String cellValue = row.getCell(type.getPosition()).getStringCellValue();
		return new Cell(cellValue);
	}

	public Excel read() {
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(getFilePath(conf.getFilePath()));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOG.error("excelファイル読込エラー", e);
			return null;
		}

		Excel excel = new Excel();
		Iterator<Sheet> sheetIte = workbook.sheetIterator();
		while (sheetIte.hasNext()) {
			jp.co.ha.tool.excel.Sheet excelSheet = new jp.co.ha.tool.excel.Sheet();
			Sheet sheet = sheetIte.next();
			excelSheet.setName(sheet.getSheetName());
			Iterator<Row> rowIte = sheet.iterator();
			while (rowIte.hasNext()) {
				jp.co.ha.tool.excel.Row excelRow = new jp.co.ha.tool.excel.Row();
				Row row = rowIte.next();
				Arrays.asList(CellPositionType.class.getEnumConstants()).stream().forEach(celltype -> {
					Cell cell = getCell(row, celltype);
					excelRow.addCell(cell);
				});
				excelSheet.addRow(excelRow);
			}
			excel.addSheet(excelSheet);
		}
		return excel;
	}

}
