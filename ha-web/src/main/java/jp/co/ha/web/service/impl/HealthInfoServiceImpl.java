package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.parameter.MainKey;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.business.parameter.SubKey;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.BeanUtil;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo) {

		SubKey subkey;
		if (form.getWeight().compareTo(lastHealthInfo.getWeight()) == 0) {
			// 変化なしの場合
			subkey = SubKey.EVEN;
		} else if (form.getWeight().compareTo(lastHealthInfo.getWeight()) == 1) {
			// 増加した場合
			subkey = SubKey.INCREASE;
		} else {
			// 減少した場合
			subkey = SubKey.DOWN;
		}
		return ParamConst.of(MainKey.HEALTH_INFO_USER_STATUS_MESSAGE, subkey).getValue();

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
		apiRequest.setRequestId(RequestType.HEALTH_INFO_REGIST.getRequestId());
		return apiRequest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRecord(List<HealthInfo> entityList, String dataId) {
		return entityList.stream()
						.map(entity -> entity.getHealthInfoId())
						.anyMatch(entityDataId -> entityDataId.equals(dataId));
	}

}
