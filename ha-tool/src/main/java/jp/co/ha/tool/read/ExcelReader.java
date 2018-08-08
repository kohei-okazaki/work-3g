package jp.co.ha.tool.read;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.config.ExcelConfig;

public class ExcelReader extends BaseFileReader {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private ExcelConfig conf;
	private Workbook workbook;
	private Iterator<Row> iteratorRow;

	public ExcelReader(ExcelConfig conf) {
		this.conf = conf;
	}

	public void init() {
		try {
			this.workbook = WorkbookFactory.create(getFilePath(conf.getFilePath()));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOG.error("excelファイル読込エラー", e);
		}
	}

	public void read() {

		Sheet sheet = this.workbook.getSheet(this.conf.getSheetName());
		if (Objects.isNull(sheet)) {
			LOG.error("指定したシートが存在しません");
			return;
		}
		this.iteratorRow = sheet.rowIterator();
	}

	public Row readRow() {
		return this.iteratorRow.next();
	}

	public boolean hasRow() {
		return this.iteratorRow.hasNext();
	}


}
