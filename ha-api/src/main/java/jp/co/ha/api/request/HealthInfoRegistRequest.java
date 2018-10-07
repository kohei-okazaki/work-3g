package jp.co.ha.api.request;

import java.math.BigDecimal;

import jp.co.ha.business.api.request.BaseApiRequest;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報登録リクエストクラス<br>
 *
 */
public class HealthInfoRegistRequest extends BaseApiRequest {

	/** 身長 */
	@Mask
	private BigDecimal height;
	/** 体重 */
	@Mask
	private BigDecimal weight;

	/**
	 * heightを返す<br>
	 *
	 * @return height 身長
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する<br>
	 *
	 * @param height
	 *     身長
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す<br>
	 *
	 * @return weight 体重
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する<br>
	 *
	 * @param weight
	 *     体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
