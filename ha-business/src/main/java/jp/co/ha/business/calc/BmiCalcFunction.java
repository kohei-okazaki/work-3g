package jp.co.ha.business.calc;

import java.math.BigDecimal;

/**
 * BMIを計算する関数インターフェース
 *
 * @version 1.0.0
 */
@FunctionalInterface
public interface BmiCalcFunction {

    /**
     * BMIの計算を行う
     *
     * @param height
     *     身長
     * @param weight
     *     体重
     * @param digit
     *     四捨五入桁数
     * @return 計算結果
     */
    BigDecimal apply(BigDecimal height, BigDecimal weight, int digit);
}
