package jp.co.ha.web.file.excel.builder;

import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.common.file.excel.annotation.ExcelSheet;
import jp.co.ha.common.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.util.ExcelUtil;
import jp.co.ha.web.file.excel.model.HealthInfoExcelModel;

/**
 * 健康情報入力画面Excel生成クラス<br>
 *
 */
@ExcelSheet("健康情報")
public class HealthInfoExcelBuilder extends BaseExcelBuilder {

	/** 健康情報モデル */
	private HealthInfoExcelModel model;

	/**
	 * コンストラクタ<br>
	 *
	 * @param model
	 *            HealthInfoExcelModel
	 */
	public HealthInfoExcelBuilder(HealthInfoExcelModel model) {
		this.model = model;
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
	protected void writeHeader(Sheet sheet) {

		List<String> headerNameList = ExcelUtil.getHeaderList(HealthInfoExcelModel.class);

		Stream.iterate(0, i -> ++i).limit(headerNameList.size()).forEach(i -> {
			String headerName = headerNameList.get(i);
			Cell cell = ExcelUtil.getCell(sheet, HEADER_POSITION, i);
			ExcelUtil.setText(cell, headerName);
		});

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(Sheet sheet) {

		final int ROW_POSITION = 1;
		Cell cell = ExcelUtil.getCell(sheet, ROW_POSITION, 0);
		ExcelUtil.setText(cell, model.getHeight());
		cell = ExcelUtil.getCell(sheet, ROW_POSITION, 1);
		ExcelUtil.setText(cell, model.getWeight());
		cell = ExcelUtil.getCell(sheet, ROW_POSITION, 2);
		ExcelUtil.setText(cell, model.getBmi());
		cell = ExcelUtil.getCell(sheet, ROW_POSITION, 3);
		ExcelUtil.setText(cell, model.getStandardWeight());

	}

}
