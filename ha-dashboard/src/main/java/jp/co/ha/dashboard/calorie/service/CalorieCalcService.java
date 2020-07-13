package jp.co.ha.dashboard.calorie.service;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.common.exception.BaseException;

/**
 * カロリー計算サービスインターフェース
 *
 * @version 1.0.0
 */
public interface CalorieCalcService {

    /**
     * カロリー計算を行う
     *
     * @param dto
     *     カロリー計算DTO
     * @return CalorieCalcDto
     * @throws BaseException
     *     カロリー計算API通信に失敗した場合
     */
    CalorieCalcDto calc(CalorieCalcDto dto) throws BaseException;

}
