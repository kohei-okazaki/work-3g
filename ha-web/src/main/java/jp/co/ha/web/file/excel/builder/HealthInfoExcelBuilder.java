package jp.co.ha.web.file.excel.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.file.excel.annotation.ExcelSheet;
import jp.co.ha.common.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.util.ExcelUtil;
import jp.co.ha.web.file.excel.model.HealthInfoExcelModel;

/**
 * 健康情報入力画面Excel生成クラス<br>
 *
 */
@ExcelSheet("健康情報")
public class HealthInfoExcelBuilder extends BaseExcelBuilder<HealthInfoExcelModel> {

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *            Excel設定情報
	 * @param modelList
	 *            Excel出力モデル
	 */
	public HealthInfoExcelBuilder(ExcelConfig conf, List<HealthInfoExcelModel> modelList) {
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

		final int ROW_POSITION = 1;
		modelList.stream().forEach(model -> {
			Cell cell = ExcelUtil.getCell(sheet, ROW_POSITION, 0);
			ExcelUtil.setText(cell, model.getHeight());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 1);
			ExcelUtil.setText(cell, model.getWeight());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 2);
			ExcelUtil.setText(cell, model.getBmi());
			cell = ExcelUtil.getCell(sheet, ROW_POSITION, 3);
			ExcelUtil.setText(cell, model.getStandardWeight());
		});

	}

}
