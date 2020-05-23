package jp.co.ha.business.healthInfo.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.ExcelConfig.ExcelConfigBuilder;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面Excelダウンロードサービス実装クラス
 *
 * @version 1.0.0
 */
@Service("referenceDownloadExcel")
public class HealthInfoReferExcelDownloadServiceImpl
        implements ExcelDownloadService<ReferenceExcelComponent> {

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
                .findById(component.getUserId()).get();

        List<HealthInfoExcelModel> modelList = toModelList(component.getResultList());

        return new HealthInfoExcelBuilder(getExcelConfig(healthInfoFileSetting),
                modelList);
    }

    /**
     * 健康情報照会リストをモデルリストに変換する
     *
     * @param resultList
     *     健康情報照会リスト
     * @return modelList
     */
    private List<HealthInfoExcelModel> toModelList(
            List<HealthInfoReferenceDto> resultList) {
        return Stream.iterate(0, i -> ++i).limit(resultList.size()).map(i -> {
            // Excel出力モデル
            HealthInfoExcelModel model = new HealthInfoExcelModel();
            HealthInfoReferenceDto result = resultList.get(i);
            model.setHeight(result.getHeight().toString());
            model.setWeight(result.getWeight().toString());
            model.setBmi(result.getBmi().toString());
            model.setStandardWeight(result.getStandardWeight().toString());
            model.setHealthInfoRegDate(DateUtil.toDate(result.getHealthInfoRegDate(),
                    DateFormatType.YYYYMMDDHHMMSS));
            return model;
        }).collect(Collectors.toList());
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
    private ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting)
            throws BaseException {
        return new ExcelConfigBuilder(null)
                .hasHeader(CommonFlag.TRUE.is(healthInfoFileSetting.getHeaderFlag()))
                .hasFooter(CommonFlag.TRUE.is(healthInfoFileSetting.getFooterFlag()))
                .useMask(CommonFlag.TRUE.is(healthInfoFileSetting.getMaskFlag()))
                .build();
    }

}
