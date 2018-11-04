package jp.co.ha.web.file.excel.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.web.file.excel.model.HealthInfoExcelModel;

/**
 * 健康情報入力画面Excel生成クラス<br>
 *
 */
public class HealthInfoExcelBuilder extends BaseExcelBuilder<HealthInfoExcelModel> {

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *     Excel設定情報
	 * @param modelList
	 *     Excel出力モデルリスト
	 */
	public HealthInfoExcelBuilder(ExcelConfig conf, List<HealthInfoExcelModel> modelList) {
		super(conf, modelList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(Sheet sheet) {

		final int ROW_POSITION = 1;
		modelList.stream().forEach(model -> {
			Cell cell = getCell(sheet, ROW_POSITION, 0);
			setText(cell, model.getHeight());
			cell = getCell(sheet, ROW_POSITION, 1);
			setText(cell, model.getWeight());
			cell = getCell(sheet, ROW_POSITION, 2);
			setText(cell, model.getBmi());
			cell = getCell(sheet, ROW_POSITION, 3);
			setText(cell, model.getStandardWeight());
		});

	}

}
