package jp.co.ha.common.system;

import javax.servlet.http.HttpSession;

/**
 * session管理サービス
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
	 * 指定されたキー名/型のセッション情報を取得する
	 *
	 * @param session
	 *     HttpSession
	 * @param key
	 *     セッションキー
	 * @param clazz
	 *     型
	 * @return セッション情報
	 */
	<T> T getValue(HttpSession session, String key, Class<T> clazz);

}
