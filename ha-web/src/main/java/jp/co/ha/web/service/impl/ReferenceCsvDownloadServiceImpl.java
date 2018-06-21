package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.csv.writer.BaseCsvWriter;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.web.file.csv.model.ReferenceCsvModel;
import jp.co.ha.web.file.csv.writer.ReferenceCsvWriter;

/**
 * 結果照会画面CSVダウンロードサービスクラス実装クラス<br>
 *
 */
@Service(value = "referenceCsv")
public class ReferenceCsvDownloadServiceImpl implements CsvDownloadService {

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

		// sessionから検索結果リストを取得
		List<HealthInfoReferenceResponse> resultList = sessionService.getValue(request, "resultList", List.class);
		String userId = sessionService.getValue(request, "userId", String.class);

		// CSV出力モデルリストに変換する
		List<ReferenceCsvModel> modelList = toModelList(userId, resultList);

		// CSV設定情報取得
		Account account = accountSearchService.findByUserId(sessionService.getValue(request, "userId", String.class));
		String fileName = ParamConst.CSV_FILE_NAME_REFERNCE_RESULT.getValue();
		CsvConfig conf = getCsvConfig(fileName, account);

		// CSVに書き込む
		BaseCsvWriter<ReferenceCsvModel> writer = new ReferenceCsvWriter(conf, modelList);
		writer.execute(response);
	}

	/**
	 * 結果照会CSVモデルリストに変換する
	 *
	 * @param userId
	 *            ユーザID
	 * @param resultList
	 *            List<HealthInfoReferenceResponse>
	 * @return modelList
	 */
	private List<ReferenceCsvModel> toModelList(String userId, List<HealthInfoReferenceResponse> resultList) {

		List<ReferenceCsvModel> modelList = new ArrayList<ReferenceCsvModel>();
		Stream.iterate(0, i -> ++i).limit(resultList.size()).forEach(i -> {
			ReferenceCsvModel model = new ReferenceCsvModel();
			HealthInfoReferenceResponse healthInfo = resultList.get(i);
			BeanUtil.copy(healthInfo, model);
			model.setUserId(userId);
			model.setRegDate(DateUtil.toDate(healthInfo.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			modelList.add(model);
		});

		return modelList;
	}

}
