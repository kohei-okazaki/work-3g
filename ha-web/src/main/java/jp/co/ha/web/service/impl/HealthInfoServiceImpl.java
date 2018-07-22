package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.web.file.csv.model.HealthInfoCsvDownloadModel;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo) {
		return healthInfoCalcService.getUserStatus(form.getWeight(), lastHealthInfo.getWeight()).getMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo lastHealthInfo) {
		return healthInfoCalcService.calcDiffWeight(lastHealthInfo.getWeight(), form.getWeight());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFirstReg(String userId) {

		// ユーザIDから健康情報のリストを取得
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByUserId(userId);
		return healthInfoList.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistRequest setUpApiRequest(HealthInfoForm form, String userId) {
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
	public boolean hasRecord(List<HealthInfo> entityList, BigDecimal dataId) {
		return entityList.stream()
						.map(entity -> entity.getHealthInfoId())
						.anyMatch(entityDataId -> entityDataId.equals(dataId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoCsvDownloadModel> toModelList(HealthInfo healthInfo) {
		List<HealthInfoCsvDownloadModel> modelList = new ArrayList<HealthInfoCsvDownloadModel>();
		HealthInfoCsvDownloadModel model = new HealthInfoCsvDownloadModel();
		BeanUtil.copy(healthInfo, model);
		modelList.add(model);
		return modelList;
	}

}
