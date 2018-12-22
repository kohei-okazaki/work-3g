package jp.co.ha.tool.build;

import java.io.File;
import java.util.List;
import java.util.Properties;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.reader.PropertyReader;
import jp.co.ha.tool.type.ExecuteType;
import jp.co.ha.tool.type.PropertyType;

/**
 * 基底ビルダー
 *
 */
public abstract class BaseBuilder {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** プロパティファイル */
	private Properties property;

	/** 対象テーブルリスト */
	protected List<String> targetTableList;
	/** 基底ディレクトリ */
	protected String baseDir;

	/**
	 * コンストラクタ
	 */
	public BaseBuilder() {
		this.property = new PropertyReader().getProperty("target.properties");
		this.init();
	}

	/**
	 * 初期処理<br>
	 */
	private void init() {
		String targetTable = getProperty(PropertyType.TARGET_TABLE);

		if (BeanUtil.notNull(targetTable)) {
			this.targetTableList = StringUtil.toStrList(targetTable, StringUtil.COMMA);
		}
		this.baseDir = getProperty(PropertyType.BASE_DIR);
	}

	/**
	 * プロパティファイルから値を取得<br>
	 *
	 * @param propType
	 *     プロパティファイル列挙
	 * @return プロパティファイルの値
	 */
	private String getProperty(PropertyType propType) {
		return this.property.getProperty(propType.getValue());
	}

	/**
	 * excel設定情報を返す<br>
	 *
	 * @return ExcelConfig
	 */
	protected ExcelConfig getExcelConfig() {
		ExcelConfig conf = new ExcelConfig();
		conf.setFilePath("META-INF" + File.separator + "DB.xlsx");
		conf.setSheetName("TABLE_LIST");
		return conf;
	}

	/**
	 * ファイル設定情報を返す<br>
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
