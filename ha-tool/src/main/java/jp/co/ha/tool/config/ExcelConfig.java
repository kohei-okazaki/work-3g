package jp.co.ha.tool.config;

/**
 * Excel設定情報保持クラス
 * 
 * @since 1.0
 */
public class ExcelConfig {

	/** ファイルパス */
	private String filePath;
	/** シート名 */
	private String sheetName;

	/**
	 * filePathを返す
	 *
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * filePathを設定する
	 *
	 * @param filePath
	 *     ファイルパス
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * sheetNameを返す
	 *
	 * @return sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * sheetNameを設定する
	 *
	 * @param sheetName
	 *     シート名
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

}
