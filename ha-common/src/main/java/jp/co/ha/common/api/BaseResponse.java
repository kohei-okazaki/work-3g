package jp.co.ha.common.api;

/**
 * 基底レスポンスクラス<br>
 *
 */
public abstract class BaseResponse {

	/** 結果区分 */
	private ResultType result;

	/**
	 * resultを返す<br>
	 *
	 * @return result 結果区分
	 */
	public ResultType getResult() {
		return result;
	}

	/**
	 * resultを設定する<br>
	 *
	 * @param result
	 *            結果区分
	 */
	public void setResult(ResultType result) {
		this.result = result;
	}

}
