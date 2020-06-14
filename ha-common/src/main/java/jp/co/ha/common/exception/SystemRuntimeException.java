package jp.co.ha.common.exception;

/**
 * 実行環境エラーの例外クラス
 *
 * @version 1.0.0
 */
public class SystemRuntimeException extends BaseRuntimeException {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = -8788668488567180289L;

    /**
     * コンストラクタ
     *
     * @param e
     *     例外クラス
     */
    public SystemRuntimeException(Exception e) {
        super(e);
    }

    /**
     * コンストラクタ
     *
     * @param errorCode
     *     エラーコード
     * @param detail
     *     詳細
     */
    public SystemRuntimeException(BaseErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    /**
     * コンストラクタ
     *
     * @param errorCode
     *     エラーコード
     * @param e
     *     例外クラス
     */
    public SystemRuntimeException(BaseErrorCode errorCode, Exception e) {
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
    public SystemRuntimeException(BaseErrorCode errorCode, String detail, Exception e) {
        super(errorCode, detail, e);
    }

}
