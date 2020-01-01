package jp.co.ha.tool.build;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.build.annotation.Build;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.config.FileConfig.FileConfigBuilder;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

/**
 * カラム追加のDDLを作成するビルダー<br>
 * <ul>
 * <li>テーブル名:HOGE</li>
 * <li>カラム名:PIYO</li>
 * <li>カラム定義:FUGA</li>
 * </ul>
 * の場合、<br>
 * <code>ALTER TABLE HOGE ADD PIYO FUGA;</code><br>
 * のDDLを作成
 *
 * @since 1.0
 */
public class AddColumnBuilder extends BaseSqlSourceBuilder {

	/**
	 * 実処理
	 *
	 * @return ファイル設定情報
	 */
	@Build
	public FileConfig execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		List<Row> targetRowList = getTargetRowList(excel.getRowList());

		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);

		targetRowList.stream().forEach(e -> {

			String tableName = e.getCell(CellPositionType.PHYSICAL_NAME).getValue();
			String columnName = e.getCell(CellPositionType.COLUMN_NAME).getValue();
			String columnType = getColumnType(e);
			String columnComment = getColumnComment(e);

			StringBuilder sb = new StringBuilder();
			sb.append("ALTER TABLE ");
			sb.append(tableName);
			sb.append(" ADD ");
			sb.append(columnName);
			sb.append(" ");
			sb.append(columnType);
			sb.append(" COMMENT '");
			sb.append(columnComment);
			sb.append("';");

			body.add(sb.toString());
		});

		String outputPath = getOutputPath(ExecuteType.DDL);
		String fileName = DateUtil.toString(DateUtil.getSysDate(), DateFormatType.YYYYMMDD_HHMMSS_NOSEP)
				+ FileExtension.SQL.getValue();

		return new FileConfigBuilder(outputPath, fileName, body.toString()).build();
	}

	/**
	 * <code>rowList</code>から対象行リストを返す
	 *
	 * @param rowList
	 *     行リスト
	 * @return 対象行リスト
	 */
	private List<Row> getTargetRowList(List<Row> rowList) {
		return rowList.stream().filter(e -> CommonFlag.TRUE.is(e.getCell(CellPositionType.ADD_FLG).getValue()))
				.collect(Collectors.toList());
	}

}
