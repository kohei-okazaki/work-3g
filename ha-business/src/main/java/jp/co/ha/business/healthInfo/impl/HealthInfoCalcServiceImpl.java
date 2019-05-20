package jp.co.ha.business.healthInfo.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import jp.co.ha.business.calc.BmiCalcFunction;
import jp.co.ha.business.calc.CalcMethod;
import jp.co.ha.business.calc.Calculator;
import jp.co.ha.business.calc.StandardWeightCalcFunction;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 計算サービス実装クラス
 *
 */
@Service
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
	public Function<BigDecimal, BigDecimal> convertMeterFromCentiMeter() {
		return e -> e.scaleByPowerOfTen(-2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit) {
		BmiCalcFunction function = (h, w, d) -> {
			// height * height
			BigDecimal multiplyResult = Calculator.calc(h, CalcMethod.MULTIPLY, h, d, RoundingMode.HALF_UP);
			// weight / multiplyResult
			BigDecimal result = Calculator.calc(w, CalcMethod.DIVIDE, multiplyResult, d, RoundingMode.HALF_UP);
			return result;
		};
		return function.apply(height, weight, digit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcStandardWeight(BigDecimal height, int digit) {
		StandardWeightCalcFunction function = (h, d) -> {
			// height * height
			BigDecimal result = Calculator.calc(h, CalcMethod.MULTIPLY, h, d, RoundingMode.HALF_UP);
			// result * 22
			return Calculator.calc(result, CalcMethod.MULTIPLY, new BigDecimal(22), d, RoundingMode.HALF_UP);
		};
		return function.apply(height, digit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcDiffWeight(BigDecimal before, BigDecimal now) {
		return now.subtract(before).abs().setScale(1, RoundingMode.HALF_UP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcBaseMetabolism(CalorieCalcDto calorieCalcDto) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
