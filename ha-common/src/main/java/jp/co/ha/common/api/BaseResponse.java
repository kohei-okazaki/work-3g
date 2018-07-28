package jp.co.ha.common.api;

/**
 * 基底レスポンスクラス<br>
 *
 */
public abstract class BaseResponse {

	/** 結果区分 */
	private ResultType resultType;

	/**
	 * resultを返す<br>
	 *
	 * @return resultType 結果区分
	 */
	public ResultType getResultType() {
		return resultType;
	}

	/**
	 * resultを設定する<br>
	 *
	 * @param resultType
	 *     結果区分
	 */
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

}
