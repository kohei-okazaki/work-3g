package jp.co.ha.tool.config;

public class ExcelConfig {

	/** ファイルパス */
	private String filePath;
	/** シート名 */
	private String sheetName;

	/**
	 * filePathを返す
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * filePathを設定する
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * sheetNameを返す
	 * @return sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}
	/**
	 * sheetNameを設定する
	 * @param sheetName
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

}
