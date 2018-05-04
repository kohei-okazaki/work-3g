package jp.co.ha.web.file.excel.model;

import jp.co.ha.common.file.excel.annotation.ExcelHeader;
import jp.co.ha.common.file.excel.annotation.ExcelModel;
import jp.co.ha.common.file.excel.model.BaseExcelModel;

/**
 * 健康情報Excel出力モデルクラス<br>
 *
 */
@ExcelModel
@ExcelHeader(names = {"身長", "体重", "BMI", "標準体重"})
public class HealthInfoExcelModel implements BaseExcelModel {

	/** 身長 */
	private String height;
	/** 体重 */
	private String weight;
	/** BMI */
	private String bmi;
	/** 標準体重 */
	private String standardWeight;

	/**
	 * heightを返す
	 * @return height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 * @return weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 * @param weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 * @return bmi
	 */
	public String getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 * @param bmi
	 */
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 * @return standardWeight
	 */
	public String getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 * @param standardWeight
	 */
	public void setStandardWeight(String standardWeight) {
		this.standardWeight = standardWeight;
	}

}
