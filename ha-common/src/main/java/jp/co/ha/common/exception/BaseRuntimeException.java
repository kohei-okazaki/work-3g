package jp.co.ha.common.exception;

/**
 * アプリ内で扱う基底Runtime例外クラス
 *
 * @version 1.0.0
 */
public abstract class BaseRuntimeException extends RuntimeException {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = -5362739275313802447L;

    /** エラーコード */
    private BaseErrorCode errorCode;
    /** 詳細 */
    private String detail;

    /**
     * コンストラクタ
     *
     * @param e
     *     例外クラス
     */
    public BaseRuntimeException(Exception e) {
        this(CommonErrorCode.UNEXPECTED_ERROR, e);
    }

    /**
     * コンストラクタ
     *
     * @param errorCode
     *     エラーコード
     * @param detail
     *     詳細
     */
    public BaseRuntimeException(BaseErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }

    /**
     * コンストラクタ
     *
     * @param errorCode
     *     エラーコード
     * @param e
     *     例外クラス
     */
    public BaseRuntimeException(BaseErrorCode errorCode, Exception e) {
        this(errorCode, "", e);
    }

    /**
     * コンストラクタ
     *
     * @param errorCode
     *     エラーコード
     * @param detail
     *     詳細
     * @param e
     *     例外クラス
     */
    public BaseRuntimeException(BaseErrorCode errorCode, String detail, Exception e) {
        super(e);
        this.errorCode = errorCode;
        this.detail = detail;
    }

    /**
     * {@linkplain BaseErrorCode}を返す
     *
     * @return errorCode
     */
    public BaseErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * detailを返す
     *
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

}
