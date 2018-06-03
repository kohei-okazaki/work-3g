package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.ResultSearchForm;
import jp.co.ha.web.service.ResultReferenceService;

/**
 * 結果照会画面サービスインターフェース実装クラス<br>
 *
 */
@Service
public class ResultReferenceServiceImpl implements ResultReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUpForm(ResultSearchForm form) {

		// 登録日from
		form.setFromRegMonth(
				form.getFromRegMonth().length() == 1 ? "0" + form.getFromRegMonth() : form.getFromRegMonth());
		form.setFromRegDay(form.getFromRegDay().length() == 1 ? "0" + form.getFromRegDay() : form.getFromRegDay());

		// 登録日to
		form.setToRegMonth(form.getToRegMonth().length() == 1 ? "0" + form.getToRegMonth() : form.getToRegMonth());
		form.setToRegDay(form.getToRegDay().length() == 1 ? "0" + form.getToRegDay() : form.getToRegDay());
	}

	/**
	 * 健康情報を取得する<br>
	 *
	 * @param form
	 *            ResultSearchForm
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	private List<HealthInfo> getHealthInfo(ResultSearchForm form, String userId) {

		List<HealthInfo> resultList = null;
		Date regDate = getStrDate(form.getFromRegYear(), form.getFromRegMonth(), form.getFromRegDay());
		if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
			// 登録日直接指定フラグがONの場合
			resultList = healthInfoSearchService.findByUserIdAndRegDate(userId, regDate);
		} else {
			Date toRegDate = getStrDate(form.getToRegYear(), form.getToRegMonth(), form.getToRegDay());
			resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, regDate, toRegDate);
		}
		return resultList;
	}

	/**
	 * 指定した文字列型のyyyy, MM, ddをDate型(yyyy/MM/dd)で返す<br>
	 *
	 * @param year
	 *            年
	 * @param month
	 *            年
	 * @param day
	 *            日
	 * @return
	 */
	private Date getStrDate(String year, String month, String day) {
		String strDate = year + StringUtil.THRASH + month + StringUtil.THRASH + day;
		return DateUtil.toDate(strDate, DateFormatDefine.YYYYMMDD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoRegistResponse> getHealthInfoResponseList(ResultSearchForm form, String userId) {

		// ユーザIDと検索条件フォームから健康情報Entityを取得
		List<HealthInfo> entityList = getHealthInfo(form, userId);
		List<HealthInfoRegistResponse> resultList = new ArrayList<HealthInfoRegistResponse>();
		entityList.stream().forEach(entity -> {
			HealthInfoRegistResponse response = new HealthInfoRegistResponse();
			BeanUtils.copyProperties(entity, response);
			response.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			resultList.add(response);
		});
		return resultList;
	}

}
