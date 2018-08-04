package jp.co.ha.common.api;

import jp.co.ha.common.exception.BaseException;

/**
 * エラーレスポンスクラス<br>
 *
 */
public class ErrorResponse extends BaseResponse {

	/** 外部エラーコード */
	private String outerErrorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 *
	 * @param e
	 *     BaseAppException 例外
	 */
	public ErrorResponse(BaseException e) {
		super.setResultType(ResultType.FAILURE);
		this.outerErrorCode = e.getErrorCode().getOuterErrorCode();
		this.detail = e.getDetail();
	}

	/**
	 * outerErrorCodeを返す<br>
	 *
	 * @return outerErrorCode
	 */
	public String getOuterErrorCode() {
		return outerErrorCode;
	}

	/**
	 * outerErrorCodeを設定する<br>
	 *
	 * @param outerErrorCode
	 *     外部エラーコード
	 */
	public void setOuterErrorCode(String outerErrorCode) {
		this.outerErrorCode = outerErrorCode;
	}

	/**
	 * detailを返す<br>
	 *
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * detailを設定する<br>
	 *
	 * @param detail
	 *     詳細
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
