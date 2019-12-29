package jp.co.ha.common.io.file.csv;

import jp.co.ha.common.function.Builder;
import jp.co.ha.common.type.Charset;

/**
 * CSV設定情報保持クラス<br>
 * 今後、追加するCSV設定情報は本クラスで定義する
 *
 * @since 1.0
 */
public class CsvConfig {

	/** ファイル名 */
	private final String fileName;
	/** ファイル出力パス */
	private final String outputPath;
	/** ヘッダ有無 */
	private final boolean hasHeader;
	/** 囲い文字有無 */
	private final boolean hasEnclosure;
	/** 囲い文字 */
	private final CsvFileChar csvFileChar;
	/** 文字コード */
	private final Charset charset;
	/** フッタ有無 */
	private final boolean hasFooter;
	/** マスク利用有無 */
	private final boolean useMask;

	/**
	 * CsvConfigのビルダー
	 *
	 * @since 1.0
	 */
	public static class CsvConfigBuilder implements Builder<CsvConfig> {

		/* 必須項目 */
		/** ファイル名 */
		private final String fileName;
		/** ファイル出力パス */
		private final String outputPath;

		/* 任意項目 */
		/** ヘッダ有無 */
		private boolean hasHeader = false;
		/** 囲い文字有無 */
		private boolean hasEnclosure = false;
		/** 囲い文字 */
		private CsvFileChar csvFileChar = CsvFileChar.DOBBLE_QUOTE;
		/** 文字コード */
		private Charset charset = Charset.UTF_8;
		/** フッタ有無 */
		private boolean hasFooter = false;
		/** マスク利用有無 */
		private boolean useMask = false;

		/**
		 * コンストラクタ
		 *
		 * @param fileName
		 *     ファイル名
		 * @param outputPath
		 *     ファイル出力パス
		 */
		public CsvConfigBuilder(String fileName, String outputPath) {
			this.fileName = fileName;
			this.outputPath = outputPath;
		}

		/**
		 * ヘッダ有無を設定
		 *
		 * @param hasHeader
		 *     ヘッダ有無
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder hasHeader(boolean hasHeader) {
			this.hasHeader = hasHeader;
			return this;
		}

		/**
		 * 囲い文字有無を設定
		 *
		 * @param hasEnclosure
		 *     囲い文字有無
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder hasEnclosure(boolean hasEnclosure) {
			this.hasEnclosure = hasEnclosure;
			return this;
		}

		/**
		 * 囲い文字を設定
		 *
		 * @param csvFileChar
		 *     囲い文字
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder csvFileChar(CsvFileChar csvFileChar) {
			this.csvFileChar = csvFileChar;
			return this;
		}

		/**
		 * 文字コードを設定
		 *
		 * @param charset
		 *     文字コード
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder charset(Charset charset) {
			this.charset = charset;
			return this;
		}

		/**
		 * フッタ有無を設定
		 *
		 * @param hasFooter
		 *     フッタ有無
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder hasFooter(boolean hasFooter) {
			this.hasFooter = hasFooter;
			return this;
		}

		/**
		 * マスク利用有無を設定
		 *
		 * @param useMask
		 *     マスク利用有無
		 * @return CsvConfigBuilder
		 */
		public CsvConfigBuilder useMask(boolean useMask) {
			this.useMask = useMask;
			return this;
		}

		@Override
		public CsvConfig build() {
			return new CsvConfig(this);
		}

	}

	/**
	 * コンストラクタ<br>
	 * ビルダーからのみインスタンスの生成を行うためprivateにする
	 *
	 * @param builder
	 *     CsvConfigのビルダー
	 */
	private CsvConfig(CsvConfigBuilder builder) {
		this.fileName = builder.fileName;
		this.outputPath = builder.outputPath;
		this.hasHeader = builder.hasHeader;
		this.hasEnclosure = builder.hasEnclosure;
		this.csvFileChar = builder.csvFileChar;
		this.charset = builder.charset;
		this.hasFooter = builder.hasFooter;
		this.useMask = builder.useMask;
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
	 * outputPathを返す
	 *
	 * @return outputPath
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * hasHeaderを返す
	 *
	 * @return hasHeader
	 */
	public boolean hasHeader() {
		return hasHeader;
	}

	/**
	 * hasEnclosureを返す
	 *
	 * @return hasEnclosure
	 */
	public boolean hasEnclosure() {
		return hasEnclosure;
	}

	/**
	 * csvFileCharを返す
	 *
	 * @return csvFileChar
	 */
	public CsvFileChar getCsvFileChar() {
		return csvFileChar;
	}

	/**
	 * charsetを返す
	 *
	 * @return charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * hasFooterを返す
	 *
	 * @return hasFooter
	 */
	public boolean hasFooter() {
		return hasFooter;
	}

	/**
	 * useMaskを返す
	 *
	 * @return useMask
	 */
	public boolean useMask() {
		return useMask;
	}

}
