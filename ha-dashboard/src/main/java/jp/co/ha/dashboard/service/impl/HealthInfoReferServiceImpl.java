package jp.co.ha.dashboard.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
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
	public List<HealthInfoReferenceDto> getHealthInfoResponseList(HealthInfoReferenceDto dto, String userId)
			throws BaseException {

		// ユーザIDと検索条件フォームから健康情報Entityリストを取得
		List<HealthInfo> entityList = getHealthInfoList(dto, userId);
		return entityList.stream().map(e -> {
			HealthInfoReferenceDto result = new HealthInfoReferenceDto();
			// リクエストDTOを設定(検索条件部の設定)
			BeanUtil.copy(dto, result);

			// 健康情報Entityを設定
			BeanUtil.copy(e, result, (src, dest) -> {
				HealthInfo srcEntity = (HealthInfo) src;
				HealthInfoReferenceDto destDto = (HealthInfoReferenceDto) dest;

				destDto.setHealthInfoRegDate(
						DateUtil.toString(srcEntity.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			});

			return result;
		}).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceDto> resultList) {
		return resultList.stream().map(e -> {
			ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
			BeanUtil.copy(e, model, (src, dest) -> {
				HealthInfoReferenceDto srcDto = (HealthInfoReferenceDto) src;
				ReferenceCsvDownloadModel destModel = (ReferenceCsvDownloadModel) dest;

				destModel.setHealthInfoRegDate(
						DateUtil.toDate(srcDto.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));

			});
			model.setUserId(userId);

			return model;
		}).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CsvConfig getCsvConfig(HealthInfoFileSetting entity) {

		CsvConfig conf = new CsvConfig();
		String fileName = "healthInfoReference_"
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
	 * @param dto
	 *     健康情報照会DTO
	 * @param userId
	 *     ユーザID
	 * @return 健康情報リスト
	 * @throws BaseException
	 *     基底例外
	 */
	private List<HealthInfo> getHealthInfoList(HealthInfoReferenceDto dto, String userId) throws BaseException {

		List<HealthInfo> resultList;

		if (BeanUtil.isNull(dto.getHealthInfoId()) || StringUtil.isEmpty(dto.getHealthInfoId().toString())) {
			Date healthInfoRegDate = editStrDate(dto.getFromHealthInfoRegDate());
			if (CommonFlag.TRUE.is(dto.getHealthInfoRegDateSelectFlag())) {
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, healthInfoRegDate,
						DateUtil.toEndDate(healthInfoRegDate));
			} else {
				Date toHealthInfoRegDate = editStrDate(dto.getToHealthInfoRegDate());
				resultList = healthInfoSearchService.findByUserIdBetweenRegDate(userId, healthInfoRegDate,
						toHealthInfoRegDate);
			}
		} else {
			resultList = healthInfoSearchService.findByHealthInfoIdAndUserId(dto.getHealthInfoId(), userId);
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
