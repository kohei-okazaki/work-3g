package jp.co.ha.business.io.file.excel.builder;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.ha.business.io.file.excel.model.ReferenceExcelModel;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.builder.BaseExcelBuilder;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;

/**
 * 結果照会画面Excel生成クラス
 *
 */
public class ResultReferenceExcelBuiler extends BaseExcelBuilder<ReferenceExcelModel> {

	/**
	 * コンストラクタ
	 *
	 * @param conf
	 *     Excel設定情報
	 * @param modelList
	 *     Excel出力モデルリスト
	 */
	public ResultReferenceExcelBuiler(ExcelConfig conf, List<ReferenceExcelModel> modelList) {
		super(conf, modelList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(Sheet sheet) {
		var rowPosition = this.conf.hasHeader() ? 1 : 0;
		for (int i = 0; i < this.modelList.size(); i++) {
			ReferenceExcelModel model = modelList.get(i);
			Cell cell = getCell(sheet, rowPosition + i, 0);
			setText(cell, model.getHeight().toString());
			cell = getCell(sheet, rowPosition + i, 1);
			setText(cell, model.getWeight().toString());
			cell = getCell(sheet, rowPosition + i, 2);
			setText(cell, model.getBmi().toString());
			cell = getCell(sheet, rowPosition + i, 3);
			setText(cell, model.getStandardWeight().toString());
			cell = getCell(sheet, rowPosition + i, 4);
			setText(cell, DateUtil.toString(model.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
		}
	}
}
