package jp.co.ha.common.api;

/**
 * 基底レスポンスクラス<br>
 *
 */
public abstract class BaseResponse {

	/** 結果 */
	private ResultType result;

	/**
	 * resultを返す<br>
	 * @return result
	 */
	public ResultType getResult() {
		return result;
	}

	/**
	 * resultを設定する<br>
	 * @param result
	 */
	public void setResult(ResultType result) {
		this.result = result;
	}

}
