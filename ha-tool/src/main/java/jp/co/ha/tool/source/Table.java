package jp.co.ha.tool.source;

public class Table {

	/** 論理名 */
	private String logicalName;
	/** 物理名 */
	private String physicalName;

	public Table(String logicalName, String physicalName) {
		this.logicalName = logicalName;
		this.physicalName = physicalName;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public String getPhysicalName() {
		return physicalName;
	}

}
