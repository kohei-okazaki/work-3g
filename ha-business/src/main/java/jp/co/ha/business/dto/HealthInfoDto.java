package jp.co.ha.business.dto;

import java.math.BigDecimal;

/**
 * 健康情報DTO
 *
 * @param height
 *     身長
 * @param weight
 *     体重
 * @version 1.0.0
 */
public record HealthInfoDto(
        BigDecimal height,
        BigDecimal weight) {
}
