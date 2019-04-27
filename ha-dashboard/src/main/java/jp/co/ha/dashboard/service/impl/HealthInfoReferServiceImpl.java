package jp.co.ha.dashboard.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.result.HealthInfoReferenceResult;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.dashboard.form.HealthInfoReferenceForm;
import jp.co.ha.dashboard.service.HealthInfoReferService;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面サービスインターフェース実装クラス
 *
 */
@Service
public class HealthInfoReferServiceImpl implements HealthInfoReferService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報設定ファイル */
	@Autowired
	private HealthInfoProperties prop;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoReferenceResult> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId)
			throws BaseException {

		// ユーザIDと検索条件フォームから健康情報Entityを取得
		List<HealthInfo> entityList = getHealthInfoList(form, userId);
		return entityList.stream().map(e -> {
			HealthInfoReferenceResult result = new HealthInfoReferenceResult();
			BeanUtil.copy(e, result);
			result.setHealthInfoRegDate(DateUtil.toString(e.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			return result;
		}).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceResult> resultList) {
		return resultList.stream().map(e -> {
			ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
			BeanUtil.copy(e, model);
			model.setUserId(userId);
			model.setHealthInfoRegDate(DateUtil.toDate(e.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			return model;
		}).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CsvConfig getCsvConfig(HealthInfoFileSetting entity) {

		CsvConfig conf = new CsvConfig();
		var fileName = "healthInfoReference_"
				+ DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS_NOSEP)
				+ FileExtension.CSV.getValue();
		conf.setFileName(fileName);
		conf.setHasHeader(CommonFlag.TRUE.is(entity.getHeaderFlag()));
		conf.setHasFooter(CommonFlag.TRUE.is(entity.getFooterFlag()));
		conf.setCsvFileChar(CsvFileChar.DOBBLE_QUOTE);
		conf.setHasEnclosure(CommonFlag.TRUE.is(entity.getEnclosureCharFlag()));
		conf.setUseMask(CommonFlag.TRUE.is(entity.getMaskFlag()));
		conf.setCharset(Charset.UTF_8);
		conf.setOutputPath(prop.getReferenceFilePath() + FileSeparator.SYSTEM.getValue() + entity.getUserId());
		return conf;
	}

	/**
	 * 健康情報リストを取得する
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

		List<HealthInfo> resultList;
		if (StringUtil.isEmpty(form.getHealthInfoId())) {
			Date healthInfoRegDate = editStrDate(form.getFromHealthInfoRegDate());
			if (CommonFlag.TRUE.is(form.getHealthInfoRegDateSelectFlag())) {
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, healthInfoRegDate,
						DateUtil.toEndDate(healthInfoRegDate));
			} else {
				Date toHealthInfoRegDate = editStrDate(form.getToHealthInfoRegDate());
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, healthInfoRegDate,
						toHealthInfoRegDate);
			}
		} else {
			resultList = healthInfoSearchService.findByHealthInfoIdAndUserId(Integer.valueOf(form.getHealthInfoId()),
					userId);
		}

		return resultList;
	}

	/**
	 * 指定した文字列型のyyyy-MM-ddをDate型で返す
	 *
	 * @param date
	 *     日付
	 * @return 日付
	 */
	private Date editStrDate(String date) {
		return DateUtil.toDate(date.replace(StringUtil.HYPHEN, StringUtil.THRASH), DateFormatType.YYYYMMDD);
	}

}
