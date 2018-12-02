package jp.co.ha.business.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.annotation.Entity;

/**
 * DB接続の共通処理クラス<br>
 *
 */
@Aspect
public class DataBaseCommonExecutor {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 更新処理の共通処理を行う<br>
	 * <ul>
	 * <li>更新日時の更新</li>
	 * </ul>
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@Before("@annotation(jp.co.ha.business.db.annotation.Update)")
	public void update(JoinPoint jp) {
		try {
			for (Object entity : jp.getArgs()) {
				if (BeanUtil.notNull(entity.getClass().getAnnotation(Entity.class))) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setUpdateDate".equals(m.getName())) {
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					LOG.infoRes("■■■SQL■■■", entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("setterの実行に失敗しました", e);
		}
	}

	/**
	 * 登録処理の共通処理を行う<br>
	 * <ul>
	 * <li>更新日時の設定</li>
	 * <li>登録日時の設定</li>
	 * </ul>
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@Before("@annotation(jp.co.ha.business.db.annotation.Insert)")
	public void insert(JoinPoint jp) {
		try {
			for (Object entity : jp.getArgs()) {
				if (BeanUtil.notNull(entity.getClass().getAnnotation(Entity.class))) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setRegDate".equals(m.getName()) || "setUpdateDate".equals(m.getName())) {
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					LOG.infoRes("■■■SQL■■■", entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("setterの実行に失敗しました", e);
		}
	}
}
