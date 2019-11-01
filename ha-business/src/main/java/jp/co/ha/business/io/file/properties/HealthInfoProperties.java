package jp.co.ha.business.io.file.properties;

import org.springframework.stereotype.Component;

/**
 * 健康情報設定ファイル
 * 
 * @since 1.0
 */
@Component
public class HealthInfoProperties {

	/** 照会ファイル格納パス */
	private String referenceFilePath;
	/** 健康情報登録バッチファイルパス */
	private String healthInfoRegistBatchFilePath;

	/**
	 * referenceFilePathを返す
	 *
	 * @return referenceFilePath
	 */
	public String getReferenceFilePath() {
		return referenceFilePath;
	}

	/**
	 * referenceFilePathを設定する
	 *
	 * @param referenceFilePath
	 *     照会ファイル格納パス
	 */
	public void setReferenceFilePath(String referenceFilePath) {
		this.referenceFilePath = referenceFilePath;
	}

	/**
	 * healthInfoRegistBatchFilePathを返す
	 *
	 * @return healthInfoRegistBatchFilePath
	 *
	 */
	public String getHealthInfoRegistBatchFilePath() {
		return healthInfoRegistBatchFilePath;
	}

	/**
	 * healthInfoRegistBatchFilePathを設定する
	 *
	 * @param healthInfoRegistBatchFilePath
	 *     健康情報登録バッチファイルパス
	 *
	 */
	public void setHealthInfoRegistBatchFilePath(String healthInfoRegistBatchFilePath) {
		this.healthInfoRegistBatchFilePath = healthInfoRegistBatchFilePath;
	}

}
