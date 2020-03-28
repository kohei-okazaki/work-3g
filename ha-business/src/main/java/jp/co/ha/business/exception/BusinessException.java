package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.BaseException;

/**
 * 業務処理による例外クラス
 *
 * @version 1.0.0
 */
public class BusinessException extends BaseException {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = -1168840693409783721L;

    /**
     * コンストラクタ
     *
     * @param e
     *     例外クラス
     */
    public BusinessException(Exception e) {
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
    public BusinessException(BaseErrorCode errorCode, String detail) {
        super(errorCode, detail);
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
    public BusinessException(BaseErrorCode errorCode, String detail, Exception e) {
        super(errorCode, detail, e);
    }

}
