package jp.co.ha.dashboard.account.service;

import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント設定サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountSettingService {

    /**
     * アカウント設定のメイン処理を行う
     *
     * @param dto
     *     アカウントDTO
     * @throws BaseException
     *     基底例外
     */
    void execute(AccountDto dto) throws BaseException;

}
