package jp.co.ha.dashboard.healthinfo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvConfig.CsvConfigBuilder;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoReferService;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面サービスインターフェース実装クラス
 *
 * @version 1.0.0
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
    public List<HealthInfoReferenceDto> getHealthInfoResponseList(
            HealthInfoReferenceDto dto, String userId) throws BaseException {

        // ユーザIDと健康情報照会DTOから健康情報Entityリストを取得
        List<HealthInfo> entityList = getHealthInfoList(dto, userId);
        return entityList.stream().map(e -> {
            HealthInfoReferenceDto result = new HealthInfoReferenceDto();
            // リクエストDTOを設定(検索条件部の設定)
            BeanUtil.copy(dto, result);

            // 健康情報Entityを設定
            BeanUtil.copy(e, result, (src, dest) -> {
                HealthInfo srcEntity = (HealthInfo) src;
                HealthInfoReferenceDto destDto = (HealthInfoReferenceDto) dest;

                destDto.setHealthInfoRegDate(DateUtil.toString(
                        srcEntity.getHealthInfoRegDate(), DateFormatType.YYYYMMDDHHMMSS));
            });

            return result;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReferenceCsvDownloadModel> toModelList(String userId,
            List<HealthInfoReferenceDto> resultList) {
        return resultList.stream().map(e -> {
            ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
            BeanUtil.copy(e, model, (src, dest) -> {
                HealthInfoReferenceDto srcDto = (HealthInfoReferenceDto) src;
                ReferenceCsvDownloadModel destModel = (ReferenceCsvDownloadModel) dest;

                destModel.setHealthInfoRegDate(DateUtil.toDate(
                        srcDto.getHealthInfoRegDate(), DateFormatType.YYYYMMDDHHMMSS));

            });
            model.setUserId(userId);

            return model;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException {

        // ファイル名
        String fileName = DateUtil.toString(DateUtil.getSysDate(),
                DateFormatType.YYYYMMDDHHMMSS_NOSEP) + FileExtension.CSV.getValue();
        // ファイル出力先
        String path = prop.getReferenceFilePath() + FileSeparator.SYSTEM.getValue()
                + entity.getUserId();

        return new CsvConfigBuilder(fileName, path)
                .hasHeader(CommonFlag.TRUE.is(entity.getHeaderFlag()))
                .hasFooter(CommonFlag.TRUE.is(entity.getFooterFlag()))
                .csvFileChar(CsvFileChar.DOBBLE_QUOTE)
                .hasEnclosure(CommonFlag.TRUE.is(entity.getEnclosureCharFlag()))
                .useMask(CommonFlag.TRUE.is(entity.getMaskFlag()))
                .build();
    }

    /**
     * 健康情報リストを取得する
     *
     * @param dto
     *     健康情報照会DTO
     * @param userId
     *     ユーザID
     * @return 健康情報リスト
     */
    private List<HealthInfo> getHealthInfoList(HealthInfoReferenceDto dto,
            String userId) {

        List<HealthInfo> resultList;
        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC).build();
        if (BeanUtil.isNull(dto.getSeqHealthInfoId())
                || StringUtil.isEmpty(dto.getSeqHealthInfoId().toString())) {
            // 健康情報IDが未指定の場合
            Date healthInfoRegDate = editStrDate(dto.getFromHealthInfoRegDate());
            if (CommonFlag.TRUE.is(dto.getHealthInfoRegDateSelectFlag())) {
                resultList = healthInfoSearchService.findByUserIdBetweenHealthInfoRegDate(
                        userId, healthInfoRegDate,
                        DateUtil.toEndDate(healthInfoRegDate), selectOption);
            } else {
                Date toHealthInfoRegDate = editStrDate(dto.getToHealthInfoRegDate());
                resultList = healthInfoSearchService.findByUserIdBetweenHealthInfoRegDate(
                        userId, healthInfoRegDate,
                        toHealthInfoRegDate, selectOption);
            }
        } else {
            resultList = healthInfoSearchService
                    .findByHealthInfoIdAndUserId(dto.getSeqHealthInfoId(), userId);
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
        return DateUtil.toDate(date.replace(StringUtil.HYPHEN, StringUtil.THRASH),
                DateFormatType.YYYYMMDD);
    }

}
