package jp.co.ha.root.base;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

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
// Vue.jsからのリクエストを受け付けるため、port=3000のリクエストを許容する
@CrossOrigin(origins = { "http://localhost:8083" })
public abstract class BaseRootApiController<T1 extends BaseRootApiRequest, T2 extends BaseRootApiResponse> {

}
