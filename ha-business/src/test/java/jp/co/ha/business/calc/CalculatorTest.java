package jp.co.ha.business.calc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import jp.co.ha.business.BaseBusinessTest;
import jp.co.ha.business.calc.Calculator.CalcMethod;

/**
 * {@linkplain Calculator} のjUnit
 *
 * @version 1.0.0
 */
public class CalculatorTest extends BaseBusinessTest {

    /**
     * {@linkplain Calculator#calc}
     */
    @Test
    public void addTest() {
        final BigDecimal ONE = BigDecimal.ONE;
        final BigDecimal TWO = BigDecimal.ONE.add(BigDecimal.ONE);
        assertEquals(new BigDecimal("3"),
                Calculator.calc(ONE, CalcMethod.ADD, TWO, 0, RoundingMode.HALF_DOWN));
    }
}
