package jp.co.ha.business.healthInfo.service.impl;

import static jp.co.ha.business.healthInfo.type.HealthInfoStatus.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 健康情報計算サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoCalcServiceImpl implements HealthInfoCalcService {

    @Override
    public HealthInfoStatus getHealthInfoStatus(BigDecimal inputWeight,
            BigDecimal beforeWeight) {

        HealthInfoStatus status;
        if (beforeWeight.compareTo(inputWeight) == 0) {
            status = EVEN;
        } else if (beforeWeight.compareTo(inputWeight) == -1) {
            status = INCREASE;
        } else {
            status = DOWN;
        }
        return status;
    }

    @Override
    public BigDecimal calcDiffWeight(BigDecimal before, BigDecimal now) {
        return now.subtract(before).abs().setScale(1, RoundingMode.HALF_UP);
    }

}
