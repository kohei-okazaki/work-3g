package jp.co.ha.business.healthinfo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.BaseBusinessTest;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * {@link HealthInfoCalcService} のjUnit
 *
 */
public class HealthInfoCalcServiceTest extends BaseBusinessTest {

	/** 健康情報計算サービス */
	@Autowired
	private HealthInfoCalcService service;

	/**
	 * {@link HealthInfoCalcService#getHealthInfoStatus}
	 */
	@Test
	public void getHealthInfoStatusTest() {
		{
			// 10
			BigDecimal inputWeight = BigDecimal.TEN;
			// 10
			BigDecimal beforeWeight = BigDecimal.TEN;
			assertEquals(HealthInfoStatus.EVEN, service.getHealthInfoStatus(inputWeight, beforeWeight));
		}
		{
			// 9
			BigDecimal inputWeight = BigDecimal.TEN.subtract(BigDecimal.ONE);
			// 10
			BigDecimal beforeWeight = BigDecimal.TEN;
			assertEquals(HealthInfoStatus.DOWN, service.getHealthInfoStatus(inputWeight, beforeWeight));
		}
		{
			// 10
			BigDecimal inputWeight = BigDecimal.TEN;
			// 9
			BigDecimal beforeWeight = BigDecimal.TEN.subtract(BigDecimal.ONE);
			assertEquals(HealthInfoStatus.INCREASE, service.getHealthInfoStatus(inputWeight, beforeWeight));
		}
	}

	/**
	 * {@link HealthInfoCalcService#convertMeterFromCentiMeter}
	 */
	@Test
	public void convertMeterFromCentiMeterTest() {
		// 100cm
		BigDecimal centiMeter = BigDecimal.TEN.multiply(BigDecimal.TEN);
		assertEquals(new BigDecimal("1.00"), service.convertMeterFromCentiMeter(centiMeter));
	}
}
