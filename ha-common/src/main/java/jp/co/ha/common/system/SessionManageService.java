package jp.co.ha.common.system;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.util.WebUtils;

/**
 * session管理サービス
 *
 * @since 1.0
 *
 */
public interface SessionManageService {

	/**
	 * セッション内の指定された文字列を削除する
	 *
	 * @param session
	 *     HttpSession
	 * @param key
	 *     セッションキー
	 */
	void removeValue(HttpSession session, String key);

	/**
	 * <key, value>の形でセッションに設定する
	 *
	 * @param session
	 *     HttpSession
	 * @param key
	 *     セッションキー
	 * @param value
	 *     セッション値
	 */
	void setValue(HttpSession session, String key, Object value);

	/**
	 * セッションに格納されてる情報すべてを削除
	 *
	 * @param session
	 *     HttpSession
	 */
	void removeValues(HttpSession session);

	/**
	 * 指定されたキー名/型のセッション情報を取得する<br>
	 * 呼び出し元で以下の方法でT型に変換して下さい<br>
	 * <ul>
	 * <li>getValue(session, "key", T.class).get()</li>
	 * <li>getValue(session, "key", T.class).orElseThrow(() -> new
	 * SystemException())</li>
	 * </ul>
	 *
	 * @param session
	 *     HttpSession
	 * @param key
	 *     セッションキー
	 * @param clazz
	 *     型
	 * @return セッション情報
	 */
	<T> Optional<T> getValue(HttpSession session, String key, Class<T> clazz);

	/**
	 * 指定されたリクエストのセッションIDを返す<br>
	 * {@linkplain WebUtils#getSessionId(HttpServletRequest)}のラッパーメソッド
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return セッションID
	 */
	String getSessionId(HttpServletRequest request);

}
