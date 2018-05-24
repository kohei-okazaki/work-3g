package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
import jp.co.ha.web.file.csv.model.ReferenceCsvModel;
import jp.co.ha.web.file.csv.writer.ReferenceCsvWriter;

/**
 * 結果照会画面CSVダウンロードサービスクラス実装クラス<br>
 *
 */
@Service(value = "referenceCsv")
public class ReferenceCsvDownloadServiceImpl implements CsvDownloadService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		// 結果照会する健康情報を取得する
		String userId = sessionService.getValue(request, "userId", String.class);
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByUserId(userId);

		// CSV出力モデルリストに変換する
		List<ReferenceCsvModel> modelList = toModelList(healthInfoList);

		// CSV設定情報取得
		Account account = accountSearchService.findByUserId(userId);
		String fileName = ParamConst.CSV_FILE_NAME_REFERNCE_RESULT.getValue();
		CsvConfig conf = getCsvConfig(fileName, account);

		// CSVに書き込む
		BaseCsvWriter<ReferenceCsvModel> writer = new ReferenceCsvWriter(conf, modelList);
		writer.execute(response);
	}

	/**
	 * 結果照会CSVモデルリストに変換する
	 * @param healthInfoList List<HealthInfo>
	 * @return modelList
	 */
	private List<ReferenceCsvModel> toModelList(List<HealthInfo> healthInfoList) {

		List<ReferenceCsvModel> modelList = new ArrayList<ReferenceCsvModel>();
		Stream.iterate(0, i -> ++i).limit(healthInfoList.size()).forEach(i -> {
			ReferenceCsvModel model = new ReferenceCsvModel();
			HealthInfo healthInfo = healthInfoList.get(i);
			BeanUtils.copyProperties(healthInfo, model);
			modelList.add(model);
		});

		return modelList;
	}

}
