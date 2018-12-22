package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント登録サービスインターフェース
 *
 */
public interface AccountRegistService {

	/**
	 * 指定したアカウント情報が有効かどうか判定する<br>
	 * 作成できないアカウント情報の場合true, それ以外の場合false<br>
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return 判定結果
	 * @throws BaseException
	 *     基底例外
	 */
	boolean invalidUserId(AccountRegistForm form) throws BaseException;

	/**
	 * 登録処理を行う<br>
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @throws BaseException
	 *     基底例外
	 */
	void regist(AccountRegistForm form) throws BaseException;

}
