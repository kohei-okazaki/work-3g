package jp.co.ha.root.base;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.root.base.BaseRootApiResponse.ErrorData;
import jp.co.ha.root.config.ApplicationProperties;
import jp.co.ha.root.type.RootApiResult;

/**
 * RootAPI基底コントローラ<br>
 * RootAPIのすべてのコントローラクラスは本クラスを継承すること
 *
 * @param <T1>
 *     リクエスト
 * @param <T2>
 *     レスポンス
 * @version 1.0.0
 */
@RequestMapping(value = "api/root")
@ControllerAdvice
// Vue.jsからのリクエストを受け付けるため、port=8083のリクエストを許容する
@CrossOrigin(origins = { "http://localhost:8083" })
public abstract class BaseRootApiController<T1 extends BaseRootApiRequest, T2 extends BaseRootApiResponse> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(BaseRootApiController.class);

    /** アプリケーション設定ファイル */
    @Autowired
    protected ApplicationProperties applicationProperties;
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;

    /**
     * 日付形式不正例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<T2> handleDateTimeParseException(DateTimeParseException e)
            throws BaseException {

        slack.sendError(ContentType.ROOT, e);

        LOG.warn("date format error");

        return ResponseEntity.badRequest().body(getErrorResponse("date format error"));
    }

    /**
     * HttpMessageNotReadable例外ハンドラー<br>
     * API受付時のExceptionクラスの関係で#handleDateTimeParseExceptionを直接ハンドリングできないため
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) throws BaseException {

        Throwable cause = e.getCause();
        slack.sendError(ContentType.ROOT, e);

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;

            if (ife.getCause() instanceof DateTimeParseException) {
                // DateTimeParseExceptionの場合
                LOG.warn("invalid date format.");

                return ResponseEntity.badRequest()
                        .body(getErrorResponse(
                                "date format error. Please use yyyy/MM/dd."));
            }

            if (ife.getTargetType() == BigDecimal.class
                    || ife.getTargetType() == Integer.class
                    || ife.getTargetType() == Long.class
                    || ife.getTargetType() == Double.class) {
                // BigDecimalなどの数値変換エラー

                LOG.warn("invalid number format.");

                return ResponseEntity.badRequest()
                        .body(getErrorResponse(
                                "number format error. Please use numeric value."));
            }

            // 他の型でも InvalidFormatException はここで共通ハンドリング可
        }

        // それ以外はデフォルトの扱いにする
        LOG.error("HttpMessageNotReadableException: " + e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(getErrorResponse("unexpected error."));
    }

    /**
     * リクエストURL不正例外ハンドラー<br>
     * パスパラメータがおかしい場合もこっち
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<T2> handleNoHandlerFoundException(NoHandlerFoundException e)
            throws BaseException {

        LOG.warn("url is illegal", e);

        slack.sendError(ContentType.ROOT, e);

        return ResponseEntity.badRequest().body(getErrorResponse("url is illegal"));
    }

    /**
     * パラメータの型不正例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<T2> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) throws BaseException {

        LOG.warn("parameter or url type error.", e);

        slack.sendError(ContentType.ROOT, e);

        return ResponseEntity.badRequest().body(
                getErrorResponse(
                        e.getName() + " is type error. value=" + e.getValue()));
    }

    /**
     * クエリパラメータの妥当性チェック例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<T2> handleHandlerMethodValidationException(
            HandlerMethodValidationException e) throws BaseException {

        List<String> list = e.getParameterValidationResults().stream()
                .flatMap(r -> r.getResolvableErrors().stream())
                .map(err -> err.getDefaultMessage())
                .toList();

        T2 response = getErrorResponse();

        for (String message : list) {
            LOG.warn("validate error. message=" + message);

            ErrorData errorData = new ErrorData();
            errorData.setMessage(message);
            response.addError(errorData);
        }

        slack.sendError(ContentType.ROOT, e);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 妥当性チェック例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<T2> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) throws BaseException {

        T2 response = getErrorResponse();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {

            LOG.warn("validate error. name=" + error.getField() + ", message="
                    + error.getDefaultMessage());

            ErrorData errorData = new ErrorData();
            errorData.setMessage(error.getDefaultMessage());
            response.addError(errorData);
        }

        slack.sendError(ContentType.ROOT, e);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * クエリパラメータの必須チェック処理
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<T2> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) throws BaseException {

        T2 response = getErrorResponse();

        for (Object o : e.getDetailMessageArguments()) {

            String param = o.toString();

            ErrorData errorData = new ErrorData();
            errorData.setMessage(param + " is required");
            response.addError(errorData);
        }
        slack.sendError(ContentType.ROOT, e);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 以下のエラーハンドリングを行う<br>
     * <ul>
     * <li>アプリケーションエラーの場合、500エラー</li>
     * <li>上記以外の場合、500エラー</li>
     * </ul>
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     * @throws BaseException
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<T2> handleExceptions(Exception e) throws BaseException {

        slack.sendError(ContentType.ROOT, e);

        if (e instanceof BaseException) {
            // アプリケーションエラーの場合
            LOG.warn("application error.", e);

            return ResponseEntity.internalServerError()
                    .body(getErrorResponse("application error."));
        }

        LOG.error("unexpected error.", e);
        // 予期せぬ例外の場合
        return ResponseEntity.internalServerError()
                .body(getErrorResponse("unexpected error."));
    }

    /**
     * レスポンスを返す
     *
     * @return レスポンス
     */
    protected abstract T2 getResponse();

    /**
     * 正常系レスポンスを返す
     *
     * @return 正常系レスポンス
     */
    protected T2 getSuccessResponse() {

        T2 response = getResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

    /**
     * 異常系レスポンスを返す
     *
     * @param messages
     *     メッセージ(可変引数)
     * @return 異常系レスポンス
     */
    protected T2 getErrorResponse(String... messages) {

        T2 response = getResponse();
        response.setRootApiResult(RootApiResult.FAILURE);

        for (String message : messages) {
            ErrorData errorData = new ErrorData();
            errorData.setMessage(message);
            response.addError(errorData);
        }

        return response;
    }
}
