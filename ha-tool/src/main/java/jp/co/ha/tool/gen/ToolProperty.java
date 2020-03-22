package jp.co.ha.tool.gen;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.io.file.property.annotation.Property;

/**
 * tool.propertiesのBeanクラス
 *
 * @version 1.0
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
	/** 処理対象テーブルリスト */
	private List<String> targetTableList = new ArrayList<>();

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
	 */
	public void setVersion(String version) {
		this.version = version;
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

}
