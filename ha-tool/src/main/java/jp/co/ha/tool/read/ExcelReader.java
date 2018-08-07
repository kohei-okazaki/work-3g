package jp.co.ha.tool.read;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader extends BaseFileReader {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public Workbook getWorkBook() {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(getFilePath("META-INF/DB.xlsx"));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOG.error("excelファイル読込エラー", e);
		}
		return workbook;
	}
}
