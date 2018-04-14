package jp.co.ha.web.file.excel.model;

import java.math.BigDecimal;

import jp.co.ha.common.file.excel.annotation.ExcelHeader;
import jp.co.ha.common.file.excel.annotation.ExcelModel;
import jp.co.ha.common.file.excel.model.BaseExcelModel;

/**
 * 結果照会Excel出力モデルクラス<br>
 *
 */
@ExcelModel
@ExcelHeader(names = {"身長", "体重", "BMI", "標準体重"})
public class ReferenceExcelModel implements BaseExcelModel {

	/** 身長 */
	private BigDecimal height;
	/** 体重 */
	private BigDecimal weight;
	/** BMI */
	private BigDecimal bmi;
	/** 標準体重 */
	private BigDecimal standardWeight;

	/**
	 * heightを返す
	 * @return height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 * @param height
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 * @return bmi
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 * @param bmi
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 * @return standardWeight
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 * @param standardWeight
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

}
