package jp.co.ha.dashboard.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.excel.builder.ReferenceExcelBuiler;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面Excelダウンロードサービス実装クラス
 * 
 * @since 1.0
 */
@Service("referenceDownloadExcel")
public class HealthInfoReferExcelDownloadServiceImpl implements ExcelDownloadService<ReferenceExcelComponent> {

	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View download(ReferenceExcelComponent component) throws BaseException {

		// 健康情報Entityから健康情報ファイル設定を検索
		HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService
				.findByUserId(component.getUserId());

		List<ReferenceExcelModel> modelList = toModelList(component.getResultList());

		return new ReferenceExcelBuiler(getExcelConfig(healthInfoFileSetting), modelList);
	}

	/**
	 * 健康情報照会リストをモデルリストに変換する
	 *
	 * @param resultList
	 *     健康情報照会リスト
	 * @return modelList
	 */
	private List<ReferenceExcelModel> toModelList(List<HealthInfoReferenceDto> resultList) {
		return Stream.iterate(0, i -> ++i).limit(resultList.size()).map(i -> {
			// Excel出力モデル
			ReferenceExcelModel model = new ReferenceExcelModel();
			HealthInfoReferenceDto result = resultList.get(i);
			model.setHeight(result.getHeight().toString());
			model.setWeight(result.getWeight().toString());
			model.setBmi(result.getBmi().toString());
			model.setStandardWeight(result.getStandardWeight().toString());
			model.setHealthInfoRegDate(DateUtil.toDate(result.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			return model;
		}).collect(Collectors.toList());
	}

	/**
	 * Excel設定情報を取得
	 *
	 * @param healthInfoFileSetting
	 *     健康情報設定情報
	 * @return Excel設定情報
	 */
	private ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting) {
		ExcelConfig conf = new ExcelConfig();
		conf.setCharsetType(Charset.UTF_8);
		conf.setHasHeader(CommonFlag.TRUE.is(healthInfoFileSetting.getHeaderFlag()));
		conf.setHasFooter(CommonFlag.TRUE.is(healthInfoFileSetting.getFooterFlag()));
		conf.setUseMask(CommonFlag.TRUE.is(healthInfoFileSetting.getMaskFlag()));
		return conf;
	}

}
