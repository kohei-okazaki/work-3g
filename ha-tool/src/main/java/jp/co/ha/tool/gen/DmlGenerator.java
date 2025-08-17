package jp.co.ha.tool.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.excel.DmlExcelReader;
import jp.co.ha.tool.excel.ExcelCell;
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
                String header = getHeader(rowList.get(0));
                sb.append(header);
                sb.append(") VALUES (");
                // レコード部の場合
                ExcelRow row = rowList.get(i);
                String body = getBody(row);
                sb.append(body);
                sb.append(");");

                sj.add(sb.toString());
            }

            GenerateFile genFile = new GenerateFile();
            genFile.setFileName(table + FileExtension.SQL);
            genFile.setData(sj.toString());
            genFile.setOutputPath(prop.getBaseDir() + FileSeparator.SYSTEM.getValue()
                    + GenerateType.DML.getPath());

            list.add(genFile);
        }

        return list;
    }

    /**
     * ヘッダを文字列形式で返す
     * 
     * @param row
     * @return ヘッダ行
     */
    private String getHeader(ExcelRow row) {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        row.getCellList().stream().forEach(e -> {
            sj.add(e.getValue());
        });
        return sj.toString();
    }

    /**
     * ボディを文字列形式で返す
     * 
     * @param row
     * @return ボディ行
     */
    private String getBody(ExcelRow row) {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        for (ExcelCell cell : row.getCellList()) {
            if (String.class.equals(cell.getClazz())) {
                sj.add("'" + cell.getValue() + "'");
            } else {
                sj.add(cell.getValue());
            }
        }
        return sj.toString();
    }

}
