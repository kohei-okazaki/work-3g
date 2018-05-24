package jp.co.ha.common.api;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * エラーレスポンスクラス<br>
 *
 */
public class ErrorResponse extends BaseResponse {

	/** エラーコード */
	private ErrorCode errorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 *
	 * @param e
	 *            BaseAppException 例外
	 */
	public ErrorResponse(BaseAppException e) {
		super.setResult(ResultType.FAILURE);
		this.errorCode = e.getErrorCode();
		this.detail = e.getDetail();
	}

	/**
	 * errorCodeを返す
	 *
	 * @return errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * detailを返す<br>
	 *
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

}
