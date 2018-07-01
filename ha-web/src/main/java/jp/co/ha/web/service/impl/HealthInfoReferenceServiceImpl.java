package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.HealthInfoReferenceForm;
import jp.co.ha.web.service.HealthInfoReferenceService;

/**
 * 健康情報照会画面サービスインターフェース実装クラス<br>
 *
 */
@Service
public class HealthInfoReferenceServiceImpl implements HealthInfoReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * 健康情報を取得する<br>
	 *
	 * @param form
	 *            健康情報照会画面フォーム
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	private List<HealthInfo> getHealthInfo(HealthInfoReferenceForm form, String userId) {

		List<HealthInfo> resultList = null;
		if (StringUtil.isEmpty(form.getHealthInfoId())) {
			Date regDate = editStrDate(form.getFromRegDate());
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 登録日直接指定フラグがONの場合
				resultList = healthInfoSearchService.findByUserIdAndRegDate(userId, regDate);
			} else {
				Date toRegDate = editStrDate(form.getToRegDate());
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, regDate, toRegDate);
			}
		} else {
			resultList = List.of(healthInfoSearchService.findByHealthInfoId(form.getHealthInfoId()));
		}

		return resultList;
	}

	/**
	 * 指定した文字列型のyyyy-MM-ddをDate型(yyyy/MM/dd)で返す<br>
	 *
	 * @param date
	 *            日付
	 * @return
	 */
	private Date editStrDate(String date) {
		String strDate = date.replace(StringUtil.HYPHEN, StringUtil.THRASH);
		return DateUtil.toDate(strDate, DateFormatDefine.YYYYMMDD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoReferenceResponse> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId) {

		// ユーザIDと検索条件フォームから健康情報Entityを取得
		List<HealthInfo> entityList = getHealthInfo(form, userId);
		List<HealthInfoReferenceResponse> resultList = new ArrayList<HealthInfoReferenceResponse>();
		entityList.stream().forEach(entity -> {
			HealthInfoReferenceResponse response = new HealthInfoReferenceResponse();
			BeanUtil.copy(entity, response);
			response.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			resultList.add(response);
		});
		return resultList;
	}

}
