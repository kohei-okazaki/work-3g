package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.util.Charset;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.web.file.excel.builder.ResultReferenceExcelBuiler;
import jp.co.ha.web.file.excel.model.ReferenceExcelModel;

/**
 * 結果照会画面Excelダウンロードサービス実装クラス
 *
 */
@Service(value = "referenceExcel")
public class HealthInfoReferExcelDownloadServiceImpl implements ExcelDownloadService<List<HealthInfoRegistResponse>> {

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
	public View execute(List<HealthInfoRegistResponse> historyList) {

		// 健康情報Entityからアカウントを検索
		Account account = accountSearchService.findByUserId(historyList.get(0).getUserId());

		List<ReferenceExcelModel> modelList = toModelList(historyList, account);

		return new ResultReferenceExcelBuiler(getExcelConfig(), modelList);
	}

	/**
	 * 健康情報履歴リストをモデルリストに変換する<br>
	 *
	 * @param historyList
	 *            List<HealthInfoRegistResponse> 健康情報リスト履歴リスト
	 * @param account
	 *            アカウント情報
	 * @return modelList
	 */
	private List<ReferenceExcelModel> toModelList(List<HealthInfoRegistResponse> historyList, Account account) {

		// 健康情報マスク利用有無
		boolean useHealthInfoMask = healthInfoFunctionService.useHealthInfoMask(account);
		List<ReferenceExcelModel> modelList = new ArrayList<ReferenceExcelModel>();
		Stream.iterate(0, i -> ++i).limit(historyList.size()).forEach(i -> {

			// 結果照会Excel出力モデル
			ReferenceExcelModel model = new ReferenceExcelModel();
			HealthInfoRegistResponse healthInfo = historyList.get(i);
			model.setHeight(useHealthInfoMask ? "****" : healthInfo.getHeight().toString());
			model.setWeight(useHealthInfoMask ? "****" : healthInfo.getWeight().toString());
			model.setBmi(useHealthInfoMask ? "****" : healthInfo.getBmi().toString());
			model.setStandardWeight(useHealthInfoMask ? "****" : healthInfo.getStandardWeight().toString());
			model.setRegDate(DateUtil.toDate(healthInfo.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

			modelList.add(model);
		});

		return modelList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExcelConfig getExcelConfig() {
		ExcelConfig conf = new ExcelConfig();
		conf.setCharset(Charset.MS_932);
		return conf;
	}

}
