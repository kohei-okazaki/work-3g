package jp.co.ha.business.healthInfo.service;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.HealthInfoStatus;

/**
 * 健康情報計算サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoCalcService {

    /**
     * 健康情報ステータスを返す
     *
     * @param inputWeight
     *     入力値
     * @param beforeWeight
     *     以前の体重
     * @return 健康情報ステータス
     */
    HealthInfoStatus getHealthInfoStatus(BigDecimal inputWeight, BigDecimal beforeWeight);

    /**
     * 最後に入力した体重と今の体重の差を計算
     *
     * @param before
     *     前の体重
     * @param now
     *     今の体重
     * @return 体重の差(小数第2位を四捨五入する)
     */
    BigDecimal calcDiffWeight(BigDecimal before, BigDecimal now);

}
