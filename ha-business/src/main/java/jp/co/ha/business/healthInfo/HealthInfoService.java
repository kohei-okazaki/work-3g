package jp.co.ha.business.healthInfo;

import java.math.BigDecimal;

public interface HealthInfoService {

	/**
	 * inputWeightとbeforeWeightを比較し、ユーザステータスを返す<br>
	 * @param inputWeight
	 * @param beforeWeight
	 * @return
	 */
	String getUserStatus(BigDecimal inputWeight, BigDecimal beforeWeight);
}
