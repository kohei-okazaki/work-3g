package jp.co.ha.web.file.csv.model;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.ha.common.file.csv.annotation.CsvModel;
import jp.co.ha.common.file.csv.model.BaseCsvModel;

/**
 * 健康情報CSV出力モデル<br>
 * CSV出力したい項目のみ持つ
 *
 */
@CsvModel(headerNames = { "ユーザID", "身長", "体重", "BMI", "標準体重", "登録日時" })
public class HealthInfoCsvModel implements BaseCsvModel {

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
	private Date regDate;

	/**
	 * userIdを返す
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *            ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * heightを返す
	 *
	 * @return height 身長
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height
	 *            身長
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 *
	 * @return weight 体重
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 *
	 * @param weight
	 *            体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 *
	 * @return bmi BMI
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 *
	 * @param bmi
	 *            BMI
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 *
	 * @return standardWeight 標準体重
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 *
	 * @param standardWeight
	 *            標準体重
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * regDateを返す
	 *
	 * @return regDate 登録日時
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 *
	 * @param regDate
	 *            登録日時
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
