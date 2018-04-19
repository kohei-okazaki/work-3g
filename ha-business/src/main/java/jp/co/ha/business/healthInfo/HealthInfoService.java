package jp.co.ha.business.healthInfo;

import java.math.BigDecimal;

/**
 * 健康情報サービス<br>
 *
 */
public interface HealthInfoService {

	/**
	 * inputWeightとbeforeWeightを比較し、ユーザステータスを返す<br>
	 * @param inputWeight
	 * @param beforeWeight
	 * @return
	 */
	String getUserStatus(BigDecimal inputWeight, BigDecimal beforeWeight);
}
