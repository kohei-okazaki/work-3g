package jp.co.ha.dashboard.user.service;

import jp.co.ha.business.dto.UserDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.dashboard.user.form.UserSettingForm;

/**
 * ユーザ設定サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserSettingService {

    /**
     * ユーザ設定Formを返す
     * 
     * @param seqUserId
     *     ユーザID
     * @return ユーザ設定Form
     */
    UserSettingForm getForm(Long seqUserId);

    /**
     * ユーザ設定のメイン処理を行う
     *
     * @param dto
     *     ユーザDTO
     * @throws BaseException
     *     基底例外
     */
    void execute(UserDto dto) throws BaseException;

}
