package jp.co.ha.common.io.file.excel.builder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.annotation.ExcelDownloadModel;
import jp.co.ha.common.io.file.excel.model.BaseExcelModel;
import jp.co.ha.common.util.BeanUtil;

/**
 * Excel出力の基底クラス<br>
 *
 * @param <T>
 *     Excel出力モデル
 */
public abstract class BaseExcelBuilder<T extends BaseExcelModel> extends AbstractXlsxView {

	/** ヘッダー位置 */
	protected final int HEADER_POSITION = 0;
	/** Excelモデルリスト */
	protected List<T> modelList;
	/** Excel設定情報 */
	protected ExcelConfig conf;

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *     Excel設定情報
	 * @param modelList
	 *     Excel出力モデルリスト
	 */
	public BaseExcelBuilder(ExcelConfig conf, List<T> modelList) {
		this.conf = conf;
		this.modelList = modelList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		init(response);
		String sheetName = getSheetName((Class<T>) BeanUtil.getParameterType(this.getClass()));
		Sheet sheet = workbook.createSheet(sheetName);

		if (this.conf.hasHeader()) {
			// ヘッダを書込
			writeHeader(sheet, (Class<T>) BeanUtil.getParameterType(this.getClass()));
		}

		// データを書込
		writeData(sheet);

		if (this.conf.hasFooter()) {
			// フッタを書込
			writeFooter(sheet, (Class<T>) BeanUtil.getParameterType(this.getClass()));
		}

	}

	/**
	 * 初期処理<br>
	 *
	 * @param response
	 *     HttpServletResponse
	 * @throws UnsupportedEncodingException
	 *     Encoding例外
	 */
	private void init(HttpServletResponse response) throws UnsupportedEncodingException {

		String fileName = new String("sample.xlsx".getBytes(conf.getCharsetType().getName()), "ISO-8859-1");
		response.setHeader("Content-Desposition", "attachment; filename=" + fileName);
	}

	/**
	 * ヘッダを書込<br>
	 *
	 * @param sheet
	 *     Sheet
	 * @param clazz
	 *     Excelモデルインターフェースクラス型
	 */
	protected void writeHeader(Sheet sheet, Class<T> clazz) {
		// ヘッダ名取得
		List<String> header = getHeaderList(clazz);
		Stream.iterate(0, i -> ++i).limit(header.size()).forEach(i -> {
			String headerName = header.get(i);
			Cell cell = getCell(sheet, HEADER_POSITION, i);
			setText(cell, headerName);
		});
	}

	/**
	 * データを書込<br>
	 *
	 * @param sheet
	 *     Sheet
	 */
	protected abstract void writeData(Sheet sheet);

	/**
	 * フッタを書込
	 *
	 * @param sheet
	 *     Sheet
	 * @param clazz
	 *     Excelモデルインターフェースクラス型
	 */
	private void writeFooter(Sheet sheet, Class<T> clazz) {
		// フッタ名取得
		List<String> footer = getHeaderList(clazz);
		Stream.iterate(0, i -> ++i).limit(footer.size()).forEach(i -> {
			String footerName = footer.get(i);
			Cell cell = getCell(sheet, HEADER_POSITION, i);
			setText(cell, footerName);
		});
	}

	/**
	 * 指定されたシートのrow行目のcol番目のセルを返す<br>
	 *
	 * @param sheet
	 *     Sheet
	 * @param row
	 *     行数
	 * @param col
	 *     カラム位置
	 * @return cell
	 */
	protected Cell getCell(Sheet sheet, int row, int col) {

		// row取得
		Row sheetRow = sheet.getRow(row);
		if (BeanUtil.isNull(sheetRow)) {
			sheetRow = sheet.createRow(row);
		}

		// cell取得
		Cell cell = sheetRow.getCell(col);
		if (BeanUtil.isNull(cell)) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	/**
	 * 指定されたセルにtextを設定する<br>
	 *
	 * @param cell
	 *     Cell
	 * @param text
	 *     テキスト
	 */
	protected void setText(Cell cell, String text) {
		cell.setCellType(CellType.STRING);
		cell.setCellValue(text);
	}

	/**
	 * シート名を取得する<br>
	 *
	 * @param clazz
	 *     ExcelDownloadModelアノテーションのついたクラス型
	 * @return
	 */
	protected String getSheetName(Class<T> clazz) {
		return clazz.getAnnotation(ExcelDownloadModel.class).sheetName();
	}

	/**
	 * ヘッダ名を取得する<br>
	 *
	 * @param clazz
	 *     ExcelDownloadModelアノテーションのついたクラス型
	 * @return
	 */
	protected List<String> getHeaderList(Class<?> clazz) {
		List<String> headerList = new ArrayList<String>();
		headerList.addAll(List.of(clazz.getAnnotation(ExcelDownloadModel.class).headerNames()));
		return headerList;
	}

	/**
	 * フッタ名を取得する<br>
	 *
	 * @param clazz
	 *     ExcelDownloadModelアノテーションのついたクラス型
	 * @return
	 */
	protected List<String> getFooterList(Class<?> clazz) {
		List<String> footerList = new ArrayList<String>();
		footerList.addAll(List.of(clazz.getAnnotation(ExcelDownloadModel.class).footerNames()));
		return footerList;
	}
}
