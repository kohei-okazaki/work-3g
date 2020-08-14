package jp.co.ha.tool.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.excel.DmlExcelReader;
import jp.co.ha.tool.excel.ExcelReader;
import jp.co.ha.tool.excel.ExcelRow;

/**
 * DML自動生成クラス
 *
 * @version 1.0.0
 */
public class DmlGenerator extends BaseGenerator {

    @Override
    protected ExcelReader getExcelReader() {
        return new DmlExcelReader();
    }

    @Override
    List<GenerateFile> generateImpl() throws Exception {

        List<GenerateFile> list = new ArrayList<>();

        for (String table : prop.getDmlTableList()) {
            excel.activeSheet(table);

            StringJoiner sj = new StringJoiner(StringUtil.NEW_LINE);

            List<ExcelRow> rowList = excel.getRowList();
            for (int i = 0; i < rowList.size(); i++) {

                if (i == 0) {
                    // 1行の場合
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO " + table + " (");
                // ヘッダ部の作成
                String header = getOneLine(rowList.get(0), false);
                sb.append(header);
                sb.append(") VALUES (");
                // レコード部の場合
                ExcelRow row = rowList.get(i);
                String data = getOneLine(row, true);
                sb.append(data);
                sb.append(");");

                sj.add(sb.toString());
            }

            GenerateFile genFile = new GenerateFile();
            genFile.setFileName(table + FileExtension.SQL.getValue());
            genFile.setData(sj.toString());
            genFile.setOutputPath(prop.getBaseDir() + FileSeparator.SYSTEM.getValue()
                    + GenerateType.DML.getPath());

            list.add(genFile);
        }

        return list;
    }

    /**
     * Excel1行分のデータを文字列にする
     *
     * @param row
     *     Excel行情報
     * @param isDataRecord
     *     データレコードかどうか
     * @return Excel1行分のデータ
     */
    private String getOneLine(ExcelRow row, boolean isDataRecord) {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        String enclosedChar = isDataRecord ? "'" : "";
        row.getCellList().stream().forEach(e -> {
            sj.add(enclosedChar + e.getValue() + enclosedChar);
        });
        return sj.toString();
    }

}
