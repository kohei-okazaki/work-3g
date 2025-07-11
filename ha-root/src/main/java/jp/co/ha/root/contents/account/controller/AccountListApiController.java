package jp.co.ha.root.contents.account.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.account.request.AccountListApiRequest;
import jp.co.ha.root.contents.account.response.AccountListApiResponse;

/**
 * アカウント情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class AccountListApiController
        extends BaseRootApiController<AccountListApiRequest, AccountListApiResponse> {

    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService service;

    /**
     * アカウント情報一覧取得
     *
     * @param request
     *     アカウント情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return アカウント情報一覧取得APIレスポンス
     */
    @GetMapping(value = "account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AccountListApiResponse> list(AccountListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = 0, message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getPage());

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_USER_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        List<AccountListApiResponse.AccountResponse> accountList = service
                .findAll(selectOption).stream()
                .map(e -> {
                    AccountListApiResponse.AccountResponse response = new AccountListApiResponse.AccountResponse();
                    BeanUtil.copy(e, response);
                    response.setDeleteFlag(e.isDeleteFlag() ? CommonFlag.TRUE.getValue()
                            : CommonFlag.FALSE.getValue());
                    return response;
                }).collect(Collectors.toList());

        PagingView paging = PagingViewFactory.getPageView(pageable, "account?page",
                service.countBySeqUserId(null));

        AccountListApiResponse response = getSuccessResponse();
        response.setAccountResponseList(accountList);
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @Override
    protected AccountListApiResponse getResponse() {
        return new AccountListApiResponse();
    }

}
