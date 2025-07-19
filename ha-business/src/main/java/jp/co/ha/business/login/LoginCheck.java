package jp.co.ha.business.login;

import java.time.LocalDateTime;
import java.util.Optional;

import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.User;

/**
 * ログイン情報チェッククラス
 *
 * @version 1.0.0
 */
public class LoginCheck {

    /**
     * ユーザ情報のチェックを行う<br>
     * <ul>
     * <li>検索したユーザ情報が存在しない場合</li>
     * <li>検索したユーザ情報入力したパスワードが一致しない場合</li>
     * <li>検索したユーザ情報が論理削除された場合</li>
     * <li>検索したユーザ情報が有効期限切れの場合</li>
     * </ul>
     *
     * @param user
     *     ユーザ情報
     * @param inputPassword
     *     入力されたパスワード
     * @return ログイン情報チェック結果
     * @throws BaseException
     */
    public LoginCheckResult check(Optional<User> user, String inputPassword)
            throws BaseException {

        LoginCheckResult result = new LoginCheckResult();
        checkExistUser(result, user);
        if (result.hasError()) {
            return result;
        }

        checkInvalidPassword(result, inputPassword, user.get());
        if (result.hasError()) {
            return result;
        }

        checkDeleteUser(result, user.get());
        if (result.hasError()) {
            return result;
        }

        checkUserExpired(result, user.get());
        if (result.hasError()) {
            return result;
        }

        return result;
    }

    /**
     * ユーザが存在しない場合validateエラーにする
     *
     * @param result
     *     ログイン情報チェック結果
     * @param user
     *     ユーザ情報
     */
    private void checkExistUser(LoginCheckResult result, Optional<User> user) {
        if (!user.isPresent()) {
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
     * @param user
     *     ユーザ情報
     * @throws BaseException
     */
    private void checkInvalidPassword(LoginCheckResult result, String inputPassword,
            User user) throws BaseException {

        String hashPassword = new Sha256HashEncoder().encode(inputPassword,
                user.getMailAddress());
        if (!hashPassword.equals(user.getPassword())) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_INVALID_PASSWORD);
        }
    }

    /**
     * ユーザ情報が有効かどうかチェック<br>
     * 有効でない場合、エラー
     *
     * @param result
     *     ログイン情報チェック結果
     * @param user
     *     ユーザ情報
     */
    private void checkDeleteUser(LoginCheckResult result, User user) {
        if (user.getDeleteFlag()) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_DELETE);
        }
    }

    /**
     * ユーザ情報が有効期限切れかどうか判定する<br>
     * ユーザ情報.パスワード有効期限 < システム日付の場合、エラー
     *
     * @param result
     *     ログイン情報チェック結果
     * @param user
     *     ユーザ情報
     */
    private void checkUserExpired(LoginCheckResult result, User user) {
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        if (DateTimeUtil.isBefore(user.getPasswordExpire(),
                DateTimeUtil.toLocalDate(sysdate), false)) {
            result.addError();
            result.setErrorCode(DashboardErrorCode.ACCOUNT_EXPIRED);
        }
    }

}
