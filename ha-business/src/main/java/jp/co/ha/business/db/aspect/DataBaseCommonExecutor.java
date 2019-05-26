package jp.co.ha.business.db.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateUtil;

/**
 * DB共通処理クラス
 *
 */
@Aspect
@Component
public class DataBaseCommonExecutor {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(DataBaseCommonExecutor.class);

	/**
	 * 更新処理の共通処理を行う
	 * <ul>
	 * <li>更新日時の更新</li>
	 * </ul>
	 *
	 * @param jp
	 *     JoinPoint
	 * @throws BaseException
	 *     基底例外
	 */
	@Before("@annotation(jp.co.ha.common.db.annotation.Update)")
	public void update(JoinPoint jp) throws BaseException {
		try {
			for (Object entity : jp.getArgs()) {
				if (entity.getClass().isAnnotationPresent(Entity.class)) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setUpdateDate".equals(m.getName())) {
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					LOG.infoRes(entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "setterの実行に失敗しました", e);
		}
	}

	/**
	 * 削除処理の共通処理を行う
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@Before("@annotation(jp.co.ha.common.db.annotation.Delete)")
	public void delete(JoinPoint jp) {
		for (Object entity : jp.getArgs()) {
			if (entity.getClass().isAnnotationPresent(Entity.class)) {
				LOG.infoRes(entity);
			}
		}
	}

	/**
	 * 登録処理の共通処理を行う
	 * <ul>
	 * <li>更新日時の設定</li>
	 * <li>登録日時の設定</li>
	 * </ul>
	 *
	 * @param jp
	 *     JoinPoint
	 * @throws BaseException
	 *     基底例外
	 */
	@Before("@annotation(jp.co.ha.common.db.annotation.Insert)")
	public void insert(JoinPoint jp) throws BaseException {
		try {
			for (Object entity : jp.getArgs()) {
				if (entity.getClass().isAnnotationPresent(Entity.class)) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setRegDate".equals(m.getName()) || "setUpdateDate".equals(m.getName())) {
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					LOG.infoRes(entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "setterの実行に失敗しました", e);
		}
	}
}
