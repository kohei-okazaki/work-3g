package jp.co.ha.tool.excel;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.ha.tool.gen.ToolProperty;

/**
 * DML用のExcelを読み込むReaderクラス
 *
 * @version 1.0.0
 */
public class DmlExcelReader extends ExcelReader {

    @Override
    public Excel read(ToolProperty prop) throws Exception {

        Iterator<Sheet> sheetIte;
        try (Workbook wb = WorkbookFactory.create(new File(prop.getExcelPath()))) {
            sheetIte = wb.sheetIterator();
        }
        Excel excel = new Excel();

        // シート毎の処理
        while (sheetIte.hasNext()) {
            ExcelSheet excelSheet = new ExcelSheet();
            Sheet sheet = sheetIte.next();

            if ("TABLE_LIST".equals(sheet.getSheetName())) {
                // TABLE_LISTは読み込み対象外
                continue;
            }

            excelSheet.setName(sheet.getSheetName());
            Iterator<Row> rowIte = sheet.iterator();

            // 行毎の処理
            while (rowIte.hasNext()) {

                Row row = rowIte.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                ExcelRow r = new ExcelRow();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = cell.getStringCellValue();
                    r.addCell(new ExcelCell(cellValue));
                }
                excelSheet.addRow(r);

            }
            excel.addSheet(excelSheet);
        }

        return excel;
    }
}
