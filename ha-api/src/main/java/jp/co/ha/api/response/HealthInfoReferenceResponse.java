package jp.co.ha.api.response;

import java.math.BigDecimal;

import jp.co.ha.common.api.BaseResponse;

/**
 * 健康情報照会レスポンスクラス<br>
 *
 */
public class HealthInfoReferenceResponse extends BaseResponse {

	/** データID */
	private String dataId;
	/** ユーザID */
	private String userId;
	/** 身長 */
	private BigDecimal height;
	/** 体重 */
	private BigDecimal weight;
	/** BMI */
	private BigDecimal bmi;
	/** 標準体重 */
	private BigDecimal standardWeight;
	/** 登録日時 */
	private String regDate;

	/**
	 * dataIdを返す<br>
	 *
	 * @return dataId データID
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * dataIdを設定する<br>
	 *
	 * @param dataId
	 *            データID
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * userIdを返す<br>
	 *
	 * @return userId userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する<br>
	 *
	 * @param userId
	 *            userId
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

	/**
	 * bmiを返す<br>
	 *
	 * @return bmi BMI
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する<br>
	 *
	 * @param bmi
	 *            BMI
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す<br>
	 *
	 * @return standardWeight 標準体重
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する<br>
	 *
	 * @param standardWeight
	 *            標準体重
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * regDateを返す<br>
	 *
	 * @return regDate 登録日時
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する<br>
	 *
	 * @param regDate
	 *            登録日時
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
