package jp.co.ha.dashboard.user.service;

import jp.co.ha.business.dto.UserDto;
import jp.co.ha.common.exception.BaseException;

/**
 * ユーザ登録サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserRegistService {

    /**
     * 登録処理を行う
     *
     * @param dto
     *     ユーザDTO
     * @throws BaseException
     *     基底例外
     */
    void regist(UserDto dto) throws BaseException;

}
