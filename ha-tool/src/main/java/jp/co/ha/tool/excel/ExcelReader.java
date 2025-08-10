package jp.co.ha.tool.excel;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.tool.excel.type.CellPositionType;
import jp.co.ha.tool.gen.ToolProperty;

/**
 * 自動生成ツールExcelの読込を行うクラス
 *
 * @version 1.0.0
 */
public class ExcelReader {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 対象シート名 */
    private static final String TARGET_SHEET_NAME = "TABLE_LIST";

    /**
     * 指定された自動生成ツール設定ファイルからExcelの読込を行う
     *
     * @param prop
     *     自動生成ツール設定ファイル
     * @return Excel
     * @throws Exception
     *     Excelの取得に失敗した場合
     */
    public Excel read(ToolProperty prop) throws Exception {

        Iterator<Sheet> sheetIte;
        try (Workbook wb = WorkbookFactory.create(new File(prop.getExcelPath()))) {
            sheetIte = wb.sheetIterator();
        }

        Excel excel = new Excel();

        // シート毎の処理
        while (sheetIte.hasNext()) {

            Sheet sheet = sheetIte.next();
            if (!TARGET_SHEET_NAME.equals(sheet.getSheetName())) {
                // ”TABLE_LIST” シートでない場合、次のシートへ
                break;
            }

            ExcelSheet excelSheet = new ExcelSheet();
            excelSheet.setName(sheet.getSheetName());
            Iterator<Row> rowIte = sheet.iterator();

            // 行毎の処理
            while (rowIte.hasNext()) {
                ExcelRow excelRow = new ExcelRow();
                Row row = rowIte.next();
                Arrays.asList(CellPositionType.class.getEnumConstants()).stream()
                        .forEach(e -> {
                            String cellValue = row.getCell(e.getPosition())
                                    .getStringCellValue();
                            ExcelCell cell = new ExcelCell(cellValue);
                            excelRow.addCell(cell);
                        });
                excelSheet.addRow(excelRow);
            }
            excel.addSheet(excelSheet);
        }

        // 現在のシートをTABLE_LISTに設定
        excel.activeSheet(TARGET_SHEET_NAME);
        return excel;
    }

}
