package jp.co.ha.web.file.excel.model;

import java.util.Date;

import jp.co.ha.common.file.excel.annotation.ExcelDownloadModel;
import jp.co.ha.common.file.excel.model.BaseExcelModel;

/**
 * 結果照会Excel出力モデルクラス<br>
 *
 */
@ExcelDownloadModel(sheetName = "健康情報照会"
					, headerNames = { "身長", "体重", "BMI", "標準体重", "登録日時" }
					, footerNames = { "身長", "体重", "BMI", "標準体重", "登録日時" })
public class ReferenceExcelModel implements BaseExcelModel {

	/** 身長 */
	private String height;
	/** 体重 */
	private String weight;
	/** BMI */
	private String bmi;
	/** 標準体重 */
	private String standardWeight;
	/** 登録日時 */
	private Date regDate;

	/**
	 * heightを返す
	 *
	 * @return height 身長
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height
	 *     身長
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 *
	 * @return weight 体重
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 *
	 * @param weight
	 *     体重
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 *
	 * @return bmi BMI
	 */
	public String getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 *
	 * @param bmi
	 *     BMI
	 */
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 *
	 * @return standardWeight 標準体重
	 */
	public String getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 *
	 * @param standardWeight
	 *     標準体重
	 */
	public void setStandardWeight(String standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * regDateを返す<br>
	 *
	 * @return regDate regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する<br>
	 *
	 * @param regDate
	 *     regDate
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
