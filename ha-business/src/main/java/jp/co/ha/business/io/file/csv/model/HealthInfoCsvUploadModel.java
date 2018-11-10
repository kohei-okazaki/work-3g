package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報CSVアップロードモデル<br>
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
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * heightを返す<br>
	 *
	 * @return height 身長
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * heightを設定する<br>
	 *
	 * @param height
	 *     身長
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * weightを返す<br>
	 *
	 * @return weight 体重
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * weightを設定する<br>
	 *
	 * @param weight
	 *     体重
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

}
