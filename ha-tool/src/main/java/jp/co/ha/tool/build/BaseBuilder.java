package jp.co.ha.tool.build;

import java.util.List;
import java.util.Properties;

import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.type.ExecuteType;

/**
 * 基底ビルダー
 *
 */
public abstract class BaseBuilder {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** 対象テーブルリスト */
	protected List<String> targetTableList;
	/** 基底ディレクトリ */
	protected String baseDir;

	/**
	 * コンストラクタ
	 */
	public BaseBuilder() {
		this.init();
	}

	/**
	 * 初期処理
	 */
	private void init() {
		String resourcePath = this.getClass().getClassLoader().getResource("").getPath() + FileSeparator.SYSTEM.getValue() + "META-INF";
		Properties prop = new PropertyReader().read(resourcePath, "target.properties");

		String targetTable = prop.getProperty("targetTable");
		if (BeanUtil.notNull(targetTable)) {
			this.targetTableList = StringUtil.toStrList(targetTable, StringUtil.COMMA);
		}

		this.baseDir = prop.getProperty("baseDir");
	}

	/**
	 * excel設定情報を返す
	 *
	 * @return ExcelConfig
	 */
	protected ExcelConfig getExcelConfig() {
		ExcelConfig conf = new ExcelConfig();
		conf.setFilePath("META-INF" + FileSeparator.SYSTEM.getValue() + "DB.xlsx");
		conf.setSheetName("TABLE_LIST");
		return conf;
	}

	/**
	 * ファイル設定情報を返す
	 *
	 * @param execType
	 *     実行タイプ
	 * @return FileConfig
	 */
	protected FileConfig getFileConfig(ExecuteType execType) {
		FileConfig conf = new FileConfig();
		if (execType == ExecuteType.DDL) {
			conf.setOutputPath(this.baseDir + "\\ha-resource\\db\\ddl");
		} else if (execType == ExecuteType.ENTITY) {
			conf.setOutputPath(this.baseDir + "\\ha-tool\\src\\main\\java\\jp\\co\\ha\\tool\\source");
		} else if (execType == ExecuteType.DROP) {
			conf.setOutputPath(this.baseDir + "\\ha-resource\\db\\drop");
		}
		return conf;
	}

	/**
	 * メイン処理
	 */
	protected abstract void execute();
}
