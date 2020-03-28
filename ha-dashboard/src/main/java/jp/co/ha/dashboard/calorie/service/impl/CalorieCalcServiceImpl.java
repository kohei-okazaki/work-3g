package jp.co.ha.dashboard.calorie.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.dashboard.calorie.service.CalorieCalcService;

/**
 * カロリー計算サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class CalorieCalcServiceImpl implements CalorieCalcService {

    /** 健康情報計算サービス */
    @Autowired
    private HealthInfoCalcService healthInfoCalcService;

    /**
     * {@inheritDoc}
     */
    @Override
    public CalorieCalcDto calc(CalorieCalcDto dto) {

        BigDecimal baseMetabolism = healthInfoCalcService.calcBaseMetabolism(dto);
        dto.setBaseMetabolism(baseMetabolism);

        BigDecimal lostCaloriePerDay = healthInfoCalcService.calcLostCaloriePerDay(dto);
        dto.setLostCaloriePerDay(lostCaloriePerDay);

        return dto;
    }

}
