package jp.co.ha.root.contents.account.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.composite.CompositeAccount;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.account.request.AccountListApiRequest;
import jp.co.ha.root.contents.account.response.AccountListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * アカウント情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class AccountListApiController
        extends BaseRootApiController<AccountListApiRequest, AccountListApiResponse> {

    /** アカウント情報検索サービス */
    @Autowired
    private AccountSearchService service;

    /**
     * アカウント情報一覧取得
     *
     * @param request
     *     アカウント情報一覧取得APIリクエスト
     * @return アカウント情報一覧取得APIレスポンス
     */
    @GetMapping(value = "account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public AccountListApiResponse list(AccountListApiRequest request) {

        List<CompositeAccount> list = service.findAll();
        List<AccountListApiResponse.Account> accountList = new ArrayList<>();
        for (CompositeAccount account : list) {
            AccountListApiResponse.Account accountResponse = new AccountListApiResponse.Account();
            BeanUtil.copy(account, accountResponse);
            accountList.add(accountResponse);
        }

        AccountListApiResponse response = new AccountListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setAccountList(accountList);

        return response;
    }

}
