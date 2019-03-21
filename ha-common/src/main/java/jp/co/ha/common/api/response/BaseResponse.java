package jp.co.ha.common.api.response;

import jp.co.ha.common.api.type.ResultType;

/**
 * 基底レスポンスクラス
 *
 */
public abstract class BaseResponse {

	/** API結果コード */
	private ResultType result;

	/**
	 * resultを返す
	 *
	 * @return result
	 */
	public ResultType getResult() {
		return result;
	}

	/**
	 * resultを設定する
	 *
	 * @param result
	 *     API結果コード
	 */
	public void setResult(ResultType result) {
		this.result = result;
	}

}
