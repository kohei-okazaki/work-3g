package jp.co.ha.business.api.service;

import jp.co.ha.business.api.request.CommonRequest;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

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
	protected void useApi(Account account, CommonRequest request) throws BaseException {
		String userApiKey = account.getApiKey();
		if (!userApiKey.equals(request.getApiKey())) {
			throw new ApiException(ErrorCode.API_EXEC_ERROR, "このユーザはAPIを実行できません");
		}
	}
}
