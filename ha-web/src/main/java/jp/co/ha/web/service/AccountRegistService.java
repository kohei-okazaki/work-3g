package jp.co.ha.web.service;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.web.form.AccountRegistForm;

/**
 * アカウント登録サービスインターフェース
 *
 */
public interface AccountRegistService {

	/**
	 * アカウントEntityに変換する<br>
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return
	 * @throws AlgorithmException
	 */
	Account toAccount(AccountRegistForm form) throws AlgorithmException;

	/**
	 * 指定したアカウント情報が有効かどうか判定する<br>
	 * 作成できないアカウント情報の場合true, それ以外の場合false<br>
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return
	 * @throws BaseAppException
	 */
	boolean invalidUserId(AccountRegistForm form) throws BaseAppException;

	/**
	 * 健康情報ファイル設定Entityに変換する<br>
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return
	 */
	HealthInfoFileSetting toHealthInfoFileSetting(AccountRegistForm form);

}
