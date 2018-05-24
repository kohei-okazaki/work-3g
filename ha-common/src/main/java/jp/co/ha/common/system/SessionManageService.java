package jp.co.ha.common.system;

import javax.servlet.http.HttpServletRequest;

/**
 * session管理サービス<br>
 *
 */
public interface SessionManageService {

	/**
	 * セッション内の指定された文字列を削除する<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param key
	 *            セッションキー
	 */
	void removeValue(HttpServletRequest request, String key);

	/**
	 * <target, value>の形でセッションに設定する<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param key
	 *            セッションキー
	 * @param value
	 *            セッション値
	 */
	void setValue(HttpServletRequest request, String key, Object value);

	/**
	 * セッションに格納されてる情報すべてを削除<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 */
	void removeValues(HttpServletRequest request);

	/**
	 * 指定されたキー名/型のセッション情報を取得する<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param key
	 *            セッションキー
	 * @param clazz
	 *            型
	 * @return
	 */
	<T> T getValue(HttpServletRequest request, String key, Class<T> clazz);

}
