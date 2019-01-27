package jp.co.ha.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.business.io.file.excel.builder.ResultReferenceExcelBuiler;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 結果照会画面Excelダウンロードサービス実装クラス
 *
 */
@Service(value = "referenceDownloadExcel")
public class HealthInfoReferExcelDownloadServiceImpl implements ExcelDownloadService<List<HealthInfoReferenceResponse>> {

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
	public View execute(List<HealthInfoReferenceResponse> apiResponseList) throws BaseException {

		// 健康情報Entityから健康情報ファイル設定を検索
		HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService
				.findByUserId(CollectionUtil.getFirst(apiResponseList).getUserId());

		List<ReferenceExcelModel> modelList = toModelList(apiResponseList, healthInfoFileSetting);

		return new ResultReferenceExcelBuiler(getExcelConfig(healthInfoFileSetting), modelList);
	}

	/**
	 * 健康情報照会リストをモデルリストに変換する
	 *
	 * @param apiResponseList
	 *     健康情報照会リスト
	 * @param entity
	 *     健康情報ファイル設定
	 * @return modelList
	 */
	private List<ReferenceExcelModel> toModelList(List<HealthInfoReferenceResponse> apiResponseList,
			HealthInfoFileSetting entity) {

		// 健康情報マスク利用有無
		boolean useHealthInfoMask = healthInfoFunctionService.useHealthInfoMask(entity);
		List<ReferenceExcelModel> modelList = new ArrayList<>();
		Stream.iterate(0, i -> ++i).limit(apiResponseList.size()).forEach(i -> {

			// 結果照会Excel出力モデル
			ReferenceExcelModel model = new ReferenceExcelModel();
			HealthInfoReferenceResponse healthInfo = apiResponseList.get(i);
			model.setHeight(useHealthInfoMask ? "****" : healthInfo.getHeight().toString());
			model.setWeight(useHealthInfoMask ? "****" : healthInfo.getWeight().toString());
			model.setBmi(useHealthInfoMask ? "****" : healthInfo.getBmi().toString());
			model.setStandardWeight(useHealthInfoMask ? "****" : healthInfo.getStandardWeight().toString());
			model.setRegDate(DateUtil.toDate(healthInfo.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));

			modelList.add(model);
		});

		return modelList;
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
		conf.setHasHeader(StringUtil.isTrue(healthInfoFileSetting.getHeaderFlag()));
		conf.setHasFooter(StringUtil.isTrue(healthInfoFileSetting.getFooterFlag()));
		return conf;
	}

}
