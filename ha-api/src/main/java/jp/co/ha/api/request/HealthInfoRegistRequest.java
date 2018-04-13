package jp.co.ha.api.request;

import java.math.BigDecimal;

import jp.co.ha.common.api.BaseRequest;

/**
 * 健康情報リクエストクラス<br>
 *
 */
public class HealthInfoRegistRequest extends BaseRequest {

	/** ユーザID */
	private String userId;
	/** 身長 */
	private BigDecimal height;
	/** 体重 */
	private BigDecimal weight;

	/**
	 * userIdを返す<br>
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * userIdを設定する<br>
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * heightを返す<br>
	 * @return height
	 */
	public BigDecimal getHeight() {
		return height;
	}
	/**
	 * heightを設定する<br>
	 * @param height
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	/**
	 * weightを返す<br>
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * weightを設定する<br>
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
