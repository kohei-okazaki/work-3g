package jp.co.ha.dashboard.healthinfo.service;

import java.util.Optional;

import jp.co.ha.business.dto.HealthInfoRefDetailDto;

/**
 * 健康情報詳細サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoRefDetailService {

    /**
     * 健康情報照会詳細Dtoを返す
     *
     * @param dto
     *     健康情報照会詳細Dto
     * @return 健康情報照会詳細Dto
     */
    Optional<HealthInfoRefDetailDto> getHealthInfoRefDetailDto(
            HealthInfoRefDetailDto dto);

}
