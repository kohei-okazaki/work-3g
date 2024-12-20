package jp.co.ha.business.login;

import java.time.LocalDateTime;
import java.util.Optional;

import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.Account;

/**
 * ログイン情報チェッククラス
 *
 * @version 1.0.0
 */
public class LoginCheck {

    /**
     * アカウント情報のチェックを行う<br>
     * <ul>
     * <li>検索したアカウント情報が存在しない場合</li>
     * <li>検索したアカウント情報入力したパスワードが一致しない場合</li>
     * <li>検索したアカウント情報が論理削除された場合</li>
     * <li>検索したアカウント情報が有効期限切れの場合</li>
     * </ul>
     *
     * @param account
     *     アカウント情報
     * @param inputPassword
     *     入力されたパスワード
     * @return ログイン情報チェック結果
     * @throws BaseException
     */
    public LoginCheckResult check(Optional<Account> account, String inputPassword)
            throws BaseException {

        LoginCheckResult result = new LoginCheckResult();
        checkExistAccount(result, account);
        if (result.hasError()) {
            return result;
        }

        checkInvalidPassword(result, inputPassword, account.get());
        if (result.hasError()) {
            return result;
        }

        checkDeleteAccount(result, account.get());
        if (result.hasError()) {
            return result;
        }

        checkAccountExpired(result, account.get());
        if (result.hasError()) {
            return result;
        }

        return result;
    }

    /**
     * アカウントが存在しない場合validateエラーにする
     *
     * @param result
     *     ログイン情報チェック結果
     * @param account
     *     アカウント情報
     */
    private void checkExistAccount(LoginCheckResult result, Optional<Account> account) {
        if (!account.isPresent()) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_EXIST);
        }
    }

    /**
     * ログイン情報と入力情報を照合する
     *
     * @param result
     *     ログイン情報チェック結果
     * @param inputPassword
     *     フォーム情報.パスワード
     * @param account
     *     DB情報.パスワード
     * @throws BaseException
     */
    private void checkInvalidPassword(LoginCheckResult result, String inputPassword,
            Account account) throws BaseException {

        String hashPassword = new Sha256HashEncoder().encode(inputPassword,
                account.getMailAddress());
        if (!hashPassword.equals(account.getPassword())) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_INVALID_PASSWORD);
        }
    }

    /**
     * アカウント情報が有効かどうかチェック<br>
     * 有効でない場合、エラー
     *
     * @param result
     *     ログイン情報チェック結果
     * @param account
     *     アカウント情報
     */
    private void checkDeleteAccount(LoginCheckResult result, Account account) {
        if (account.getDeleteFlag()) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_DELETE);
        }
    }

    /**
     * アカウント情報が有効期限切れかどうか判定する<br>
     * アカウント情報.パスワード有効期限 < システム日付の場合、エラー
     *
     * @param result
     *     ログイン情報チェック結果
     * @param account
     *     アカウント情報
     */
    private void checkAccountExpired(LoginCheckResult result, Account account) {
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        if (DateTimeUtil.isBefore(account.getPasswordExpire(),
                DateTimeUtil.toLocalDate(sysdate), false)) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_EXPIRED);
        }
    }

}
