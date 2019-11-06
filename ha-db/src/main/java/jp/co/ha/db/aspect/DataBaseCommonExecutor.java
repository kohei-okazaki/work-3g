package jp.co.ha.db.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.db.EntityCrypter;
import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;

/**
 * DB共通処理クラス
 *
 * @since 1.0
 */
@Aspect
@Component
public class DataBaseCommonExecutor {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(DataBaseCommonExecutor.class);

	/** Entity暗号/復号インターフェース */
	@Autowired
	private EntityCrypter entityCrypter;

	/**
	 * 更新処理の共通処理を行う
	 * <ul>
	 * <li>更新日時の更新</li>
	 * <li>暗号化カラムの暗号化</li>
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
				if (isEntity(entity)) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setUpdateDate".equals(m.getName())) {
							// 更新日時の設定
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					entityCrypter.encrypt(entity);
					LOG.debugRes(entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "setterの実行に失敗しました", e);
		}
	}

	/**
	 * 登録処理の共通処理を行う
	 * <ul>
	 * <li>更新日時の設定</li>
	 * <li>登録日時の設定</li>
	 * <li>暗号化カラムの暗号化</li>
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
				if (isEntity(entity)) {
					for (Method m : entity.getClass().getDeclaredMethods()) {
						if ("setRegDate".equals(m.getName()) || "setUpdateDate".equals(m.getName())) {
							// 登録日時/更新日時の設定
							m.invoke(entity, DateUtil.getSysDate());
						}
					}
					entityCrypter.encrypt(entity);
					LOG.debugRes(entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "setterの実行に失敗しました", e);
		}
	}

	/**
	 * 検索処理の共通処理を行う
	 * <ul>
	 * <li>暗号化カラムの復号</li>
	 * </ul>
	 *
	 * @param pjp
	 *     ProceedingJoinPoint
	 * @return pjp.proceed()
	 * @throws Throwable
	 *     例外発生
	 */
	@SuppressWarnings("unchecked")
	@Around("@annotation(jp.co.ha.common.db.annotation.Select)")
	public Object select(ProceedingJoinPoint pjp) throws Throwable {

		Object o = pjp.proceed();
		if (BeanUtil.isNull(o)) {
			// select結果がない場合
			return o;
		}
		if (o instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) o;
			for (Object entity : list) {
				if (isEntity(entity)) {
					entityCrypter.decrypt(entity);
					LOG.debugRes(entity);
				}
			}
		} else {
			if (isEntity(o)) {
				entityCrypter.decrypt(o);
				LOG.debugRes(o);
			} else if (((Optional<Object>) o).isPresent() && isEntity(((Optional<Object>) o).get())) {
				Object object = ((Optional<Object>) o).get();
				entityCrypter.decrypt(object);
				LOG.debugRes(object);
			}
		}
		return o;
	}

	/**
	 * 削除処理の共通処理を行う
	 *
	 * @param jp
	 *     JoinPoint
	 */
	@Before("@annotation(jp.co.ha.common.db.annotation.Delete)")
	public void delete(JoinPoint jp) {
		Stream.of(jp.getArgs()).filter(e -> isEntity(e)).forEach(e -> LOG.debugRes(e));
	}

	/**
	 * 指定したentityがEntityかどうか判定する<br>
	 * <ul>
	 * <li>Entityの場合、true</li>
	 * <li>それ以外の場合、false</li>
	 * </ul>
	 *
	 * @param entity
	 *     検査Entity
	 * @return 判定結果
	 */
	private boolean isEntity(Object entity) {
		return entity.getClass().isAnnotationPresent(Entity.class);
	}

}
