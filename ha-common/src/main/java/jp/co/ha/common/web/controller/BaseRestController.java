package jp.co.ha.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.function.ThrowableBiConsumer;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.common.validator.ValidateErrorResult.ValidateError;
import jp.co.ha.common.web.form.BaseRestApiRequest;
import jp.co.ha.common.web.form.BaseRestApiResponse;

/**
 * ユーザ認証の必要なRest APIのGETメソッド受付の基底コントローラ<br>
 * ユーザ認証が必要な場合は本クラスを継承すること<br>
 * TODO 各REST APIのコントローラが継承するこの基底コントローラで処理の大きな流れを実装し、<br>
 * 各個別処理を継承先のコントローラで実装したいが<br>
 * <ul>
 * <li>/api/{userId}/{function}/***</li>
 * <li>/api/hoge/***</li>
 * </ul>
 * のような自由なURI設計ができないため、<br>
 * このクラスの継承クラスに{@linkplain RequestMapping}をつけて継承先でリクエストを受け付けるように実装している<br>
 *
 * @param <Rq>
 *     リクエスト
 * @param <Rs>
 *     レスポンス
 * @version 1.0.0
 */
public abstract class BaseRestController<Rq extends BaseRestApiRequest, Rs extends BaseRestApiResponse>
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

}
