package jp.co.ha.business.healthInfo.prop;

/**
 * 健康情報設定ファイル
 *
 */
public class HealthInfoProperties {

	/** 照会ファイル格納パス */
	private String referenceFilePath;

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

}
