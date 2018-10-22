package jp.co.ha.tool.config;

public class FileConfig {

	/** 出力先ファイルパス */
	private String outputPath;
	/** ファイル名 */
	private String fileName;
	/** ファイル内容 */
	private String data;

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
