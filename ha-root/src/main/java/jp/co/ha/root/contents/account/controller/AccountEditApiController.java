package jp.co.ha.root.contents.account.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.account.request.AccountEditApiRequest;
import jp.co.ha.root.contents.account.response.AccountEditApiResponse;

/**
 * アカウント情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class AccountEditApiController
        extends BaseRootApiController<AccountEditApiRequest, AccountEditApiResponse> {

    /** アカウント情報更新サービス */
    @Autowired
    private AccountUpdateService updateService;

    /**
     * アカウント情報編集処理
     *
     * @param seqUserId
     *     ユーザID
     * @param request
     *     アカウント情報編集APIリクエスト
     * @return アカウント情報編集APIレスポンス
     * @throws BaseException
     *     アカウント情報の編集に失敗した場合
     */
    @PutMapping(value = "account/{seq_user_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public AccountEditApiResponse edit(
            @PathVariable(name = "seq_user_id", required = false) Optional<Long> seqUserId,
            @RequestBody AccountEditApiRequest request) throws BaseException {

        if (!seqUserId.isPresent()) {
            return getErrorResponse("seq_user_id is required");
        }

        Account entity = new Account();
        BeanUtil.copy(request, entity);
        entity.setSeqUserId(seqUserId.get());
        updateService.updateSelective(entity);

        AccountEditApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected AccountEditApiResponse getResponse() {
        return new AccountEditApiResponse();
    }

}
