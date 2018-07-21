package jp.co.ha.business.healthInfo.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import jp.co.ha.business.calc.CalcMethod;
import jp.co.ha.business.calc.Calculator;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.type.HealthStatus;

/**
 * 計算サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoCalcServiceImpl implements HealthInfoCalcService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthStatus getUserStatus(BigDecimal inputWeight, BigDecimal beforeWeight) {

		HealthStatus status;
		if (beforeWeight.compareTo(inputWeight) == 0) {
			status = HealthStatus.EVEN;
		} else if (beforeWeight.compareTo(inputWeight) == -1) {
			status = HealthStatus.INCREASE;
		} else {
			status = HealthStatus.DOWN;
		}
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal convertMeterFromCentiMeter(BigDecimal target) {
		return target.scaleByPowerOfTen(-2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal convertCentiMeterFromMeter(BigDecimal target) {
		return target.scaleByPowerOfTen(2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit) {

		Calculator calculator = new Calculator();
		BigDecimal multiplyResult = calculator.execute(height, CalcMethod.MULTIPLY, height, 2, RoundingMode.HALF_UP);
		BigDecimal result = calculator.execute(weight, CalcMethod.DIVIDE, multiplyResult, 2, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcStandardWeight(BigDecimal height, int digit) {

		Calculator calculator = new Calculator();
		BigDecimal result = calculator.execute(height, CalcMethod.MULTIPLY, height, digit, RoundingMode.HALF_UP);
		result = calculator.execute(result, CalcMethod.MULTIPLY, new BigDecimal(22), digit, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcDiffWeight(BigDecimal before, BigDecimal now) {
		return now.subtract(before).abs().setScale(1, RoundingMode.HALF_UP);
	}

}
