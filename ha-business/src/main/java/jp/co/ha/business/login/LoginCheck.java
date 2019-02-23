package jp.co.ha.business.login;

import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.Account;

/**
 * ログイン情報チェッククラス
 *
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
	 * @param inputUserId
	 *     入力されたユーザID
	 * @param inputPassword
	 *     入力されたパスワード
	 * @return ログイン情報チェック結果
	 */
	public LoginCheckResult check(Account account, String inputUserId, String inputPassword) {

		LoginCheckResult result = new LoginCheckResult();
		checkExistAccount(result, account);
		if (result.hasError()) {
			return result;
		}

		checkInvalidPassword(result, inputPassword, account.getPassword());
		if (result.hasError()) {
			return result;
		}

		checkDeleteAccount(result, account);
		if (result.hasError()) {
			return result;
		}

		checkAccountExpired(result, account);
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
	private void checkExistAccount(LoginCheckResult result, Account account) {
		if (BeanUtil.isNull(account)) {
			result.addError();
			result.setErrorCode(WebErrorCode.ACCOUNT_ILLEGAL);
		}
	}

	/**
	 * ログイン情報と入力情報を照合する
	 *
	 * @param result
	 *     ログイン情報チェック結果
	 * @param inputPassword
	 *     フォーム情報.パスワード
	 * @param dbPassword
	 *     DB情報.パスワード
	 */
	private void checkInvalidPassword(LoginCheckResult result, String inputPassword, String dbPassword) {
		if (!inputPassword.equals(dbPassword)) {
			result.addError();
			result.setErrorCode(WebErrorCode.ACCOUNT_INVALID_PASSWORD);
		}
	}

	/**
	 * アカウント情報が有効かどうかチェック<br>
	 * 有効でない場合true, そうでない場合false
	 *
	 * @param result
	 *     ログイン情報チェック結果
	 * @param account
	 *     アカウント情報
	 */
	private void checkDeleteAccount(LoginCheckResult result, Account account) {
		if (StringUtil.isTrue(account.getDeleteFlag())) {
			result.addError();
			result.setErrorCode(WebErrorCode.ACCOUNT_EXPIRED);
		}
	}

	/**
	 * アカウント情報が有効期限切れかどうか判定する<br>
	 * アカウント情報.パスワード有効期限 < システム日付の場合、true
	 *
	 * @param result
	 *    ログイン情報チェック結果
	 * @param account
	 *     アカウント情報
	 */
	private void checkAccountExpired(LoginCheckResult result, Account account) {
		if (DateUtil.isAfter(account.getPasswordExpire(), false)) {
			result.addError();
			result.setErrorCode(WebErrorCode.ACCOUNT_EXPIRED);
		}
	}

}
