package jp.co.ha.batch.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 健康情報登録情報
 *
 */
public class HealthInfoRegistData {

	/** ユーザID */
	private String userId;
	/** APIキー */
	private String apiKey;
	/** 健康情報要求情報 */
	private List<HealthInfoRequestData> healthInfoRequestData;

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
	 * apiKeyを返す
	 *
	 * @return apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * apiKeyを設定する
	 *
	 * @param apiKey
	 *     APIキー
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * healthInfoRequestDataを返す
	 *
	 * @return healthInfoRequestData
	 */
	public List<HealthInfoRequestData> getHealthInfoRequestData() {
		return healthInfoRequestData;
	}

	/**
	 * healthInfoRequestDataを設定する
	 *
	 * @param healthInfoRequestData
	 *     健康情報要求情報
	 */
	public void setHealthInfoRequestData(List<HealthInfoRequestData> healthInfoRequestData) {
		this.healthInfoRequestData = healthInfoRequestData;
	}

	/**
	 * 健康情報必須情報
	 *
	 */
	public static class HealthInfoRequestData {

		/** 身長 */
		private BigDecimal height;
		/** 体重 */
		private BigDecimal weight;

		/**
		 * heightを返す
		 *
		 * @return height
		 */
		public BigDecimal getHeight() {
			return height;
		}

		/**
		 * heightを設定する
		 *
		 * @param height
		 *     身長
		 */
		public void setHeight(BigDecimal height) {
			this.height = height;
		}

		/**
		 * weightを返す
		 *
		 * @return weight
		 */
		public BigDecimal getWeight() {
			return weight;
		}

		/**
		 * weightを設定する
		 *
		 * @param weight
		 *     体重
		 */
		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}

	}
}
