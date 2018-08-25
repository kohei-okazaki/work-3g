package jp.co.ha.business.api;

import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.api.BaseRequest;
import jp.co.ha.common.exception.ApiException;
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
	 * @throws ApiException
	 *     API例外
	 */
	protected void useApi(Account account, BaseRequest request) throws ApiException {
		String userApiKey = account.getApiKey();
		if (!userApiKey.equals(request.getApiKey())) {
			throw new ApiException(ErrorCode.API_EXEC_ERROR, "このユーザはAPIを実行できません");
		}
	}
}
