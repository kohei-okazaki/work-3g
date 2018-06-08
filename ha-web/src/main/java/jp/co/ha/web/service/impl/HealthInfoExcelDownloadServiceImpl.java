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
	 * 健康情報Entityをモデルに変換する<br>
	 *
	 * @param healthInfo
	 *            HealthInfo
	 * @param account
	 *            Account
	 * @return model HealthInfoExcelModel
	 */
	private HealthInfoExcelModel toModel(HealthInfo healthInfo, Account account) {

		HealthInfoExcelModel model = new HealthInfoExcelModel();

		boolean useMask = healthInfoFunctionService.useHealthInfoMask(account);
		model.setHeight(useMask ? "****" : healthInfo.getHeight().toString());
		model.setWeight(useMask ? "****" : healthInfo.getWeight().toString());
		model.setBmi(useMask ? "****" : healthInfo.getBmi().toString());
		model.setStandardWeight(useMask ? "****" : healthInfo.getStandardWeight().toString());

		return model;
	}

}
