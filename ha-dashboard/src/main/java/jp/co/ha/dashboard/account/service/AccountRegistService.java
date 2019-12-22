package jp.co.ha.dashboard.account.service;

import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント登録サービスインターフェース
 *
 * @since 1.0
 */
public interface AccountRegistService {

	/**
	 * 登録処理を行う
	 *
	 * @param dto
	 *     アカウントDTO
	 * @throws BaseException
	 *     基底例外
	 */
	void regist(AccountDto dto) throws BaseException;

}
