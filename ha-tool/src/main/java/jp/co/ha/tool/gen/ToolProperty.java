package jp.co.ha.tool.gen;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.io.file.property.annotation.Property;

/**
 * tool.propertiesのBeanクラス
 *
 * @version 1.0.0
 */
public class ToolProperty {

    /** 基底パス */
    @Property(name = "tool.base.dir")
    private String baseDir;
    /** 自動生成ツールExcelまでのパス */
    @Property(name = "tool.excel.path")
    private String excelPath;
    /** 処理対象テーブル(カンマ区切り) */
    @Property(name = "tool.target.tables")
    private String targetTables;
    /** バージョン情報 */
    @Property(name = "tool.version")
    private String version;
    /** DML対象テーブル(カンマ区切り) */
    @Property(name = "tool.dml.tables")
    private String dmlTables;

    /** DDL対象テーブルリスト */
    private List<String> ddlTableList = new ArrayList<>();
    /** DML対象テーブルリスト */
    private List<String> dmlTableList = new ArrayList<>();

    /**
     * 基底パスを返す
     *
     * @return baseDir
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * 基底パスを設定する
     *
     * @param baseDir
     *     基底パス
     */
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * 自動生成ツールExcelまでのパスを返す
     *
     * @return excelPath
     */
    public String getExcelPath() {
        return excelPath;
    }

    /**
     * 自動生成ツールExcelまでのパスを設定する
     *
     * @param excelPath
     *     自動生成ツールExcelまでのパス
     */
    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * 処理対象テーブルリストを返す
     *
     * @return targetTables
     */
    public String getTargetTables() {
        return targetTables;
    }

    /**
     * 処理対象テーブルリストを設定する
     *
     * @param targetTables
     *     処理対象テーブルリスト
     */
    public void setTargetTables(String targetTables) {
        this.targetTables = targetTables;
    }

    /**
     * バージョン情報を返す
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * バージョン情報を設定する
     *
     * @param version
     *     バージョン情報
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * DML対象テーブルを返す
     *
     * @return dmlTables
     */
    public String getDmlTables() {
        return dmlTables;
    }

    /**
     * DML対象テーブルを設定する
     *
     * @param dmlTables
     *     DML対象テーブル
     */
    public void setDmlTables(String dmlTables) {
        this.dmlTables = dmlTables;
    }

    /**
     * 処理対象テーブルリストを返す
     *
     * @return targetTableList
     */
    public List<String> getDdlTableList() {
        return ddlTableList;
    }

    /**
     * 処理対象テーブルリストを設定する
     *
     * @param targetTableList
     *     処理対象テーブルリスト
     */
    public void setDdlTableList(List<String> targetTableList) {
        this.ddlTableList = targetTableList;
    }

    /**
     * 指定した対象テーブルを追加する
     *
     * @param targetTable
     *     対象テーブル
     */
    public void addDdlTable(String targetTable) {
        this.ddlTableList.add(targetTable);
    }

    /**
     * DML対象テーブルリストを返す
     *
     * @return dmlTableList
     */
    public List<String> getDmlTableList() {
        return dmlTableList;
    }

    /**
     * DML対象テーブルリストを設定する
     *
     * @param dmlTableList
     *     DML対象テーブルリスト
     */
    public void setDmlTableList(List<String> dmlTableList) {
        this.dmlTableList = dmlTableList;
    }

    /**
     * 指定した対象テーブルを追加する
     *
     * @param dmlTable
     *     DML対象テーブル
     */
    public void addDmlTable(String dmlTable) {
        this.dmlTableList.add(dmlTable);
    }

}
