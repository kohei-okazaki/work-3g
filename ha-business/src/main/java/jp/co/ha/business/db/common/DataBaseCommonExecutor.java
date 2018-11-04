package jp.co.ha.business.db.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.annotation.Entity;

/**
 * DBの共通処理クラス<br>
 *
 */
@Aspect
@Component
public class DataBaseCommonExecutor {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * 更新処理の共通処理を行う<br>
	 * <ul>
	 * <li>更新日時の更新</li>
	 * </ul>
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@Before("execution(* *..*UpdateServiceImpl.update(..))*")
	public void update(JoinPoint jp) {
		try {
			for (Object arg : jp.getArgs()) {
				if (BeanUtil.notNull(arg.getClass().getAnnotation(Entity.class))) {
					LOG.info("■■■SQL■■■");
					for (Method m : arg.getClass().getDeclaredMethods()) {
						if ("setUpdateDate".equals(m.getName())) {
							m.invoke(arg, DateUtil.getSysDate());
						}
					}
					LOG.infoRes(arg);
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
	@Before("execution(* *..*CreateServiceImpl.create(..))*")
	public void regist(JoinPoint jp) {
		try {
			for (Object arg : jp.getArgs()) {
				if (BeanUtil.notNull(arg.getClass().getAnnotation(Entity.class))) {
					LOG.info("■■■SQL■■■");
					for (Method m : arg.getClass().getDeclaredMethods()) {
						if ("setRegDate".equals(m.getName()) || "setUpdateDate".equals(m.getName())) {
							m.invoke(arg, DateUtil.getSysDate());
						}
					}
					LOG.infoRes(arg);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("setterの実行に失敗しました", e);
		}
	}
}
