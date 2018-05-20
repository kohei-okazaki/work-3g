package jp.co.ha.common.system.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import jp.co.ha.common.system.SessionManageService;

/**
 * session管理サービス実装クラス<br>
 *
 */
@Service
public class SessionManageServiceImpl implements SessionManageService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeValue(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		session.removeAttribute(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(HttpServletRequest request, String key, Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeValues(HttpServletRequest request) {
		Enumeration<String> enm= request.getSession().getAttributeNames();
		while(enm.hasMoreElements()) {
			this.removeValue(request, enm.nextElement());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getValue(HttpServletRequest request, String key, Class<T> clazz) {
		return (T) request.getSession().getAttribute(key);
	}

}
