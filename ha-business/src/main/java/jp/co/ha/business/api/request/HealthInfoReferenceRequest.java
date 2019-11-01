package jp.co.ha.business.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 健康情報照会リクエストクラス
 * 
 * @since 1.0
 */
public class HealthInfoReferenceRequest extends CommonApiRequest {

	/** 健康情報ID */
	@Required(message = "healthInfoIdが未設定です")
	@Pattern(regixPattern = RegixType.HALF_NUMBER, message = "healthInfoIdが半角数字でありません")
	@JsonProperty("healthInfoId")
	private Integer healthInfoId;

	/**
	 * healthInfoIdを返す
	 *
	 * @return healthInfoId
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

}
