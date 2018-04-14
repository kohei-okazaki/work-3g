package jp.co.ha.common.api;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * エラーレスポンスクラス<br>
 *
 */
public class ErrorResponse extends BaseResponse {

	/** エラーコード*/
	private ErrorCode errorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 * @param e
	 */
	public ErrorResponse(BaseAppException e) {
		super.setResult(1);
		setErrorCode(e.getErrorCode());
		setDetail(e.getDetail());
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * errorCodeを設定する
	 * @param errorCode
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * detailを返す<br>
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * detailを設定する<br>
	 * @param detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
