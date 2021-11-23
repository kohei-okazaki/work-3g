package jp.co.ha.business.healthInfo.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.io.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelComponent;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報Excelダウンロードサービス実装クラス
 *
 * @version 1.0.0
 */
@Service("healthInfoDownloadExcel")
public class HealthInfoExcelDownloadServiceImpl
        implements ExcelDownloadService<HealthInfoExcelComponent> {

    @Override
    public View download(HealthInfoExcelComponent component, ExcelConfig config)
            throws BaseException {
        return new HealthInfoExcelBuilder(config,
                Arrays.asList(toModel(component.getHealthInfo())));
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

}
