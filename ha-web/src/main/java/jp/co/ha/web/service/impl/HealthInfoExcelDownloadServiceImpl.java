package jp.co.ha.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.business.io.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報Excelダウンロードサービス実装クラス
 *
 */
@Service(value = "healthInfoDownloadExcel")
public class HealthInfoExcelDownloadServiceImpl implements ExcelDownloadService<HealthInfo> {

	/** 健康情報利用機能サービス */
	@Autowired
	private HealthInfoFunctionService healthInfoFunctionService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View execute(HealthInfo healthInfo) throws BaseException {

		// 健康情報Entityから健康情報ファイル設定を検索
		HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService.findByUserId(healthInfo.getUserId());
		HealthInfoExcelModel model = toModel(healthInfo, healthInfoFileSetting);

		return new HealthInfoExcelBuilder(getExcelConfig(healthInfoFileSetting), List.of(model));
	}

	/**
	 * 健康情報Entityをモデルに変換する<br>
	 *
	 * @param healthInfo
	 *     HealthInfo
	 * @param entity
	 *     健康情報ファイル設定
	 * @return model HealthInfoExcelModel
	 */
	private HealthInfoExcelModel toModel(HealthInfo healthInfo, HealthInfoFileSetting entity) {

		HealthInfoExcelModel model = new HealthInfoExcelModel();

		boolean useMask = healthInfoFunctionService.useHealthInfoMask(entity);
		model.setHeight(useMask ? "****" : healthInfo.getHeight().toString());
		model.setWeight(useMask ? "****" : healthInfo.getWeight().toString());
		model.setBmi(useMask ? "****" : healthInfo.getBmi().toString());
		model.setStandardWeight(useMask ? "****" : healthInfo.getStandardWeight().toString());

		return model;
	}

	/**
	 * Excel設定情報を取得<br>
	 * @param healthInfoFileSetting 健康情報設定情報
	 * @return
	 */
	private ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting) {
		ExcelConfig conf = new ExcelConfig();
		conf.setCharsetType(Charset.UTF_8);
		conf.setHasHeader(StringUtil.isTrue(healthInfoFileSetting.getHeaderFlag()));
		conf.setHasFooter(StringUtil.isTrue(healthInfoFileSetting.getFooterFlag()));
		return conf;
	}

}
