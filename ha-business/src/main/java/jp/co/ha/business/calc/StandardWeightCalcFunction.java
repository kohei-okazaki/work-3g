package jp.co.ha.business.calc;

import java.math.BigDecimal;

/**
 * 標準体重を計算する関数インターフェース
 *
 * @version 1.0.0
 */
@FunctionalInterface
public interface StandardWeightCalcFunction {

    /**
     * 標準体重の計算を行う
     *
     * @param height
     *     身長
     * @param digit
     *     四捨五入桁数
     * @return 計算結果
     */
    BigDecimal apply(BigDecimal height, int digit);
}
