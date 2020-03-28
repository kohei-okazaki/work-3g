package jp.co.ha.dashboard.account.service;

import jp.co.ha.business.dto.AccountDto;

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
     */
    void execute(AccountDto dto);

}
