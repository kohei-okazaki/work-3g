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
	 * @return dataId
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * dataIdを設定する<br>
	 * @param dataId
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
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

	/**
	 * bmiを返す<br>
	 * @return bmi
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する<br>
	 * @param bmi
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す<br>
	 * @return standardWeight
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する<br>
	 * @param standardWeight
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * regDateを返す<br>
	 * @return regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する<br>
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
