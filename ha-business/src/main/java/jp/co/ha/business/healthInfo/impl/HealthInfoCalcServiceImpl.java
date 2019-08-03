package jp.co.ha.business.healthInfo.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import jp.co.ha.business.calc.BmiCalcFunction;
import jp.co.ha.business.calc.CalcMethod;
import jp.co.ha.business.calc.Calculator;
import jp.co.ha.business.calc.StandardWeightCalcFunction;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.GenderType;
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
	public HealthInfoStatus getHealthInfoStatus(BigDecimal inputWeight, BigDecimal beforeWeight) {

		HealthInfoStatus status;
		if (beforeWeight.compareTo(inputWeight) == 0) {
			status = HealthInfoStatus.EVEN;
		} else if (beforeWeight.compareTo(inputWeight) == -1) {
			status = HealthInfoStatus.INCREASE;
		} else {
			status = HealthInfoStatus.DOWN;
		}
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal convertMeterFromCentiMeter(BigDecimal centiMeter) {
		return centiMeter.scaleByPowerOfTen(-2);
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

		BigDecimal WEIGHT_ADJUST_VALUE = null;
		BigDecimal HEIGHT_ADJUST_VALUE = null;
		BigDecimal AGE_ADJUST_VALUE = null;
		BigDecimal ADJUST_VALUE = null;

		if (GenderType.MALE == calorieCalcDto.getGenderType()) {

			WEIGHT_ADJUST_VALUE = new BigDecimal("13.397");
			HEIGHT_ADJUST_VALUE = new BigDecimal("4.799");
			AGE_ADJUST_VALUE = new BigDecimal("5.677");
			ADJUST_VALUE = new BigDecimal("88.362");

		} else {

			WEIGHT_ADJUST_VALUE = new BigDecimal("9.247");
			HEIGHT_ADJUST_VALUE = new BigDecimal("3.098");
			AGE_ADJUST_VALUE = new BigDecimal("4.33");
			ADJUST_VALUE = new BigDecimal("447.593");

		}

		BigDecimal calcWeight = Calculator.calc(WEIGHT_ADJUST_VALUE, CalcMethod.MULTIPLY,
				calorieCalcDto.getWeight(), 3, RoundingMode.HALF_UP);
		BigDecimal calcHeight = Calculator.calc(HEIGHT_ADJUST_VALUE, CalcMethod.MULTIPLY,
				calorieCalcDto.getHeight(), 3, RoundingMode.HALF_UP);
		BigDecimal calcAge = Calculator.calc(AGE_ADJUST_VALUE, CalcMethod.MULTIPLY,
				BigDecimal.valueOf(calorieCalcDto.getAge()), 3, RoundingMode.HALF_UP);

		BigDecimal result = null;
		result = Calculator.calc(calcWeight, CalcMethod.ADD, calcHeight, 3, RoundingMode.HALF_UP);
		result = Calculator.calc(result, CalcMethod.SUBTRACT, calcAge, 3, RoundingMode.HALF_UP);
		result = Calculator.calc(result, CalcMethod.ADD, ADJUST_VALUE, 3, RoundingMode.HALF_UP);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcLostCaloriePerDay(CalorieCalcDto calorieCalcDto) {
		return Calculator.calc(calorieCalcDto.getBaseMetabolism(), CalcMethod.ADD,
				calorieCalcDto.getLifeWorkMetabolism(), 2, RoundingMode.HALF_UP);
	}

}
