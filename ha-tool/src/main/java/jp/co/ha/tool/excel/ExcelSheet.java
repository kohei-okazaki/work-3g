package jp.co.ha.tool.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * シート情報保持クラス
 *
 * @version 1.0.0
 */
public class ExcelSheet {

    /** 名前 */
    private String name;
    /** 行リスト */
    private List<ExcelRow> rowList = new ArrayList<>();

    /**
     * nameを返す
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定する
     *
     * @param name
     *     名前
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 全行返す
     *
     * @return rowList
     */
    public List<ExcelRow> getRowList() {
        return rowList;
    }

    /**
     * 行を追加する
     *
     * @param row
     *     行
     */
    public void addRow(ExcelRow row) {
        this.rowList.add(row);
    }

}
