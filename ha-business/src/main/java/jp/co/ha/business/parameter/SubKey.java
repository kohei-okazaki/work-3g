package jp.co.ha.business.parameter;

/**
 * CodeManagerで使うサブキーenum<br>
 * codeParameter.xlsxにMainKeyと定義
 *
 */
public enum SubKey {

	/** 入力 */
	INPUT,
	/** 確認 */
	CONFIRM,
	/** 完了 */
	COMPLETE,

	/** 健康情報 */
	HEALTH_INFO,
	/** 結果照会 */
	REFERNCE_RESULT,

	/** 真 */
	TRUE,
	/** 偽 */
	FALSE;

}