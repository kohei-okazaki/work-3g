package jp.co.ha.dashboard.healthinfo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoRefDetailDto;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoRefDetailService;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報詳細サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoRefDetailServiceImpl implements HealthInfoRefDetailService {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** BMI範囲マスタ検索サービス */
    @Autowired
    private BmiRangeMtSearchService bmiRangeMtSearchService;

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthInfoRefDetailDto getHealthInfoRefDetailDto(HealthInfoRefDetailDto dto) {

        // 健康情報を検索
        List<HealthInfo> healthInfoList = healthInfoSearchService
                .findByHealthInfoIdAndUserId(dto.getSeqHealthInfoId(), dto.getUserId());

        List<HealthInfoRefDetailDto> list = healthInfoList.stream().map(e -> {

            HealthInfoRefDetailDto detailDto = new HealthInfoRefDetailDto();
            BeanUtil.copy(dto, detailDto);
            BeanUtil.copy(e, detailDto, (src, dest) -> {
                HealthInfo srcEntity = (HealthInfo) src;
                HealthInfoRefDetailDto destDto = (HealthInfoRefDetailDto) dest;

                // 健康情報ステータスメッセージ
                destDto.setHealthInfoStatusMessage(HealthInfoStatus
                        .of(srcEntity.getHealthInfoStatus()).getMessage());
                // 健康情報作成日時
                destDto.setHealthInfoRegDate(
                        DateUtil.toString(srcEntity.getHealthInfoRegDate(),
                                DateFormatType.YYYYMMDDHHMMSS));
                // 肥満度メッセージ
                BmiRangeMt mt = bmiRangeMtSearchService
                        .findByBmiRangeId(srcEntity.getSeqBmiRangeId())
                        .orElseThrow(() -> new SystemRuntimeException(
                                CommonErrorCode.DB_NO_DATA,
                                "BMI範囲マスタが存在しません seqBmiRangeId="
                                        + srcEntity.getSeqBmiRangeId()));
                destDto.setOverweightMessage(mt.getOverWeightStatus());
            });
            return detailDto;
        }).collect(Collectors.toList());

        return list.isEmpty() ? null : list.get(0);
    }

}
