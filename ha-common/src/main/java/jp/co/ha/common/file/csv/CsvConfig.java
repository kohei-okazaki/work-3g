package jp.co.ha.common.file.csv;

/**
 * CSV設定情報保持クラス<br>
 * 今後、追加するCSV設定情報は本クラスで定義する<br>
 *
 */
public class CsvConfig {

	/** ファイル名 */
	private String fileName;
	/** ヘッダー有無 */
	private boolean hasHeader;
	/** 囲い文字有無 */
	private boolean hasEnclosure;
	/** 囲い文字 */
	private String enclosureChar;

	/**
	 * fileNameを返す<br>
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * fileNameを設定する<br>
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * hasHeaderを返す<br>
	 * @return hasHeader
	 */
	public boolean isHasHeader() {
		return hasHeader;
	}
	/**
	 * hasHeaderを設定する<br>
	 * @param hasHeader
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	/**
	 * hasEnclosureを返す<br>
	 * @return hasEnclosure
	 */
	public boolean isHasEnclosure() {
		return hasEnclosure;
	}
	/**
	 * hasEnclosureを設定する<br>
	 * @param hasEnclosure
	 */
	public void setHasEnclosure(boolean hasEnclosure) {
		this.hasEnclosure = hasEnclosure;
	}
	/**
	 * enclosureCharを返す<br>
	 * @return enclosureChar
	 */
	public String getEnclosureChar() {
		return enclosureChar;
	}
	/**
	 * enclosureCharを設定する<br>
	 * @param enclosureChar
	 */
	public void setEnclosureChar(String enclosureChar) {
		this.enclosureChar = enclosureChar;
	}
}
