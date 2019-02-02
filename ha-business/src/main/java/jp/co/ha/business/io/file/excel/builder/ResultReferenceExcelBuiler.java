package jp.co.ha.business.io.file.excel.builder;

import java.util.List;
import java.util.stream.Stream;

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
		Stream.iterate(0, i -> ++i).limit(this.modelList.size()).forEach(i -> {
			ReferenceExcelModel model = modelList.get(i);
			var rowPosition = i++;
			Cell cell = getCell(sheet, rowPosition, 0);
			setText(cell, model.getHeight().toString());
			cell = getCell(sheet, rowPosition, 1);
			setText(cell, model.getWeight().toString());
			cell = getCell(sheet, rowPosition, 2);
			setText(cell, model.getBmi().toString());
			cell = getCell(sheet, rowPosition, 3);
			setText(cell, model.getStandardWeight().toString());
			cell = getCell(sheet, rowPosition, 4);
			setText(cell, DateUtil.toString(model.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
		});
	}
}
