package jp.co.ha.business.healthInfo.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

import jp.co.ha.business.calc.CalcMethod;
import jp.co.ha.business.calc.Calculator;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 計算サービス実装クラス
 *
 */
public class HealthInfoCalcServiceImpl implements HealthInfoCalcService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BiFunction<BigDecimal, BigDecimal, HealthInfoStatus> getHealthInfoStatus() {
		return (inputWeight, beforeWeight) -> {
			HealthInfoStatus status;
			if (beforeWeight.compareTo(inputWeight) == 0) {
				status = HealthInfoStatus.EVEN;
			} else if (beforeWeight.compareTo(inputWeight) == -1) {
				status = HealthInfoStatus.INCREASE;
			} else {
				status = HealthInfoStatus.DOWN;
			}
			return status;
		};
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
		BigDecimal multiplyResult = Calculator.calc(height, CalcMethod.MULTIPLY, height, digit, RoundingMode.HALF_UP);
		BigDecimal result = Calculator.calc(weight, CalcMethod.DIVIDE, multiplyResult, digit, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcStandardWeight(BigDecimal height, int digit) {
		// height * height
		BigDecimal result = Calculator.calc(height, CalcMethod.MULTIPLY, height, digit, RoundingMode.HALF_UP);
		// result * 22
		result = Calculator.calc(result, CalcMethod.MULTIPLY, new BigDecimal(22), digit, RoundingMode.HALF_UP);
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
