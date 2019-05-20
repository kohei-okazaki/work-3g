package jp.co.ha.dashboard.service.impl;

import org.springframework.stereotype.Service;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.dashboard.service.CalorieCalcService;

/**
 * カロリー計算サービスインターフェース実装クラス
 *
 */
@Service
public class CalorieCalcServiceImpl implements CalorieCalcService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CalorieCalcDto calc(CalorieCalcDto dto) {

		CalorieCalcDto calcResult = new CalorieCalcDto();
		return calcResult;
	}

}
