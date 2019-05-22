package jp.co.ha.dashboard.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.dashboard.service.CalorieCalcService;

/**
 * カロリー計算サービスインターフェース実装クラス
 *
 */
@Service
public class CalorieCalcServiceImpl implements CalorieCalcService {

	@Autowired
	private HealthInfoCalcService healthInfoCalcService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CalorieCalcDto calc(CalorieCalcDto dto) {

		BigDecimal baseMetabolism = healthInfoCalcService.calcBaseMetabolism(dto);
		dto.setBaseMetabolism(baseMetabolism);

		return dto;
	}

}
