package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.web.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.web.file.excel.model.HealthInfoExcelModel;

/**
 * 健康情報Excelダウンロードサービス実装クラス
 *
 */
@Service(value = "healthInfoExcel")
public class HealthInfoExcelDownloadServiceImpl implements ExcelDownloadService<HealthInfo> {

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
	public View execute(HealthInfo healthInfo) {

		// 健康情報Entityからアカウントを検索
		Account account = accountSearchService.findByUserId(healthInfo.getUserId());

		HealthInfoExcelModel model = toModel(healthInfo, account);

		return new HealthInfoExcelBuilder(model);
	}

	/**
	 * 健康情報フォームをモデルに変換する<br>
	 * @param form
	 * @param account
	 * @return model
	 */
	private HealthInfoExcelModel toModel(HealthInfo healthInfo, Account account) {

		HealthInfoExcelModel model = new HealthInfoExcelModel();

		if (healthInfoFunctionService.useHealthInfoMask(account)) {
			// 健康情報マスク表示を利用する場合
			model.setHeight("****");
			model.setWeight("****");
			model.setBmi("****");
			model.setStandardWeight("****");
		} else {
			model.setHeight(healthInfo.getHeight().toString());
			model.setWeight(healthInfo.getWeight().toString());
			model.setBmi(healthInfo.getBmi().toString());
			model.setStandardWeight(healthInfo.getStandardWeight().toString());
		}

		return model;

	}

}
