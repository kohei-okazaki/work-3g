package jp.co.ha.common.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.common.entity.Code;
import jp.co.ha.common.other.OsDefine;
import jp.co.ha.common.util.StringUtil;

public class CodeManager {

	/** singletonパターン */
	private static CodeManager instance = new CodeManager();
	/** コードエクセルファイル */
	private static final String CODE_EXCEL = OsDefine.isWin()
			? "C:\\work\\pleiades\\workspace\\isol-3g\\app-common\\src\\main\\resources\\META-INF\\codeParameter.xlsx"
			: "/Applications/Eclipse_4.7.2.app/Contents/workspace/isol-3g/app-common/src/main/resources/META-INF/codeParameter.xlsx";
	/** シート名 */
	private static final String SHEET_NAME = "CODE";

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private CodeManager() {

	}

	/**
	 * CodeManagerインスタンスを取得する<br>
	 * @return
	 */
	public static final CodeManager getInstance() {
		return instance;
	}

	/**
	 * メインキーとサブキーにヒモづくvalueを返す<br>
	 * 指定されたキーがnullの場合、空文字を返す<br>
	 * @param mainKey メインキー
	 * @param subKey サブキー
	 * @return value
	 */
	public String getValue(MainKey mainKey, SubKey subKey) {

		String value = StringUtil.EMPTY;

		if (Objects.isNull(mainKey) || Objects.isNull(subKey)) {
			return value;
		}

		try {
			Iterator<Row> rowIterator  = getRowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (Objects.isNull(row) || Objects.isNull(row.getCell(0))) {
					break;
				}
				String cellMainKey = row.getCell(0).getStringCellValue();
				String cellSubKey = row.getCell(1).getStringCellValue();

				if (cellMainKey.equals(mainKey.name()) && cellSubKey.equals(subKey.name())) {
					value = row.getCell(2).getStringCellValue();
				}
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 指定したメインキーに該当するvalueのリストを返す<br>
	 * @param mainKey
	 * @return valueList
	 */
	public List<String> getValues(MainKey mainKey) {

		if (Objects.isNull(mainKey)) {
			return null;
		}
		List<String> valueList = new ArrayList<String>();
		try {
			Iterator<Row> rowIterator  = getRowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String cellMainKey = row.getCell(0).getStringCellValue();

				if (cellMainKey.equals(mainKey.name())) {
					valueList.add(row.getCell(2).getStringCellValue());
				}
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valueList;
	}

	/**
	 * メインキーに該当する定数クラスのリストを返す<br>
	 * @param mainKey メインキー
	 * @return
	 */
	public List<Code> getList(MainKey mainKey) {

		if (Objects.isNull(mainKey)) {
			return null;
		}

		List<Code> codeList = new ArrayList<Code>();
		try {
			Iterator<Row> rowIterator  = getRowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String cellMainKey = row.getCell(0).getStringCellValue();
				String cellSubKey = row.getCell(1).getStringCellValue();

				if (cellMainKey.equals(mainKey.name())) {
					Code code = new Code();
					code.setMainKey(cellMainKey);
					code.setSubKey(cellSubKey);
					code.setValue(row.getCell(2).getStringCellValue());
					codeList.add(code);
				}
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codeList;
	}

	/**
	 * コードエクセル内の行をすべてIteratorで返す<br>
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public Iterator<Row> getRowIterator() throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(new File(CODE_EXCEL));
		Sheet sheet = workbook.getSheet(SHEET_NAME);
		return sheet.rowIterator();
	}

	/**
	 * 指定された値がメインキー、サブキーから取得される値と一致するか判定する<br>
	 * 一致した場合true, そうでなければfalseを返す<br>
	 * @param mainKey
	 * @param subKey
	 * @param target
	 * @return 判定結果
	 */
	public final boolean isEquals(MainKey mainKey, SubKey subKey, String target) {

		if (StringUtil.isEmpty(target)) {
			return false;
		}

		return target.equals(getValue(mainKey, subKey));

	}

}
