package jp.co.ha.root.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    /** {@linkplain ApplicationProperties} */
    @Autowired
    protected ApplicationProperties applicationProperties;

    /**
     * リクエストURL不正例外ハンドラー<br>
     * パスパラメータがおかしい場合もこっち
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<T2> handleNoHandlerFoundException(NoHandlerFoundException e) {

        LOG.warn("url is illegal", e);

        return ResponseEntity.badRequest().body(getErrorResponse("url is illegal"));
    }

    /**
     * パラメータの型不正例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<T2> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {

        LOG.warn("parameter or url type error.");

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
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<T2> handleHandlerMethodValidationException(
            HandlerMethodValidationException e) {

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

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 妥当性チェック例外ハンドラー
     *
     * @param e
     *     例外クラス
     * @return 異常系レスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<T2> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        T2 response = getErrorResponse();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {

            LOG.warn("validate error. name=" + error.getField() + ", message="
                    + error.getDefaultMessage());

            ErrorData errorData = new ErrorData();
            errorData.setMessage(error.getDefaultMessage());
            response.addError(errorData);
        }

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
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<T2> handleExceptions(Exception e) {

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
