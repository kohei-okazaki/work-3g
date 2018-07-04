package jp.co.ha.web.file.excel.builder;

import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.web.file.excel.model.ReferenceExcelModel;

/**
 * 結果照会画面Excel生成クラス<br>
 *
 */
public class ResultReferenceExcelBuiler extends BaseExcelBuilder<ReferenceExcelModel> {

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *            Excel設定情報
	 * @param modelList
	 *            Excel出力モデルリスト
	 */
	public ResultReferenceExcelBuiler(ExcelConfig conf, List<ReferenceExcelModel> modelList) {
		super(conf, modelList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(Sheet sheet) {

		Stream.iterate(0, i -> ++i).limit(this.modelList.size()).forEach(i -> {
			ReferenceExcelModel model = modelList.get(i);
			final int ROW_POSITION = i + 1;
			Cell cell = getCell(sheet, ROW_POSITION, 0);
			setText(cell, model.getHeight().toString());
			cell = getCell(sheet, ROW_POSITION, 1);
			setText(cell, model.getWeight().toString());
			cell = getCell(sheet, ROW_POSITION, 2);
			setText(cell, model.getBmi().toString());
			cell = getCell(sheet, ROW_POSITION, 3);
			setText(cell, model.getStandardWeight().toString());
			cell = getCell(sheet, ROW_POSITION, 4);
			setText(cell, DateUtil.toString(model.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
		});
	}
}
