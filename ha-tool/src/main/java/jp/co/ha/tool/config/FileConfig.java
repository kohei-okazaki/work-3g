package jp.co.ha.tool.config;

/**
 * ファイル設定情報保持クラス
 * 
 * @since 1.0
 */
public class FileConfig {

	/** 出力先ファイルパス */
	private String outputPath;
	/** ファイル名 */
	private String fileName;
	/** ファイル内容 */
	private String data;

	/**
	 * outputPathを返す
	 *
	 * @return outputPath
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * outputPathを設定する
	 *
	 * @param outputPath
	 *     出力先ファイルパス
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * fileNameを返す
	 *
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * fileNameを設定する
	 *
	 * @param fileName
	 *     ファイル名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * dataを返す
	 *
	 * @return data
	 */
	public String getData() {
		return data;
	}

	/**
	 * dataを設定する
	 *
	 * @param data
	 *     ファイル内容
	 */
	public void setData(String data) {
		this.data = data;
	}

}
