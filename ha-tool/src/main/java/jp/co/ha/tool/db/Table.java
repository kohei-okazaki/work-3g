package jp.co.ha.tool.db;

import java.util.List;

/**
 * テーブル情報保持クラス
 *
 * @param logicalName
 *     論理名
 * @param physicalName
 *     物理名
 * @param columnList
 *     カラムリスト
 * @version 1.0.0
 */
public record Table(
        String logicalName,
        String physicalName,
        List<Column> columnList) {
}
