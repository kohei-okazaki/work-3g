package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報CSVアップロードモデル
 *
 */
public class HealthInfoCsvUploadModel implements BaseCsvModel {

	/** ユーザID */
	private String userId;
	/** 身長 */
	@Mask
	private String height;
	/** 体重 */
	@Mask
	private String weight;

	/**
	 * userIdを返す
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * heightを返す
	 *
	 * @return height
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
	 * @return weight
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

}
