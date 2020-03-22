package jp.co.ha.business.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四則演算オペレータインターフェース
 *
 * @since 1.0
 */
@FunctionalInterface
public interface CalcOperatorFunction {

    /**
     * 四則演算行う
     *
     * @param target1
     *     対象1
     * @param target2
     *     対象2
     * @param digit
     *     区切り
     * @param roundingMode
     *     丸め方法
     * @return 計算結果
     */
    BigDecimal apply(BigDecimal target1, BigDecimal target2, int digit,
            RoundingMode roundingMode);
}
