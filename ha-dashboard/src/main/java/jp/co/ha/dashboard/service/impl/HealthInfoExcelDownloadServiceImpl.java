package jp.co.ha.dashboard.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.io.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelComponent;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.ExcelConfig.ExcelConfigBuilder;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報Excelダウンロードサービス実装クラス
 *
 * @since 1.0
 */
@Service("healthInfoDownloadExcel")
public class HealthInfoExcelDownloadServiceImpl implements ExcelDownloadService<HealthInfoExcelComponent> {

	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View download(HealthInfoExcelComponent component) throws BaseException {

		HealthInfo healthInfo = component.getHealthInfo();
		// 健康情報Entityから健康情報ファイル設定を検索
		HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService
				.findByUserId(healthInfo.getUserId()).get();

		// 健康情報Excelモデルに変換
		HealthInfoExcelModel model = toModel(healthInfo);

		return new HealthInfoExcelBuilder(getExcelConfig(healthInfoFileSetting), Arrays.asList(model));
	}

	/**
	 * 健康情報をモデルに変換する
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return model
	 */
	private HealthInfoExcelModel toModel(HealthInfo healthInfo) {
		HealthInfoExcelModel model = new HealthInfoExcelModel();
		model.setHeight(healthInfo.getHeight().toString());
		model.setWeight(healthInfo.getWeight().toString());
		model.setBmi(healthInfo.getBmi().toString());
		model.setStandardWeight(healthInfo.getStandardWeight().toString());
		model.setHealthInfoRegDate(healthInfo.getHealthInfoRegDate());
		return model;
	}

	/**
	 * Excel設定情報を取得
	 *
	 * @param healthInfoFileSetting
	 *     健康情報設定情報
	 * @return Excel設定情報
	 * @throws BaseException
	 *     基底例外
	 */
	private ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting) throws BaseException {

		return new ExcelConfigBuilder(null)
				.hasHeader(CommonFlag.TRUE.is(healthInfoFileSetting.getHeaderFlag()))
				.hasFooter(CommonFlag.TRUE.is(healthInfoFileSetting.getFooterFlag()))
				.useMask(CommonFlag.TRUE.is(healthInfoFileSetting.getMaskFlag()))
				.build();
	}

}
