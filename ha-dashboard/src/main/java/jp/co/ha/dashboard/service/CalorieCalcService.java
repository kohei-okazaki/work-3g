package jp.co.ha.dashboard.service;

import jp.co.ha.business.dto.CalorieCalcDto;

/**
 * カロリー計算サービスインターフェース
 *
 */
public interface CalorieCalcService {

	/**
	 * カロリー計算を行う
	 *
	 * @param dto
	 *     カロリー計算DTO
	 * @return CalorieCalcDto
	 */
	CalorieCalcDto calc(CalorieCalcDto dto);
}
