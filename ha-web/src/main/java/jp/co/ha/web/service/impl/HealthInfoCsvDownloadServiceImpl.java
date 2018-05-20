package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.csv.writer.BaseCsvWriter;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.web.file.csv.model.HealthInfoCsvModel;
import jp.co.ha.web.file.csv.writer.HealthInfoCsvWriter;

/**
 * 健康情報CSVダウンロードサービス実装クラス<br>
 *
 */
@Service("healthInfoCsv")
public class HealthInfoCsvDownloadServiceImpl implements CsvDownloadService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** sessionサービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		// 最後に登録した健康情報を検索
		String userId = (String) sessionService.getValue(request, "userId");
		HealthInfo healthInfo = this.healthInfoSearchService.findLastByUserId(userId);

		// CSV出力モデルリストに変換する
		List<HealthInfoCsvModel> modelList = toModelList(healthInfo);

		// CSV設定情報取得
		Account account = accountSearchService.findByUserId(userId);
		String fileName = ParamConst.CSV_FILE_NAME_HEALTH_INFO.getValue();
		CsvConfig conf = getCsvConfig(fileName, account);

		// CSVに書き込む
		BaseCsvWriter<HealthInfoCsvModel> writer = new HealthInfoCsvWriter(conf, modelList);
		writer.execute(response);

	}

	/**
	 * CSVモデルリストに変換する<br>
	 * @param healthInfo
	 * @return modelList
	 */
	private List<HealthInfoCsvModel> toModelList(HealthInfo healthInfo) {

		List<HealthInfoCsvModel> modelList = new ArrayList<HealthInfoCsvModel>();
		HealthInfoCsvModel model = new HealthInfoCsvModel();
		BeanUtils.copyProperties(healthInfo, model);
		modelList.add(model);

		return modelList;
	}
}
