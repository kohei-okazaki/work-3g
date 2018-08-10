package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.db.find.HealthInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.file.csv.model.ReferenceCsvDownloadModel;
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
	 *     健康情報照会画面フォーム
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws BaseException
	 */
	private List<HealthInfo> getHealthInfo(HealthInfoReferenceForm form, String userId) throws BaseException {

		List<HealthInfo> resultList = null;
		if (BeanUtil.isNull(form.getHealthInfoId()) || StringUtil.isEmpty(form.getHealthInfoId().toString())) {
			Date regDate = editStrDate(form.getFromRegDate());
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 登録日直接指定フラグがONの場合
				resultList = healthInfoSearchService.findByUserIdAndRegDate(userId, regDate);
			} else {
				Date toRegDate = editStrDate(form.getToRegDate());
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, regDate, toRegDate);
			}
		} else {
			HealthInfo entity = healthInfoSearchService.findByHealthInfoId(form.getHealthInfoId());
			if (BeanUtil.isNull(entity)) {
				resultList = new ArrayList<>();
			} else {
				resultList = List.of(entity);
			}
		}

		return resultList;
	}

	/**
	 * 指定した文字列型のyyyy-MM-ddをDate型(yyyy/MM/dd)で返す<br>
	 *
	 * @param date
	 *     日付
	 * @return
	 */
	private Date editStrDate(String date) {
		String strDate = date.replace(StringUtil.HYPHEN, StringUtil.THRASH);
		return DateUtil.toDate(strDate, DateFormatPattern.YYYYMMDD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoReferenceResponse> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId) throws BaseException {

		// ユーザIDと検索条件フォームから健康情報Entityを取得
		List<HealthInfo> entityList = getHealthInfo(form, userId);
		List<HealthInfoReferenceResponse> resultList = new ArrayList<HealthInfoReferenceResponse>();
		entityList.stream().forEach(entity -> {
			HealthInfoReferenceResponse response = new HealthInfoReferenceResponse();
			BeanUtil.copy(entity, response);
			response.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
			resultList.add(response);
		});
		return resultList;
	}

	/**
	 * 結果照会CSVモデルリストに変換する
	 *
	 * @param userId
	 *     ユーザID
	 * @param resultList
	 *     List<HealthInfoReferenceResponse>
	 * @return modelList
	 */
	@Override
	public List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceResponse> resultList) {

		List<ReferenceCsvDownloadModel> modelList = new ArrayList<ReferenceCsvDownloadModel>();
		Stream.iterate(0, i -> ++i).limit(resultList.size()).forEach(i -> {
			ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
			HealthInfoReferenceResponse healthInfo = resultList.get(i);
			BeanUtil.copy(healthInfo, model);
			model.setUserId(userId);
			model.setRegDate(DateUtil.toDate(healthInfo.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
			modelList.add(model);
		});

		return modelList;
	}

}
