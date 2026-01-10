package jp.co.ha.business.login;

import jp.co.ha.business.exception.DashboardErrorCode;

/**
 * ログイン情報チェック結果保持クラス
 *
 * @version 1.0.0
 */
public class LoginCheckResult {

    /** エラーカウント */
    private int errorCount = 0;
    /** エラーコード */
    private DashboardErrorCode errorCode;

    /**
     * エラーが存在するかどうかを返す
     *
     * @return 判定結果
     */
    public boolean hasError() {
        return errorCount > 0;
    }

    /**
     * エラーを追加する
     */
    public void addError() {
        this.errorCount++;
    }

    /**
     * エラーコードを返す
     *
     * @return errorCode
     */
    public DashboardErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * エラーコードを設定する
     *
     * @param errorCode
     *     エラーコード
     */
    public void setErrorCode(DashboardErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
