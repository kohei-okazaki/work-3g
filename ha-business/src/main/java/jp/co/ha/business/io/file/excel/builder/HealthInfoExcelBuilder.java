package jp.co.ha.business.io.file.excel.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.log.MaskExecutor;

/**
 * 健康情報入力画面Excel生成クラス
 * 
 * @since 1.0
 */
public class HealthInfoExcelBuilder extends BaseExcelBuilder<HealthInfoExcelModel> {

	/**
	 * コンストラクタ
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
		int rowPosition = this.conf.hasHeader() ? 1 : 0;
		this.modelList.stream().forEach(model -> {
			Cell cell = getCell(sheet, rowPosition, 0);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getHeight());
			cell = getCell(sheet, rowPosition, 1);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getWeight());
			cell = getCell(sheet, rowPosition, 2);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getBmi());
			cell = getCell(sheet, rowPosition, 3);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getStandardWeight());
		});
	}

}
