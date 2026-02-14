package jp.co.ha.business.api.healthinfoapp.service;

import static jp.co.ha.business.exception.ApiErrorCode.*;
import static jp.co.ha.business.exception.DashboardErrorCode.*;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.api.healthinfoapp.request.BaseAppApiRequest;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.User;

/**
 * API共通サービス
 *
 * @version 1.0.0
 */
public abstract class CommonService {

    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;

    /**
     * 指定したAPI実行ユーザがAPIを実行できるか判定する<br>
     * 指定したユーザIDが存在しない場合、またはAPIキーが一致しない場合エラーとする
     *
     * @param request
     *     リクエスト
     * @throws BaseException
     *     基底例外
     */
    protected void checkApiUse(BaseAppApiRequest request) throws BaseException {

        // ユーザ情報取得
        User user = userComponent.findById(request.getSeqUserId())
                .orElseThrow(() -> new BusinessException(ACCOUNT_ILLEGAL,
                        "ユーザ情報が存在しません seqUserId:%s".formatted(request.getSeqUserId())));

        if (!user.getApiKey().equals(request.getApiKey())) {
            throw new ApiException(API_EXEC_ERROR,
                    "このユーザはAPIを実行できません。seq_user_id=%s".formatted(request.getSeqUserId()));
        }
    }
}
