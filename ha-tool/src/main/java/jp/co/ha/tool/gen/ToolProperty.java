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
    @Property(name = "tool.basedir")
    private String baseDir;
    /** 自動生成ツールExcelまでのパス */
    @Property(name = "tool.excel.path")
    private String excelPath;
    /** 処理対象テーブルリスト */
    @Property(name = "tool.target.tables")
    private String targetTables;
    /** バージョン情報 */
    @Property(name = "tool.version")
    private String version;
    /** DML対象テーブル */
    @Property(name = "tool.dml.tables")
    private String dmlTables;

    /** 処理対象テーブルリスト */
    private List<String> targetTableList = new ArrayList<>();
    /** DML対象テーブルリスト */
    private List<String> dmlTableList = new ArrayList<>();

    /**
     * baseDirを返す
     *
     * @return baseDir
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * baseDirを設定する
     *
     * @param baseDir
     *     基底パス
     */
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * excelPathを返す
     *
     * @return excelPath
     */
    public String getExcelPath() {
        return excelPath;
    }

    /**
     * excelPathを設定する
     *
     * @param excelPath
     *     自動生成ツールExcelまでのパス
     */
    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * targetTablesを返す
     *
     * @return targetTables
     */
    public String getTargetTables() {
        return targetTables;
    }

    /**
     * targetTablesを設定する
     *
     * @param targetTables
     *     処理対象テーブルリスト
     */
    public void setTargetTables(String targetTables) {
        this.targetTables = targetTables;
    }

    /**
     * versionを返す
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * versionを設定する
     *
     * @param version
     *     バージョン情報
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * dmlTablesを返す
     *
     * @return dmlTables
     */
    public String getDmlTables() {
        return dmlTables;
    }

    /**
     * dmlTablesを設定する
     *
     * @param dmlTables
     *     DML対象テーブル
     */
    public void setDmlTables(String dmlTables) {
        this.dmlTables = dmlTables;
    }

    /**
     * targetTableListを返す
     *
     * @return targetTableList
     */
    public List<String> getTargetTableList() {
        return targetTableList;
    }

    /**
     * targetTableListを設定する
     *
     * @param targetTableList
     *     処理対象テーブルリスト
     */
    public void setTargetTableList(List<String> targetTableList) {
        this.targetTableList = targetTableList;
    }

    /**
     * 指定した対象テーブルを追加する
     *
     * @param targetTable
     *     対象テーブル
     */
    public void addTargetTable(String targetTable) {
        this.targetTableList.add(targetTable);
    }

    /**
     * dmlTableListを返す
     *
     * @return dmlTableList
     */
    public List<String> getDmlTableList() {
        return dmlTableList;
    }

    /**
     * dmlTableListを設定する
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
