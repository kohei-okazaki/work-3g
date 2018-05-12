package jp.co.ha.common.dao;

import jp.co.ha.common.util.SystemInfo;

/**
 * dao基底インターフェース<br>
 *
 */
public interface BaseDao {

	/** 保存先ファイルパス */
	public static final String RESOURCES = SystemInfo.isWin() ? "C:\\work\\data.xlsx" : "/Applications/data.xlsx";

	/** ヘッダー位置*/
	public static final int HEADER_POSITION = 0;

}
