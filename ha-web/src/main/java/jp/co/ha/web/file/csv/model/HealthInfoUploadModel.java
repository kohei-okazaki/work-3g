package jp.co.ha.web.file.csv.model;

import jp.co.ha.common.file.csv.annotation.CsvModel;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.Mask;

/**
 * 健康情報CSVアップロードモデル<br>
 *
 */
@CsvModel(headerNames = { "userId", "height", "weight" })
public class HealthInfoUploadModel implements BaseCsvModel {

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
	public String getHeight() {
		return height;
	}

	/**
	 * heightを設定する<br>
	 *
	 * @param height
	 *            身長
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
	 *            体重
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

}
