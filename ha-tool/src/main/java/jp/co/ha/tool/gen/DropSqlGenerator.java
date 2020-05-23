package jp.co.ha.tool.gen;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.util.ToolUtil;

/**
 * drop.sqlの自動生成クラス
 *
 * @version 1.0.0
 */
public class DropSqlGenerator extends BaseGenerator {

    @Override
    List<GenerateFile> generateImpl() throws Exception {

        StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
        ToolUtil.getTableList(excel.getRowList()).stream().forEach(e -> {
            body.add("-- " + e.getLogicalName());
            body.add("DROP TABLE IF EXISTS " + e.getPhysicalName() + ";");
        });

        // 自動生成ファイル
        GenerateFile generateFile = new GenerateFile();
        generateFile.setFileName("DROP.sql");
        generateFile.setData(body.toString());
        generateFile.setOutputPath(prop.getBaseDir() + FileSeparator.SYSTEM.getValue()
                + GenerateType.DROP.getPath());
        return Arrays.asList(generateFile);
    }

}
