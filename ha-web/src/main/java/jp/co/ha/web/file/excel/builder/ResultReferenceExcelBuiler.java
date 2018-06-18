package jp.co.ha.web.file.excel.builder;

import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.file.excel.annotation.ExcelSheet;
import jp.co.ha.common.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.ExcelUtil;
import jp.co.ha.web.file.excel.model.ReferenceExcelModel;

/**
 * 結果照会画面Excel生成クラス<br>
 *
 */
@ExcelSheet("健康情報")
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
	protected String getSheetName() {
		return ExcelUtil.getSheetName(this.getClass());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(Sheet sheet) {

		Stream.iterate(0, i -> ++i).limit(this.modelList.size()).forEach(i -> {
			ReferenceExcelModel model = modelList.get(i);
			final int ROW_POSITION = i + 1;
			Cell cell = ExcelUtil.getCell(sheet, ROW_POSITION, 0);
			ExcelUtil.setText(cell, model.getHeight().toString());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 1);
			ExcelUtil.setText(cell, model.getWeight().toString());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 2);
			ExcelUtil.setText(cell, model.getBmi().toString());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 3);
			ExcelUtil.setText(cell, model.getStandardWeight().toString());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 4);
			ExcelUtil.setText(cell, DateUtil.toString(model.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
		});
	}
}
