package jp.co.ha.business.healthInfo.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.excel.builder.HealthInfoExcelBuilder;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;

/**
 * 健康情報照会画面Excelダウンロードサービス実装クラス
 *
 * @version 1.0.0
 */
@Service("referenceDownloadExcel")
public class HealthInfoReferExcelDownloadServiceImpl
        implements ExcelDownloadService<ReferenceExcelComponent> {

    @Override
    public View download(ReferenceExcelComponent component, ExcelConfig config)
            throws BaseException {
        return new HealthInfoExcelBuilder(config, toModelList(component.getResultList()));
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
            model.setHealthInfoRegDate(DateTimeUtil.toLocalDateTime(
                    result.getHealthInfoRegDate(), DateFormatType.YYYYMMDDHHMMSS_STRICT));
            return model;
        }).collect(Collectors.toList());
    }

}
