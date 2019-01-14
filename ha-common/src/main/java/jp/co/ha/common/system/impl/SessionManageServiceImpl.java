package jp.co.ha.common.system.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import jp.co.ha.common.system.SessionManageService;

/**
 * session管理サービス実装クラス
 *
 */
public class SessionManageServiceImpl implements SessionManageService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeValue(HttpSession session, String key) {
		session.removeAttribute(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(HttpSession session, String key, Object value) {
		session.setAttribute(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeValues(HttpSession session) {
		Enumeration<String> enm = session.getAttributeNames();
		while (enm.hasMoreElements()) {
			this.removeValue(session, enm.nextElement());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(HttpSession session, String key, Class<T> clazz) {
		return (T) session.getAttribute(key);
	}

}
