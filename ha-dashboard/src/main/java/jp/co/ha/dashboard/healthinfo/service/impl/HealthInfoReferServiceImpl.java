package jp.co.ha.dashboard.healthinfo.service.impl;

import static jp.co.ha.common.db.SelectOption.SortType.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvConfig.CsvConfigBuilder;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
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

    /** 健康情報情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public List<HealthInfoReferenceDto> getHealthInfoResponseList(
            HealthInfoReferenceDto dto, Long seqUserId, Pageable pageable)
            throws BaseException {

        List<HealthInfo> resultList;
        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", DESC)
                .pageable(pageable)
                .build();
        if (BeanUtil.isNull(dto.getSeqHealthInfoId())
                || StringUtil.isEmpty(dto.getSeqHealthInfoId().toString())) {
            // 健康情報IDが未指定の場合
            if (CommonFlag.TRUE.is(dto.getHealthInfoRegDateSelectFlag())) {
                resultList = healthInfoSearchService
                        .findBySeqUserIdBetweenHealthInfoRegDate(
                                seqUserId, editFromDate(dto.getFromHealthInfoRegDate()),
                                editToDate(dto.getFromHealthInfoRegDate()), selectOption);
            } else {
                resultList = healthInfoSearchService
                        .findBySeqUserIdBetweenHealthInfoRegDate(
                                seqUserId, editFromDate(dto.getFromHealthInfoRegDate()),
                                editToDate(dto.getToHealthInfoRegDate()), selectOption);
            }
        } else {
            resultList = healthInfoSearchService
                    .findByHealthInfoIdAndSeqUserId(dto.getSeqHealthInfoId(), seqUserId);
        }

        return resultList.stream().map(e -> {

            HealthInfoReferenceDto result = new HealthInfoReferenceDto();
            // リクエストDTOを設定(検索条件部の設定)
            BeanUtil.copy(dto, result);

            // 健康情報Entityを設定
            BeanUtil.copy(e, result, (src, dest) -> {
                HealthInfo srcEntity = (HealthInfo) src;
                HealthInfoReferenceDto destDto = (HealthInfoReferenceDto) dest;

                destDto.setHealthInfoRegDate(DateTimeUtil
                        .toString(srcEntity.getHealthInfoRegDate(), YYYYMMDDHHMMSS));
            });

            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public long getCount(HealthInfoReferenceDto dto, Long seqUserId)
            throws BaseException {

        // ユーザIDと健康情報照会DTOから健康情報件数を取得
        long count;
        if (BeanUtil.isNull(dto.getSeqHealthInfoId())
                || StringUtil.isEmpty(dto.getSeqHealthInfoId().toString())) {
            // 健康情報IDが未指定の場合
            if (CommonFlag.TRUE.is(dto.getHealthInfoRegDateSelectFlag())) {
                count = healthInfoSearchService
                        .countBySeqUserIdBetweenHealthInfoRegDate(
                                seqUserId, editFromDate(dto.getFromHealthInfoRegDate()),
                                editToDate(dto.getFromHealthInfoRegDate()));
            } else {
                count = healthInfoSearchService
                        .countBySeqUserIdBetweenHealthInfoRegDate(
                                seqUserId, editFromDate(dto.getFromHealthInfoRegDate()),
                                editToDate(dto.getToHealthInfoRegDate()));
            }
        } else {
            count = healthInfoSearchService
                    .countByHealthInfoIdAndSeqUserId(dto.getSeqHealthInfoId(), seqUserId);
        }

        return count;
    }

    @Override
    public List<ReferenceCsvDownloadModel> toModelList(Long seqUserId,
            List<HealthInfoReferenceDto> resultList) {
        return resultList.stream().map(e -> {
            ReferenceCsvDownloadModel model = new ReferenceCsvDownloadModel();
            BeanUtil.copy(e, model, (src, dest) -> {
                HealthInfoReferenceDto srcDto = (HealthInfoReferenceDto) src;
                ReferenceCsvDownloadModel destModel = (ReferenceCsvDownloadModel) dest;

                destModel.setHealthInfoRegDate(DateTimeUtil.toLocalDateTime(
                        srcDto.getHealthInfoRegDate(), YYYYMMDDHHMMSS_STRICT));

            });
            model.setSeqUserId(seqUserId);

            return model;
        }).collect(Collectors.toList());
    }

    @Override
    public CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException {

        // ファイル名
        String fileName = DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                YYYYMMDDHHMMSS_NOSEP_STRICT) + FileExtension.CSV;
        // ファイル出力先
        String path = prop.referenceFilePath() + FileSeparator.SYSTEM.getValue()
                + entity.getSeqUserId();

        return new CsvConfigBuilder(fileName, path)
                .hasHeader(entity.getHeaderFlag())
                .hasFooter(entity.getFooterFlag())
                .csvFileChar(CsvFileChar.DOUBLE_QUOTE)
                .hasEnclosure(entity.getEnclosureCharFlag())
                .useMask(entity.getMaskFlag())
                .build();
    }

    /**
     * 指定した文字列型の日付をYYYYMMDD 00:00:00の{@linkplainLocalDateTime}に直す
     *
     * @param date
     *     日付
     * @return YYYYMMDD 00:00:00
     */
    private LocalDateTime editFromDate(LocalDate date) {
        return DateTimeUtil.getStartDay(date);
    }

    /**
     * 指定した文字列型の日付をYYYYMMDD 23:59:59の{@linkplainLocalDateTime}に直す
     *
     * @param date
     *     日付
     * @return YYYYMMDD 23:59:59
     */
    private LocalDateTime editToDate(LocalDate date) {
        return DateTimeUtil.getEndDay(date);
    }

}
