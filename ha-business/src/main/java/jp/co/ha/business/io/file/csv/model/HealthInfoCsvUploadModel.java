package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 健康情報CSVアップロードモデル
 *
 * @since 1.0
 */
public class HealthInfoCsvUploadModel implements BaseCsvModel {

	/** ユーザID */
	@Required(message = "ユーザIDが未指定です")
	private String userId;
	/** 身長 */
	@Mask
	@Required(message = "身長が未指定です")
	@Pattern(regixPattern = RegexType.DECIMAL, message = "身長が半角数字とピリオドでありません")
	private String height;
	/** 体重 */
	@Mask
	@Required(message = "体重が未指定です")
	@Pattern(regixPattern = RegexType.DECIMAL, message = "体重が半角数字とピリオドでありません")
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
