package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

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
 * ユーザ認証の必要なRest APIのGETメソッド受付の基底コントローラ<br>
 * ユーザ認証が必要な場合は本クラスを継承すること<br>
 * TODO
 * {@link BaseUserAuthPostRestController#doPost(String, String, BaseUserAuthApiRequest)}<br>
 * のように親クラスでリクエスト受付を行いたいが、/api/{userId}/${function}/***のような自由なURI設計ができないため、<br>
 * このクラスの継承クラスにGetMappingを付けている
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public abstract class BaseUserAuthGetRestController<Rq extends BaseUserAuthApiRequest, Rs extends BaseUserAuthApiResponse>
        implements ThrowableBiConsumer<Rq, Rs>, BaseController {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 妥当性チェック */
    @Autowired
    protected BeanValidator<Rq> validator;

    /**
     * 妥当性チェックを行う
     *
     * @param request
     *     リクエスト
     * @throws BaseException
     *     妥当性チェックエラーが発生した場合
     */
    protected void validate(Rq request) throws BaseException {
        ValidateErrorResult result = validator.validate(request);
        if (result.hasError()) {
            ValidateError error = result.getFirst();
            // 妥当性チェックエラー
            throw new ApiException(CommonErrorCode.VALIDATE_ERROR,
                    error.getMessage() + " " + error.getName() + "=" + error.getValue());
        }
    }

    /**
     * APIリクエストを返す
     *
     * @return APIリクエスト
     */
    protected abstract Rq getApiRequest();

    /**
     * APIレスポンスを返す
     *
     * @return APIレスポンス
     */
    protected abstract Rs getApiResponse();

}
