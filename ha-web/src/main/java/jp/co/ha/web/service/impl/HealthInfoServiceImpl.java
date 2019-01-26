package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;

/**
 * 健康情報_入力画面サービス実装クラス
 *
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報計算サービス */
	@Autowired
	private HealthInfoCalcService healthInfoCalcService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService healthInfoRegistService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addModel(Model model, HealthInfoForm form, HealthInfo lastHealthInfo) {
		model.addAttribute("beforeWeight", lastHealthInfo.getWeight());
		model.addAttribute("diffWeight", getDiffWeight(form, lastHealthInfo));
		model.addAttribute("resultMessage", getDiffMessage(form, lastHealthInfo));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFirstReg(String userId) throws BaseException {
		// ユーザIDから健康情報のリストを取得
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByUserId(userId);
		return CollectionUtil.isEmpty(healthInfoList);
	}

	/**
	 * 健康情報登録APIリクエストの設定を行う<br>
	 *
	 * @param form
	 *     健康情報入力フォーム
	 * @param userId
	 *     ユーザID
	 * @throws BaseException
	 *     基底例外
	 */
	private HealthInfoRegistRequest setUpApiRequest(HealthInfoForm form, String userId) throws BaseException {
		HealthInfoRegistRequest apiRequest = new HealthInfoRegistRequest();
		// フォーム情報をリクエストクラスにコピー
		BeanUtil.copy(form, apiRequest);
		apiRequest.setUserId(userId);
		// リクエストタイプ設定
		apiRequest.setRequestType(RequestType.HEALTH_INFO_REGIST);
		// アカウント情報.APIキーを設定
		Account account = accountSearchService.findByUserId(userId);
		apiRequest.setApiKey(account.getApiKey());
		return apiRequest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRecord(List<HealthInfo> entityList, Integer healthInfoId) {
		return entityList.stream()
				.map(e -> e.getHealthInfoId())
				.anyMatch(e -> e.equals(healthInfoId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoCsvDownloadModel> toModelList(HealthInfo healthInfo) {
		List<HealthInfoCsvDownloadModel> modelList = new ArrayList<>();
		HealthInfoCsvDownloadModel model = new HealthInfoCsvDownloadModel();
		BeanUtil.copy(healthInfo, model);
		modelList.add(model);
		return modelList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse regist(HealthInfoForm form, String userId) throws BaseException {
		HealthInfoRegistRequest apiRequest = setUpApiRequest(form, userId);
		healthInfoRegistService.checkRequest(apiRequest);
		HealthInfoRegistResponse apiResponse = healthInfoRegistService.execute(apiRequest);
		return apiResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CsvConfig getCsvConfig(HealthInfoFileSetting entity) {
		CsvConfig csvConfig = new CsvConfig();
		var fileName = "healthInfoRegist_"
				+ DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS_NOSEP)
				+ FileExtension.CSV.getValue();
		csvConfig.setFileName(fileName);
		csvConfig.setHasHeader(StringUtil.isTrue(entity.getHeaderFlag()));
		csvConfig.setHasFooter(StringUtil.isTrue(entity.getFooterFlag()));
		csvConfig.setCsvFileChar(CsvFileChar.DOBBLE_QUOTE);
		csvConfig.setHasEnclosure(StringUtil.isTrue(entity.getEnclosureCharFlag()));
		csvConfig.setUseMask(StringUtil.isTrue(entity.getMaskFlag()));
		csvConfig.setCharset(Charset.UTF_8);
		return csvConfig;
	}

	/**
	 * 体重差メッセージを返す
	 *
	 * @param form
	 *     健康情報入力フォーム
	 * @param healthInfo
	 *     健康情報
	 * @return 体重差メッセージ
	 */
	private String getDiffMessage(HealthInfoForm form, HealthInfo healthInfo) {
		return healthInfoCalcService.getHealthStatus(form.getWeight(), healthInfo.getWeight()).getMessage();
	}

	/**
	 * 体重差を返す
	 *
	 * @param form
	 *     健康情報入力フォーム
	 * @param healthInfo
	 *     健康情報
	 * @return 体重差
	 */
	private BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo healthInfo) {
		return healthInfoCalcService.calcDiffWeight(healthInfo.getWeight(), form.getWeight());
	}

}
