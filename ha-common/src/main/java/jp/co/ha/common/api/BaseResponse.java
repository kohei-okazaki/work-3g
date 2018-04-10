package jp.co.ha.common.api;

/**
 * 基底レスポンスクラス<br>
 *
 */
public abstract class BaseResponse {

	/** 結果 */
	private int result;

	/**
	 * resultを返す<br>
	 * @return result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * resultを設定する<br>
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}

}
