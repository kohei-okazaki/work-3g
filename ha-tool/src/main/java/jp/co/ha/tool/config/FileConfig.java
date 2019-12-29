package jp.co.ha.tool.config;

import jp.co.ha.common.function.Builder;

/**
 * ファイル設定情報保持クラス
 *
 * @since 1.0
 */
public class FileConfig {

	/** 出力先ファイルパス */
	private final String outputPath;
	/** ファイル名 */
	private final String fileName;
	/** ファイル内容 */
	private final String data;

	/**
	 * プライベートコンストラクタ<br>
	 * ビルダーからのみインスタンスの生成を行うためprivateにする
	 *
	 * @param builder
	 *     FileConfigBuilder
	 */
	private FileConfig(FileConfigBuilder builder) {
		this.outputPath = builder.outputPath;
		this.fileName = builder.fileName;
		this.data = builder.data;
	}

	/**
	 * FileConfigのビルダー
	 *
	 * @since 1.0
	 */
	public static class FileConfigBuilder implements Builder<FileConfig> {

		/* 必須項目 */
		/** 出力先ファイルパス */
		private String outputPath;
		/** ファイル名 */
		private String fileName;
		/** ファイル内容 */
		private String data;

		/**
		 * コンストラクタ
		 *
		 * @param outputPath
		 *     出力先ファイルパス
		 * @param fileName
		 *     ファイル名
		 * @param data
		 *     ファイル内容
		 */
		public FileConfigBuilder(String outputPath, String fileName, String data) {
			this.outputPath = outputPath;
			this.fileName = fileName;
			this.data = data;
		}

		@Override
		public FileConfig build() {
			return new FileConfig(this);
		}

	}

	/**
	 * outputPathを返す
	 *
	 * @return outputPath
	 */
	public String getOutputPath() {
		return outputPath;
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
	 * dataを返す
	 *
	 * @return data
	 */
	public String getData() {
		return data;
	}

}
