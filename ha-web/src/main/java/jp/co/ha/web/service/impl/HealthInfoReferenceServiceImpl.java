package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.FileUtil.FileSuffix;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoReferenceForm;
import jp.co.ha.web.service.HealthInfoReferenceService;

/**
 * 健康情報照会画面サービスインターフェース実装クラス
 *
 */
@Service
public class HealthInfoReferenceServiceImpl implements HealthInfoReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * 健康情報リストを取得する<br>
	 *
	 * @param form
	 *     健康情報照会画面フォーム
	 * @param userId
	 *     ユーザID
	 * @return 健康情報リスト
	 * @throws BaseException
	 *     基底例外
	 */
	private List<HealthInfo> getHealthInfoList(HealthInfoReferenceForm form, String userId) throws BaseException {

		List<HealthInfo> resultList = null;
		if (StringUtil.isEmpty(form.getHealthInfoId())) {
			Date regDate = editStrDate(form.getFromRegDate());
			if (StringUtil.isTrue(form.getRegDateSelectFlag())) {
				// 登録日直接指定フラグがONの場合
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, regDate, DateUtil.toEndDate(regDate));
			} else {
				Date toRegDate = editStrDate(form.getToRegDate());
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, regDate, toRegDate);
			}
		} else {
			HealthInfo entity = healthInfoSearchService.findByHealthInfoId(Integer.valueOf(form.getHealthInfoId()));
			if (BeanUtil.isNull(entity) || !entity.getUserId().equals(userId)) {
				// selectした健康情報がログイン中のユーザIDと一致しない場合
				resultList = CollectionUtil.getEmptyList(HealthInfo.class);
			} else {
				resultList = List.of(entity);
			}
		}

		return resultList;
	}

	/**
	 * 指定した文字列型のyyyy-MM-ddをDate型で返す<br>
	 *
	 * @param date
	 *     日付
	 * @return 日付
	 */
	private Date editStrDate(String date) {
		String strDate = date.replace(StringUtil.HYPHEN, StringUtil.THRASH);
		return DateUtil.toDate(strDate, DateFormatType.YYYYMMDD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoReferenceResponse> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId) throws BaseException {

		// ユーザIDと検索条件フォームから健康情報Entityを取得
		List<HealthInfo> entityList = getHealthInfoList(form, userId);
		List<HealthInfoReferenceResponse> resultList = new ArrayList<>();
		entityList.stream().forEach(entity -> {
			HealthInfoReferenceResponse response = new HealthInfoReferenceResponse();
			BeanUtil.copy(entity, response);
			response.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
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
	 *     健康情報照会レスポンスリスト
	 * @return 結果照会CSVモデルリスト
	 */
	@Override
	public List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceResponse> resultList) {

		List<ReferenceCsvDownloadModel> modelList = new ArrayList<>();
		Stream.iterate(0, i -> ++i).limit(resultList.size()).forEach(i -> {
			ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
			HealthInfoReferenceResponse healthInfo = resultList.get(i);
			BeanUtil.copy(healthInfo, model);
			model.setUserId(userId);
			model.setRegDate(DateUtil.toDate(healthInfo.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			modelList.add(model);
		});

		return modelList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CsvConfig getCsvConfig(HealthInfoFileSetting entity) {

		CsvConfig conf = new CsvConfig();
		var fileName = "healthInfoReference_"
				+ DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS_NOSEP)
				+ FileSuffix.CSV.getValue();
		conf.setFileName(fileName);
		conf.setHasHeader(StringUtil.isTrue(entity.getHeaderFlag()));
		conf.setHasFooter(StringUtil.isTrue(entity.getFooterFlag()));
		conf.setCsvFileChar(CsvFileChar.DOBBLE_QUOTE);
		conf.setHasEnclosure(StringUtil.isTrue(entity.getEnclosureCharFlag()));
		conf.setUseMask(StringUtil.isTrue(entity.getMaskFlag()));
		conf.setCharset(Charset.UTF_8);
		return conf;
	}

}
