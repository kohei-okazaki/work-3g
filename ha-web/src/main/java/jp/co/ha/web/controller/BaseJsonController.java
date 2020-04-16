package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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
import jp.co.ha.web.form.ErrorResponse;

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

    /**
     * JSONで例外が起きた場合のエラーハンドリング
     *
     * @param e
     *     JSON系のエラー
     * @return エラーレスポンス
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(JsonProcessingException.class)
    public Rs jsonExceptionHandle(JsonProcessingException e) {

        BaseException baseException = null;
        if (e instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) e;
            baseException = new ApiException(CommonErrorCode.JSON_FORMAT_ERROR,
                    ife.getValue() + "はリクエスト形式エラーです", e);
        } else if (e instanceof JsonParseException) {
            baseException = new ApiException(CommonErrorCode.JSON_PARSE_ERROR,
                    "JSON形式ではありません", e);
        } else if (e instanceof JsonProcessingException) {
            baseException = new ApiException(CommonErrorCode.JSON_PARSE_ERROR,
                    "JSON生成処理が失敗しました", e);
        } else {
            baseException = new ApiException(CommonErrorCode.UNEXPECTED_ERROR,
                    "JSON生成で予期せぬエラーが発生しました", e);
        }

        @SuppressWarnings("unchecked")
        Rs response = (Rs) new ErrorResponse(baseException);
        LOG.warnRes(response, baseException);

        return response;
    }

    /**
     * アプリケーション例外のエラーハンドリング
     *
     * @param e
     *     アプリエラー
     * @return エラーレスポンス
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public Rs appExceptionHandle(BaseException e) {

        @SuppressWarnings("unchecked")
        Rs response = (Rs) new ErrorResponse(e);
        switch (e.getErrorCode().getLogLevel()) {
        case WARN:
            LOG.warnRes(response, e);
            break;
        case ERROR:
            LOG.errorRes(response, e);
            break;
        default:
            break;
        }
        return response;
    }

}
