package jp.co.ha.common.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.api.type.ResultType;

/**
 * 基底レスポンスクラス
 *
 */
public abstract class BaseResponse {

	/** API結果コード */
	@JsonProperty(value = "result")
	private ResultType resultType;

	/**
	 * resultTypeを返す
	 *
	 * @return resultType
	 */
	public ResultType getResultType() {
		return resultType;
	}

	/**
	 * resultTypeを設定する
	 *
	 * @param resultType
	 */
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

}
