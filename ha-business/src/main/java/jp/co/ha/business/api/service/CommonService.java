package jp.co.ha.business.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.api.request.CommonApiRequest;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.Account;

/**
 * API共通サービス
 *
 */
public abstract class CommonService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * 指定したアカウントがAPIを実行できるか判定する<br>
	 * 実行出来ない場合throw<br>
	 *
	 * @param request
	 *     リクエスト
	 * @throws BaseException
	 *     基底例外
	 */
	protected void checkApiUse(CommonApiRequest request) throws BaseException {

		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (BeanUtil.isNull(account)) {
			throw new BusinessException(DashboardErrorCode.ACCOUNT_ILLEGAL,
					"アカウントが存在しません userId:" + request.getUserId());
		}

		if (!account.getApiKey().equals(request.getApiKey())) {
			throw new ApiException(ApiErrorCode.API_EXEC_ERROR, "このユーザはAPIを実行できません");
		}
	}
}
