package jp.co.ha.root.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

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
// Vue.jsからのリクエストを受け付けるため、port=8083のリクエストを許容する
@CrossOrigin(origins = { "http://localhost:8083" })
public abstract class BaseRootApiController<T1 extends BaseRootApiRequest, T2 extends BaseRootApiResponse> {

    /** アプリケーション設定ファイル情報 */
    @Autowired
    protected ApplicationProperties applicationProperties;

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
     * エラーレスポンスを返す
     *
     * @param message
     *     メッセージ
     * @return エラーレスポンス
     */
    protected T2 getErrorResponse(String message) {

        T2 response = getResponse();
        response.setRootApiResult(RootApiResult.FAILURE);
        ErrorData errorData = new ErrorData();
        errorData.setMessage(message);
        response.setErrorData(errorData);

        return response;
    }
}
