package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.web.file.excel.builder.ResultReferenceExcelBuiler;
import jp.co.ha.web.file.excel.model.ReferenceExcelModel;

/**
 * 結果照会画面Excelダウンロードサービス実装クラス
 *
 */
@Service(value = "referenceExcel")
public class ReferenceExcelDownloadServiceImpl implements ExcelDownloadService<List<HealthInfo>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View execute(List<HealthInfo> historyList) {

		List<ReferenceExcelModel> modelList = toModelList(historyList);

		return new ResultReferenceExcelBuiler(modelList);
	}

	/**
	 * 健康情報履歴リストをモデルリストに変換する<br>
	 * @param historyList 健康情報リスト履歴リスト
	 * @return modelList
	 */
	private List<ReferenceExcelModel> toModelList(List<HealthInfo> historyList) {

		List<ReferenceExcelModel> modelList = new ArrayList<ReferenceExcelModel>();
		Stream.iterate(0, i -> ++i).limit(historyList.size()).forEach(i -> {
			ReferenceExcelModel model = new ReferenceExcelModel();
			HealthInfo healthInfo = historyList.get(i);
			BeanUtils.copyProperties(healthInfo, model);
			modelList.add(model);
		});

		return modelList;
	}


}
