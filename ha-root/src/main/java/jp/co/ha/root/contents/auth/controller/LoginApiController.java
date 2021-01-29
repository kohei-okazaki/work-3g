package jp.co.ha.root.contents.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.base.BaseRootApiResponse.ErrorData;
import jp.co.ha.root.contents.auth.request.LoginApiRequest;
import jp.co.ha.root.contents.auth.response.LoginApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * ログインAPIコントローラ<br>
 * Auth Moduleからリクエストを送っているため、 ヘッダにurlencodedが設定されない<br>
 * axios.postの場合で受け取る以下の方法ではリクエストのJSONをバインドできない<br>
 * <code>&#64;RequestBody MultiValueMap<String, Object> request</code><br>
 * そのためそのままRequestクラスで受け取る
 *
 * @version 1.0.0
 */
@RestController
@Deprecated
public class LoginApiController
        extends BaseRootApiController<LoginApiRequest, LoginApiResponse> {

    /** 管理者サイトユーザログイン情報検索サービス */
    @Autowired
    private RootLoginInfoSearchService searchService;

    /**
     * ログインAPI受付
     *
     * @param request
     *     ログインAPIリクエスト
     * @param br
     *     {@linkplain BindingResult}
     * @return ログインAPIレスポンス
     */
    @PostMapping(value = "login", produces = { MediaType.APPLICATION_JSON_VALUE })
    public LoginApiResponse login(@Validated @RequestBody LoginApiRequest request,
            BindingResult br) {

        LoginApiResponse response = new LoginApiResponse();

        if (br.hasErrors()) {
            ErrorData errorData = new ErrorData();
            response.setRootApiResult(RootApiResult.FAILURE);
            errorData.setMessage("required error");
            response.setErrorData(errorData);
            return response;
        }

        // 管理者サイトユーザログイン情報を検索
        RootLoginInfo entity = searchService.findById(request.getSeqLoginId()).get();

        response.setRootApiResult(RootApiResult.SUCCESS);
        String strdate = DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                DateFormatType.YYYYMMDDHHMMSS_NOSEP_STRICT);
        response.setToken("authed_" + strdate);
        return response;
    }

}
