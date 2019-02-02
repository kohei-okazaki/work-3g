package jp.co.ha.business.api.service;

import jp.co.ha.business.api.request.BaseApiRequest;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.db.entity.Account;

/**
 * API共通サービス
 *
 */
public abstract class CommonService {

	/**
	 * 指定したアカウントがAPIを実行できるか判定する<br>
	 * 実行出来ない場合throw<br>
	 *
	 * @param account
	 *     アカウント情報
	 * @param request
	 *     リクエスト
	 * @throws BaseException
	 *     API例外
	 */
	protected void checkApiUse(Account account, BaseApiRequest request) throws BaseException {
		if (!account.getApiKey().equals(request.getApiKey())) {
			throw new ApiException(ErrorCode.API_EXEC_ERROR, "このユーザはAPIを実行できません");
		}
	}
}
