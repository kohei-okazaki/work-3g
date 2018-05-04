package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.common.entity.Account;
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

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報利用機能サービス */
	@Autowired
	private HealthInfoFunctionService healthInfoFunctionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View execute(List<HealthInfo> historyList) {

		// 健康情報Entityからアカウントを検索
		Account account = accountSearchService.findByUserId(historyList.get(0).getUserId());

		List<ReferenceExcelModel> modelList = toModelList(historyList, account);

		return new ResultReferenceExcelBuiler(modelList);
	}

	/**
	 * 健康情報履歴リストをモデルリストに変換する<br>
	 * @param historyList 健康情報リスト履歴リスト
	 * @param account
	 * @return modelList
	 */
	private List<ReferenceExcelModel> toModelList(List<HealthInfo> historyList, Account account) {

		// 健康情報マスク利用有無
		boolean useHealthInfoMask = healthInfoFunctionService.useHealthInfoMask(account);
		List<ReferenceExcelModel> modelList = new ArrayList<ReferenceExcelModel>();
		Stream.iterate(0, i -> ++i).limit(historyList.size()).forEach(i -> {

			// 結果照会Excel出力モデル
			ReferenceExcelModel model = new ReferenceExcelModel();
			if (useHealthInfoMask) {
				// 健康情報マスク表示を利用する場合
				model.setHeight("****");
				model.setWeight("****");
				model.setBmi("****");
				model.setStandardWeight("****");
			} else {
				HealthInfo healthInfo = historyList.get(i);
				model.setHeight(healthInfo.getHeight().toString());
				model.setWeight(healthInfo.getWeight().toString());
				model.setBmi(healthInfo.getBmi().toString());
				model.setStandardWeight(healthInfo.getStandardWeight().toString());
			}

			modelList.add(model);
		});

		return modelList;
	}


}
