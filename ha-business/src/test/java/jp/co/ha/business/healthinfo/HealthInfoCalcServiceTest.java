package jp.co.ha.business.healthinfo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.BaseBusinessTest;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * {@linkplain HealthInfoCalcService} のjUnit
 *
 * @version 1.0.0
 */
public class HealthInfoCalcServiceTest extends BaseBusinessTest {

    /** 健康情報計算サービス */
    @Autowired
    private HealthInfoCalcService service;

    /**
     * {@linkplain HealthInfoCalcService#getHealthInfoStatus}
     */
    @Test
    public void getHealthInfoStatusTest() {
        {
            // 10
            BigDecimal inputWeight = BigDecimal.TEN;
            // 10
            BigDecimal beforeWeight = BigDecimal.TEN;
            assertEquals(HealthInfoStatus.EVEN,
                    service.getHealthInfoStatus(inputWeight, beforeWeight));
        }
        {
            // 9
            BigDecimal inputWeight = BigDecimal.TEN.subtract(BigDecimal.ONE);
            // 10
            BigDecimal beforeWeight = BigDecimal.TEN;
            assertEquals(HealthInfoStatus.DOWN,
                    service.getHealthInfoStatus(inputWeight, beforeWeight));
        }
        {
            // 10
            BigDecimal inputWeight = BigDecimal.TEN;
            // 9
            BigDecimal beforeWeight = BigDecimal.TEN.subtract(BigDecimal.ONE);
            assertEquals(HealthInfoStatus.INCREASE,
                    service.getHealthInfoStatus(inputWeight, beforeWeight));
        }
    }

}
