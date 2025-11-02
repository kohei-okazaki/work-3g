package jp.co.ha.common.system;

import java.util.Enumeration;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * session操作のComponentクラス
 *
 * @version 1.0.0
 */
@Component
public class SessionComponent {

    /**
     * 指定した<code>key</code>の情報を<code>{@linkplain HttpSession}</code>から削除する
     *
     * @param session
     *     {@linkplain HttpSession}
     * @param key
     *     セッションに登録されているキー
     */
    public void removeValue(HttpSession session, String key) {
        session.removeAttribute(key);
    }

    /**
     * 指定した<code>key</code>と<code>value</code>のペアを<code>{@linkplain HttpSession}</code>に登録する
     *
     * @param session
     *     {@linkplain HttpSession}
     * @param key
     *     セッションに登録されているキー
     * @param value
     *     セッションに登録する値
     */
    public void setValue(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    /**
     * <code>{@linkplain HttpSession}</code>に登録されているデータをすべて削除する
     *
     * @param session
     *     {@linkplain HttpSession}
     */
    public void removeValues(HttpSession session) {
        Enumeration<String> enm = session.getAttributeNames();
        while (enm.hasMoreElements()) {
            this.removeValue(session, enm.nextElement());
        }
    }

    /**
     * <code>{@linkplain HttpSession}</code>より、指定した<code>key</code>で情報を取得する<br>
     * 取得後の型は<code>clazz</code>の型で型変換された{@linkplain Optional}を返す
     *
     * @param <T>
     *     変換したい型のオブジェクト
     * @param session
     *     {@linkplain HttpSession}
     * @param key
     *     セッションキー
     * @param clazz
     *     変換したい型
     * @return Optional<T>のデータ
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(HttpSession session, String key, Class<T> clazz) {
        return Optional.ofNullable((T) session.getAttribute(key));
    }

    /**
     * 指定した{@linkplain HttpServletRequest}よりセッションIDを返す<br>
     * {@linkplain WebUtils#getSessionId}のラッパーメソッド
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return セッションID
     */
    public String getSessionId(HttpServletRequest request) {
        return WebUtils.getSessionId(request);
    }

}
