package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.function.ThrowableBiConsumer;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.web.form.BaseUserAuthApiRequest;
import jp.co.ha.web.form.BaseUserAuthApiResponse;

/**
 * ユーザ認証の必要なRest APIのPOSTメソッド受付の基底コントローラ<br>
 * ユーザ認証が必要な場合は本クラスを継承すること<br>
 * TODO POST形式だとJSONに必要な情報が設定できるので基底クラスに<br>
 * PostMappingアノテーションのリクエスト受付を行う<br>
 * ただし、今後URLをPathVariableを増やすようなURI設計のAPIが必要な場合、<br>
 * {@linkplain BaseUserAuthGetRestController}のような
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
@RestController
public abstract class BaseUserAuthPostRestController<Rq extends BaseUserAuthApiRequest, Rs extends BaseUserAuthApiResponse>
        implements ThrowableBiConsumer<Rq, Rs>, BaseController {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 妥当性チェック */
    @Autowired
    protected BeanValidator<Rq> validator;

    /**
     * ユーザIDを必要とするPOST形式のJSON通信の受付を行う
     *
     * @param userId
     *     ユーザID
     * @param apiKey
     *     APIキー
     * @param request
     *     リクエスト情報
     * @return レスポンス
     * @throws BaseException
     *     基底例外
     */
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Rs doPost(@PathVariable("userId") String userId,
            @RequestHeader("Api-Key") String apiKey, @RequestBody Rq request)
            throws BaseException {

        request.setUserId(userId);
        request.setApiKey(apiKey);

        ValidateErrorResult result = validator.validate(request);
        if (result.hasError()) {
            ValidateError error = result.getFirst();
            // 妥当性チェックエラー
            throw new ApiException(CommonErrorCode.VALIDATE_ERROR,
                    error.getMessage() + " " + error.getName() + "=" + error.getValue());
        }

        Rs response = getApiResponse();

        this.accept(request, response);

        return response;
    }

    /**
     * APIレスポンスを返す
     *
     * @return APIレスポンス
     */
    protected abstract Rs getApiResponse();

}
