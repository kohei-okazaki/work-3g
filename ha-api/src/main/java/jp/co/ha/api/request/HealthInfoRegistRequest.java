package jp.co.ha.api.request;

import java.math.BigDecimal;

import jp.co.ha.common.api.BaseRequest;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報登録リクエストクラス<br>
 *
 */
public class HealthInfoRegistRequest extends BaseRequest {

	/** ユーザID */
	private String userId;
	/** 身長 */
	@Mask
	private BigDecimal height;
	/** 体重 */
	@Mask
	private BigDecimal weight;

	/**
	 * userIdを返す<br>
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する<br>
	 *
	 * @param userId
	 *            ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
	 *            身長
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
	 *            体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
