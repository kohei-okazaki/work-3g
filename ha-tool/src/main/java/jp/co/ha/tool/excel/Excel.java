package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 自動生成ツールのExcelクラス
 *
 * @version 1.0.0
 */
public class Excel {

    /** シートリスト */
    private List<ExcelSheet> sheetList = new ArrayList<>();
    /** アクティブなシート */
    private ExcelSheet currentSheet;

    /**
     * シートを返す
     *
     * @param sheetName
     *     シート名
     * @return Sheet
     */
    private ExcelSheet getSheet(String sheetName) {
        return this.sheetList.stream()
                .filter(e -> e.getName().equals(sheetName))
                .findFirst()
                .orElse(null);
    }

    /**
     * シートを追加する
     *
     * @param sheet
     *     シート
     */
    public void addSheet(ExcelSheet sheet) {
        this.sheetList.add(sheet);
    }

    /**
     * シートをアクティブにする
     *
     * @param sheetName
     *     シート名
     */
    public void activeSheet(String sheetName) {
        this.currentSheet = getSheet(sheetName);
    }

    /**
     * 現在のリストを全行返す
     *
     * @return 全行リスト
     */
    public List<ExcelRow> getRowList() {
        return this.currentSheet.getRowList();
    }
}
