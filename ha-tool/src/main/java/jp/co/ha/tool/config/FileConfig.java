package jp.co.ha.tool.config;

public class FileConfig {

	private String outputPath;
	private String fileName;
	private String data;

	/**
	 * outputPathを返す
	 * @return outputPath
	 */
	public String getOutputPath() {
		return outputPath;
	}
	/**
	 * outputPathを設定する
	 * @param outputPath
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	/**
	 * fileNameを返す
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * fileNameを設定する
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * dataを返す
	 * @return data
	 */
	public String getData() {
		return data;
	}
	/**
	 * dataを設定する
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}


}
