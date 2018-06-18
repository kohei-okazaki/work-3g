package jp.co.ha.common.file.excel.builder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.file.excel.annotation.ExcelSheet;
import jp.co.ha.common.file.excel.model.BaseExcelModel;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.ExcelUtil;

/**
 * Excel出力の基底クラス<br>
 *
 * @param <M>
 *            Excelモデル
 */
public abstract class BaseExcelBuilder<M extends BaseExcelModel> extends AbstractXlsxView {

	/** ヘッダー位置 */
	protected final int HEADER_POSITION = 0;
	/** Excelモデルインターフェース継承クラス */
	protected List<M> modelList;
	/** Excel設定情報保持クラス */
	protected ExcelConfig conf;

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *            Excel設定情報
	 * @param modelList
	 *            Excel出力モデルリスト
	 */
	public BaseExcelBuilder(ExcelConfig conf, List<M> modelList) {
		this.conf = conf;
		this.modelList = modelList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		init(response);
		Sheet sheet = workbook.createSheet(getSheetName());

		// ヘッダーを書き込む
		writeHeader(sheet, (Class<M>) BeanUtil.getParameterType(this.getClass()));

		// データを書き込む
		writeData(sheet);
	}

	/**
	 * 初期処理<br>
	 *
	 * @param response
	 *            HttpServletResponse
	 * @throws UnsupportedEncodingException
	 *             Encoding例外
	 */
	private void init(HttpServletResponse response) throws UnsupportedEncodingException {

		String fileName = new String("sample.xlsx".getBytes(conf.getCharset().getName()), "ISO-8859-1");
		response.setHeader("Content-Desposition", "attachment; filename=" + fileName);
	}

	/**
	 * 継承先の@ExcelSheetからシート名を取得<br>
	 * {@link ExcelSheet}
	 *
	 * @return シート名
	 */
	protected abstract String getSheetName();

	/**
	 * ヘッダーを設定する<br>
	 *
	 * @param sheet
	 *            Sheet
	 * @param clazz
	 *            Excelモデルインターフェースクラス型
	 */
	protected void writeHeader(Sheet sheet, Class<M> clazz) {
		// ヘッダー名取得
		List<String> headerNameList = ExcelUtil.getHeaderList(clazz);

		Stream.iterate(0, i -> ++i).limit(headerNameList.size()).forEach(i -> {
			String headerName = headerNameList.get(i);
			Cell cell = ExcelUtil.getCell(sheet, HEADER_POSITION, i);
			ExcelUtil.setText(cell, headerName);
		});
	}

	/**
	 * データを設定する<br>
	 *
	 * @param sheet
	 *            Sheet
	 */
	protected abstract void writeData(Sheet sheet);
}
