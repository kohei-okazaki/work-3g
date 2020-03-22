package jp.co.ha.tool.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.FileUtil.LineFeedType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.util.ToolUtil;

/**
 * create tableの自動生成のクラス
 *
 * @version 1.0
 */
public class CreateTableGenerator extends BaseGenerator {

	@Override
	List<GenerateFile> generateImpl() throws Exception {

		// 自動生成ファイルリスト
		List<GenerateFile> list = new ArrayList<>();

		for (String tableName : prop.getTargetTableList()) {
			Table table = ToolUtil.getTable(excel.getRowList(), tableName);

			StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
			body.add("-- " + table.getLogicalName());
			body.add("CREATE TABLE IF NOT EXISTS " + tableName + " (");
			StringJoiner columnData = new StringJoiner(
					StringUtil.COMMA + LineFeedType.CRLF.getValue());
			table.getColumnList().stream().forEach(e -> {

				String comment = e.getComment();
				String name = e.getName();
				String type = e.getType();
				boolean isSequence = e.isSequence();
				boolean isNotNull = e.isNotNull();
				boolean isPrimary = e.isPrimary();

				StringBuilder sb = new StringBuilder();
				sb.append("-- ");
				sb.append(comment);
				sb.append(StringUtil.NEW_LINE);
				// インデントを揃える為に半角スペースを2つ入れる
				sb.append("  " + name);
				sb.append(StringUtil.SPACE);
				sb.append(type);
				sb.append(isSequence ? " AUTO_INCREMENT" : "");
				sb.append(isNotNull ? " NOT NULL" : "");
				sb.append(isPrimary ? " PRIMARY KEY" : "");
				sb.append(" COMMENT '");
				sb.append(comment);
				sb.append("'");

				// インデントを揃える為に半角スペースを2つ入れる
				columnData.add("  " + sb.toString());
			});
			body.add(columnData.toString());
			body.add(");");

			GenerateFile generateFile = new GenerateFile();
			generateFile.setFileName(tableName + FileExtension.SQL.getValue());
			generateFile.setData(body.toString());
			generateFile.setOutputPath(prop.getBaseDir() + FileSeparator.SYSTEM.getValue()
					+ GenerateType.DDL.getPath());
			list.add(generateFile);
		}

		return list;
	}

}
