package jp.co.ha.business.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.api.request.CommonApiRequest;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.Account;

/**
 * API共通サービス
 *
 * @since 1.0
 */
public abstract class CommonService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * 指定したAPI実行ユーザがAPIを実行できるか判定する<br>
	 * 実行出来ない場合throw<br>
	 *
	 * @param request
	 *     リクエスト
	 * @throws BaseException
	 *     基底例外
	 */
	protected void checkApiUse(CommonApiRequest request) throws BaseException {

		// アカウント情報取得
		Account account = accountSearchService.findByUserId(request.getUserId())
				.orElseThrow(() -> new BusinessException(DashboardErrorCode.ACCOUNT_ILLEGAL,
						"アカウント情報が存在しません userId:" + request.getUserId()));

		if (!account.getApiKey().equals(request.getApiKey())) {
			throw new ApiException(ApiErrorCode.API_EXEC_ERROR, "このユーザはAPIを実行できません");
		}
	}
}
