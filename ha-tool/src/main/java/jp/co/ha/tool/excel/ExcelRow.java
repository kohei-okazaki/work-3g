package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.tool.excel.type.CellPositionType;

/**
 * エクセル行情報保持クラス
 *
 * @version 1.0.0
 */
public class ExcelRow {

    /** セルリスト */
    private List<ExcelCell> cellList = new ArrayList<>();

    /**
     * セルを追加する
     *
     * @param cell
     *     セル
     */
    public void addCell(ExcelCell cell) {
        this.cellList.add(cell);
    }

    /**
     * セルリストを返す
     *
     * @return cellList
     */
    public List<ExcelCell> getCellList() {
        return cellList;
    }

    /**
     * セル位置に対応する{@linkplain ExcelCell}を返す
     *
     * @param type
     *     セル位置
     * @return セル
     */
    public ExcelCell getCell(CellPositionType type) {
        return this.cellList.get(type.getPosition());
    }
}
