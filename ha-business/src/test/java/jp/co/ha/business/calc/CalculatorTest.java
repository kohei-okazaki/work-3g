package jp.co.ha.business.calc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import jp.co.ha.business.BaseBusinessTest;

/**
 * {@link Calculator} のjUnit
 *
 */
public class CalculatorTest extends BaseBusinessTest {

	@Test
	public void addTest() {
		final BigDecimal ONE = BigDecimal.ONE;
		final BigDecimal TWO = BigDecimal.ONE.add(BigDecimal.ONE);
		assertEquals(new BigDecimal("3"), Calculator.calc(ONE, CalcMethod.ADD, TWO, 0, RoundingMode.HALF_DOWN));
	}
}
