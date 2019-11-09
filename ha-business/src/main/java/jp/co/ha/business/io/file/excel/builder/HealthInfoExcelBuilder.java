package jp.co.ha.business.io.file.excel.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.log.MaskExecutor;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報Excel生成クラス
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
		for (int i = 0; i < this.modelList.size(); i++) {
			HealthInfoExcelModel model = modelList.get(i);
			Cell cell = getCell(sheet, rowPosition + i, 0);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getHeight());
			cell = getCell(sheet, rowPosition + i, 1);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getWeight());
			cell = getCell(sheet, rowPosition + i, 2);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getBmi());
			cell = getCell(sheet, rowPosition + i, 3);
			setText(cell, this.conf.useMask() ? MaskExecutor.MASK : model.getStandardWeight());
			cell = getCell(sheet, rowPosition + i, 4);
			setText(cell, DateUtil.toString(model.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
		}
	}

}
