package jp.co.ha.business.api.request;

import java.math.BigDecimal;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 健康情報登録リクエストクラス
 *
 */
public class HealthInfoRegistRequest extends CommonApiRequest {

	/** 身長 */
	@Mask
	@Required(message = "heightが未設定です")
	@Pattern(regixPattern = RegixType.DECIMAL)
	private BigDecimal height;
	/** 体重 */
	@Mask
	@Required(message = "weightが未設定です")
	@Pattern(regixPattern = RegixType.DECIMAL)
	private BigDecimal weight;

	/**
	 * heightを返す
	 *
	 * @return height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height
	 *     身長
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 *
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 *
	 * @param weight
	 *     体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
