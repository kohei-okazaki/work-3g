package jp.co.ha.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ErrorInfo;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseAppError;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/***
 * REST APIの例外ハンドラークラス<br>
 * ユーザの認証有無に関わらず、ha-apiで発生した例外については本クラスで制御を行う
 *
 * @version 1.0.0
 */
@RestControllerAdvice
public class RestApiExceptionHandler extends BaseExceptionHandler {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(RestApiExceptionHandler.class);
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slack;

    /**
     * APIで発生した例外をハンドリングする<br>
     * 発生した例外が{@linkplain java.lang.Exception}などのJavaの例外はすべてアプリ用の例外でラップする
     *
     * @param e
     *     APIで発生した例外
     * @return クライアントへ返すレスポンス
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseAppApiResponse handleException(Exception e) {

        slack.send(ContentType.API, super.getLogErrorMessage(e));
        slack.sendError(ContentType.API, e);

        BaseAppError appError = getAppError(e);

        // 例外からレスポンスを作成
        BaseAppApiResponse response = new BaseAppApiResponse();
        response.setResultType(ResultType.FAILURE);
        ErrorInfo error = new ErrorInfo();
        error.setOuterErrorCode(appError.getErrorCode().getOuterErrorCode());
        error.setDetail(appError.getDetail());
        response.setErrorInfo(error);

        // 例外のログレベルからログを出力
        switch (appError.getErrorCode().getLogLevel()) {
        case WARN:
            LOG.warnBean(response, (Exception) appError);
            break;
        case ERROR:
            LOG.errorBean(response, (Exception) appError);
            break;
        default:
            // WARNとERROR以外は何もしない
            break;
        }

        return response;
    }

    @Override
    protected BaseAppError getAppError(Exception e) {

        BaseAppError error = null;
        if (e instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) e;
            error = new ApiException(CommonErrorCode.JSON_FORMAT_ERROR,
                    ife.getValue() + "はリクエスト形式エラーです", e);
        } else if (e instanceof JsonParseException) {
            error = new ApiException(CommonErrorCode.JSON_PARSE_ERROR,
                    "JSON形式ではありません", e);
        } else if (e instanceof JsonProcessingException) {
            error = new ApiException(CommonErrorCode.JSON_PARSE_ERROR,
                    "JSON生成処理が失敗しました", e);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException matme = (MethodArgumentTypeMismatchException) e;

            error = new ApiException(CommonErrorCode.VALIDATE_ERROR,
                    "リクエストパラメータが不正です。 パラメータ名:" + matme.getName() + ", 値="
                            + matme.getValue(),
                    e);
        } else {
            error = super.getAppError(e);
        }

        return error;
    }

}
