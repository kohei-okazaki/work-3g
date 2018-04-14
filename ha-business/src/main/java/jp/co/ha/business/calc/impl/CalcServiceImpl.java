package jp.co.ha.business.calc.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import jp.co.ha.business.calc.CalcService;
import jp.co.ha.common.other.CalcMethod;
import jp.co.ha.common.util.CalcUtil;

@Service
public class CalcServiceImpl implements CalcService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcBmi(BigDecimal height, BigDecimal weight, int digit) {
		BigDecimal multiplyResult = CalcUtil.execute(height, CalcMethod.MULTIPLY, height, 2, RoundingMode.HALF_UP);
		BigDecimal result = CalcUtil.execute(weight, CalcMethod.DIVIDE, multiplyResult, 2, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calcStandardWeight(BigDecimal height, int digit) {
		BigDecimal result = CalcUtil.execute(height, CalcMethod.MULTIPLY, height, digit, RoundingMode.HALF_UP);
		result = CalcUtil.execute(result, CalcMethod.MULTIPLY, new BigDecimal(22), digit, RoundingMode.HALF_UP);
		return result;
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

}
