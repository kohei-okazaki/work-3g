package jp.co.ha.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.web.form.BaseApiResponse.ErrorInfo;
import jp.co.ha.web.form.BaseApiResponse.ResultType;
import jp.co.ha.web.form.BaseRestApiResponse;

/***
 * REST APIの例外ハンドラークラス<br>
 * ユーザの認証有無に関わらず、すべてのAPIで発生した例外については本クラスで制御を行う
 *
 * @version 1.0.0
 */
@RestControllerAdvice
public class RestApiExceptionHandler {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(RestApiExceptionHandler.class);

    /**
     * APIで発生した例外をハンドリングする<br>
     * {@linkplain java.lang.Exception}などのJavaの例外をすべてアプリの用の例外でラップする
     *
     * @param e
     *     APIで発生した例外
     * @return クライアントへ返すレスポンス
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public BaseRestApiResponse handleException(Exception e) {

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
        } else if (e instanceof BaseException) {
            // アプリで発生した例外
            baseException = (BaseException) e;
        } else {
            baseException = new ApiException(CommonErrorCode.UNEXPECTED_ERROR,
                    "JSON生成で予期せぬエラーが発生しました", e);
        }

        // 例外からレスポンスを作成
        BaseRestApiResponse response = new BaseRestApiResponse();
        response.setResultType(ResultType.FAILURE);
        ErrorInfo error = new ErrorInfo();
        error.setOuterErrorCode(baseException.getErrorCode().getOuterErrorCode());
        error.setDetail(baseException.getDetail());
        response.setErrorInfo(error);

        // 例外のログレベルからログを出力
        switch (baseException.getErrorCode().getLogLevel()) {
        case WARN:
            LOG.warnRes(response, baseException);
            break;
        case ERROR:
            LOG.errorRes(response, baseException);
            break;
        default:
            // WARNとERROR以外は何もしない
            break;
        }

        return response;
    }

}
