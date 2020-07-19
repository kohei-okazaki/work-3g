package jp.co.ha.dashboard.healthinfo.service;

import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報メールサービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoMailService {

    /**
     * 健康情報登録確認メールを送信する
     *
     * @param apiResponse
     *     健康情報登録APIレスポンス
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    void sendHealthInfoMail(HealthInfoRegistResponse apiResponse) throws BaseException;

}
