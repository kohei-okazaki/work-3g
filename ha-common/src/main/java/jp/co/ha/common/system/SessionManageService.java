package jp.co.ha.common.system;

import javax.servlet.http.HttpServletRequest;

/**
 * session管理サービス<br>
 *
 */
public interface SessionManageService {

	/**
	 * セッションから指定された文字列に紐づく値を返す<br>
	 * 指定された値が存在しない場合、nullで返す<br>
	 * @param request
	 * @param key
	 * @return
	 */
	Object getValue(HttpServletRequest request, String key);

	/**
	 * セッション内の指定された文字列を削除する<br>
	 * @param request
	 * @param key
	 */
	void removeValue(HttpServletRequest request, String key);

	/**
	 * <target, value>の形でセッションに設定する<br>
	 * @param request
	 * @param key
	 * @param value
	 */
	void setValue(HttpServletRequest request, String key, Object value);

	/**
	 * セッションに格納されてる情報すべてを削除<br>
	 * @param request
	 */
	void removeValues(HttpServletRequest request);

}
