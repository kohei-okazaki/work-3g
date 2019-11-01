package jp.co.ha.common.system;

import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

/**
 * session管理サービス実装クラス
 * 
 * @since 1.0
 */
@Service
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
	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<T> getValue(HttpSession session, String key, Class<T> clazz) {
		return Optional.ofNullable((T) session.getAttribute(key));
	}

}
