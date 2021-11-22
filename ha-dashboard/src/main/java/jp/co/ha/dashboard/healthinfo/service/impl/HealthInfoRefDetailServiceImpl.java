package jp.co.ha.dashboard.healthinfo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoRefDetailDto;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoRefDetailService;
import jp.co.ha.db.entity.composite.CompositeHealthInfo;

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

    @Override
    public Optional<HealthInfoRefDetailDto> getHealthInfoRefDetailDto(
            HealthInfoRefDetailDto dto) {

        // 健康情報を検索
        CompositeHealthInfo healthInfo = healthInfoSearchService
                .findHealthInfoDetail(dto.getSeqHealthInfoId(), dto.getSeqUserId());
        if (healthInfo == null) {
            return Optional.empty();
        }

        HealthInfoRefDetailDto detailDto = new HealthInfoRefDetailDto();
        BeanUtil.copy(healthInfo, detailDto, (src, dest) -> {
            CompositeHealthInfo srcEntity = (CompositeHealthInfo) src;
            HealthInfoRefDetailDto destDto = (HealthInfoRefDetailDto) dest;

            // 健康情報作成日時
            destDto.setHealthInfoRegDate(DateTimeUtil.toString(
                    srcEntity.getHealthInfoRegDate(), DateFormatType.YYYYMMDDHHMMSS));
        });
        return Optional.ofNullable(detailDto);
    }

}
