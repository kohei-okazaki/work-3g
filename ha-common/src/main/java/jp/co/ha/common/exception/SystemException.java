package jp.co.ha.common.exception;

/**
 * システム情報の例外クラス<br>
 * <ul>
 * <li>DB関連で発生した例外</li>
 * <li>IO関連で発生した例外</li>
 * <li>Session関連で発生した例外</li>
 * </ul>
 *
 * @version 1.0.0
 */
public class SystemException extends BaseException {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 8975593059485986641L;

    /**
     * コンストラクタ
     *
     * @param e
     *     例外クラス
     */
    public SystemException(Exception e) {
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
    public SystemException(BaseErrorCode errorCode, String detail) {
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
    public SystemException(BaseErrorCode errorCode, Exception e) {
        super(errorCode, e);
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
    public SystemException(BaseErrorCode errorCode, String detail, Exception e) {
        super(errorCode, detail, e);
    }

}
