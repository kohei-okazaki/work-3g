package jp.co.ha.tool.db;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブル情報保持クラス
 *
 * @since 1.0
 */
public class Table {

	/** 論理名 */
	private String logicalName;
	/** 物理名 */
	private String physicalName;
	/** カラムリスト */
	private List<Column> columnList = new ArrayList<>();

	/**
	 * logicalNameを返す
	 *
	 * @return logicalName
	 */
	public String getLogicalName() {
		return logicalName;
	}

	/**
	 * logicalNameを設定する
	 *
	 * @param logicalName
	 *     論理名
	 */
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	/**
	 * physicalNameを返す
	 *
	 * @return physicalName
	 */
	public String getPhysicalName() {
		return physicalName;
	}

	/**
	 * physicalNameを設定する
	 *
	 * @param physicalName
	 *     物理名
	 */
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	/**
	 * columnListを返す
	 *
	 * @return columnList
	 */
	public List<Column> getColumnList() {
		return columnList;
	}

	/**
	 * columnListを設定する
	 *
	 * @param columnList
	 *     カラムリスト
	 */
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	/**
	 * 指定したカラムを追加する
	 * 
	 * @param column
	 *     カラム
	 */
	public void addColumn(Column column) {
		this.columnList.add(column);
	}

}
