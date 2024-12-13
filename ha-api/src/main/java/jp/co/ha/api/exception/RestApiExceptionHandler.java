package jp.co.ha.api.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.BaseExceptionHandler;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;

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
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;

    /**
     * APIで発生した例外をハンドリングする<br>
     * 発生した例外が{@linkplain java.lang.Exception}などのJavaの例外はすべてアプリ用の例外でラップする
     *
     * @param e
     *     APIで発生した例外
     * @return クライアントへ返すレスポンス
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public BaseAppApiResponse handleException(Exception e) {

        // Slackに通知
        try (StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);) {
            e.printStackTrace(pw);
            slackApiComponent.sendFile(ContentType.DASHBOARD,
                    sw.toString().getBytes(Charset.UTF_8.getValue()), "エラー情報",
                    "stack-trace", getLogErrorMessage(e));
        } catch (BaseException | IOException be) {
            LOG.error("slack通知に失敗しました", be);
        }

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
        } else {
            error = super.getAppError(e);
        }

        return error;
    }

}
