package jp.co.ha.tool.type;

/**
 * SqlExecutorの処理タイプ
 *
 */
public enum ExecuteType {
	/** entity作成 */
	ENTITY,
	/** DDL作成 */
	DDL,
	/** DML作成 */
	DML,
	/** DROP作成 */
	DROP;
}
