package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * ユーザ認証を必要としないJSON形式のAPIの基底コントローラ<br>
 * ユーザ認証が必要な場合は{@linkplain BaseUserAuthPostRestController}を継承すること
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
@RestController
@Deprecated
public abstract class BaseJsonController<Rq extends BaseApiRequest, Rs extends BaseApiResponse>
        implements ThrowableBiConsumer<Rq, Rs>, BaseController {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 妥当性チェック */
    @Autowired
    private BeanValidator<Rq> validator;

    /**
     * POST形式のJSON通信の受付を行う
     *
     * @param request
     *     リクエスト
     * @return レスポンス
     * @throws BaseException
     *     基底例外
     */
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Rs doPost(@RequestBody Rq request) throws BaseException {

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
