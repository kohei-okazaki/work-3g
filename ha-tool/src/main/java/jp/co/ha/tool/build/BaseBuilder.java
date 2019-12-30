package jp.co.ha.tool.build;

import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.reader.ExcelReader;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

/**
 * 基底Builder<br>
 * すべての自動生成のメイン処理を定義するBuilderは本クラスを継承する
 *
 * @since 1.0
 */
public abstract class BaseBuilder {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** 対象テーブルリスト */
	protected List<String> targetTableList;
	/** 基底ディレクトリ */
	protected String baseDir;

	/** ExcelReader */
	protected ExcelReader reader;

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

		String resourcePath = this.getClass().getClassLoader().getResource("").getPath()
				+ FileSeparator.SYSTEM.getValue() + "META-INF";
		Properties prop = new PropertyReader().read(resourcePath, "tool.properties");

		String targetTable = prop.getProperty("tool.targetTable");
		if (BeanUtil.notNull(targetTable)) {
			this.targetTableList = StringUtil.toStrList(targetTable, StringUtil.COMMA);
		}

		this.baseDir = prop.getProperty("tool.baseDir");

		this.reader = new ExcelReader(getExcelConfig());
	}

	/**
	 * excel設定情報を返す
	 *
	 * @return ExcelConfig
	 */
	private ExcelConfig getExcelConfig() {
		ExcelConfig conf = new ExcelConfig();
		StringJoiner sj = new StringJoiner(FileSeparator.SYSTEM.getValue());
		sj.add(this.baseDir).add("ha-resource").add("02_db").add("DB.xlsx");
		conf.setFilePath(sj.toString());
		conf.setSheetName("TABLE_LIST");
		return conf;
	}

	/**
	 * ファイル出力先を返す<br>
	 * 今後SQLを自動生成する場合、ここに出力先を定義する
	 *
	 * @param execType
	 *     実行タイプ
	 * @return 出力先パス
	 */
	protected String getOutputPath(ExecuteType execType) {

		String outputPath = null;
		switch (execType) {
		case DDL:
			outputPath = this.baseDir + "\\ha-resource\\02_db\\ddl";
			break;
		case ENTITY:
			outputPath = this.baseDir + "\\ha-tool\\src\\main\\java\\jp\\co\\ha\\tool\\source";
			break;
		case DROP:
			outputPath = this.baseDir + "\\ha-resource\\02_db\\drop";
			break;
		case DML:
			outputPath = this.baseDir + "\\ha-resource\\02_db\\dml";
			break;
		case TABLE_DEFINE:
			outputPath = this.baseDir + "\\ha-resource\\02_db\\others";
			break;
		default:
			LOG.warn("SQL生成の指定が間違っています execType:" + execType.getValue());
			break;
		}
		return outputPath;
	}

	/**
	 * 対象のテーブルかどうか判定
	 *
	 * @param row
	 *     excelの行情報
	 * @param tableName
	 *     物理テーブル名
	 * @return 判定結果
	 */
	protected boolean isTargetTable(Row row, String tableName) {
		Cell cell = row.getCell(CellPositionType.PHYSICAL_NAME);
		return tableName.equals(cell.getValue());
	}

}
