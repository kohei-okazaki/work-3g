package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
import jp.co.ha.web.form.BaseApiResponse.ResultType;
import jp.co.ha.web.form.BaseUserAuthApiRequest;
import jp.co.ha.web.form.BaseUserAuthApiResponse;
import jp.co.ha.web.form.BaseUserAuthApiResponse.ErrorInfo;

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

    // /**
    // * ユーザIDを必要とするGET形式の通信の受付を行う
    // *
    // * @param userId
    // * ユーザID
    // * @param apiKey
    // * APIキー
    // * @param id
    // * ID
    // * @return レスポンス
    // * @throws BaseException
    // * 基底例外
    // */
    // @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    // public Rs doGet(@PathVariable("userId") String userId,
    // @RequestHeader("Api-Key") String apiKey,
    // @RequestParam(value = "id") String id) throws BaseException {
    //
    // Rq request = getApiRequest();
    // Rs response = getApiResponse();
    //
    // request.setUserId(userId);
    // request.setApiKey(apiKey);
    //
    // ValidateErrorResult result = validator.validate(request);
    // if (result.hasError()) {
    // ValidateError error = result.getFirst();
    // // 妥当性チェックエラー
    // throw new ApiException(CommonErrorCode.VALIDATE_ERROR,
    // error.getMessage() + " " + error.getName() + "=" + error.getValue());
    // }
    //
    // Map<String, Object> map = new LinkedHashMap<>();
    // map.put(getQueryName(), id);
    // this.accept(request, response, map);
    //
    // return response;
    // }

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

        Rs response = getApiResponse();
        setErrorResponse(response, baseException);

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

        Rs response = getApiResponse();
        setErrorResponse(response, e);

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

    /**
     * エラー情報を設定
     *
     * @param response
     *     レスポンス情報
     * @param e
     *     アプリで発生した例外
     */
    private void setErrorResponse(Rs response, BaseException e) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setOuterErrorCode(e.getErrorCode().getOuterErrorCode());
        errorInfo.setDetail(e.getDetail());
        response.setErrorInfo(errorInfo);
        response.setResultType(ResultType.FAILURE);
    }

}
