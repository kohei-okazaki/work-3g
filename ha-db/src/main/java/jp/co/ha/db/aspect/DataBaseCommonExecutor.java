package jp.co.ha.db.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jp.co.ha.common.db.Crypter;
import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
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
	/** 暗号/復号インターフェース */
	@Autowired
	@Qualifier("aesCrypter")
	private Crypter crypter;

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
					encryptEntity(entity);
					LOG.infoRes(entity);
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
					encryptEntity(entity);
					LOG.infoRes(entity);
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
					decryptEntity(entity);
					LOG.infoRes(entity);
				}
			}
		} else {
			if (isEntity(o)) {
				decryptEntity(o);
				LOG.infoRes(o);
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
		Stream.of(jp.getArgs()).filter(e -> isEntity(e)).forEach(e -> LOG.infoRes(e));
	}

	/**
	 * entityの復号を行う
	 *
	 * @param entity
	 *     対象Entity
	 *
	 * @throws BaseException
	 *     基底例外
	 */
	private void decryptEntity(Object entity)
			throws BaseException {
		try {
			for (Field f : entity.getClass().getDeclaredFields()) {
				if (isCryptField(f)) {
					// 値を取得
					Method getter = BeanUtil.getAccessor(f.getName(), entity.getClass(),
							AccessorType.GETTER);
					Object value = getter.invoke(entity);

					if (value != null) {

						// 復号化
						String dec = crypter.decrypt(value.toString());
						// 復号後の値を設定
						Method setter = BeanUtil.getAccessor(f.getName(), entity.getClass(),
								AccessorType.SETTER);
						setter.invoke(entity, dec);
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "entityから値を取得できません", e);
		}

	}

	/**
	 * entityの暗号化を行う
	 *
	 * @param entity
	 *     対象Entity
	 * @throws BaseException
	 *     基底例外
	 */
	private void encryptEntity(Object entity) throws BaseException {
		try {
			for (Field f : entity.getClass().getDeclaredFields()) {
				if (isCryptField(f)) {
					// 暗号化前の値を取得
					Method getter = BeanUtil.getAccessor(f.getName(), entity.getClass(), AccessorType.GETTER);
					Object value = getter.invoke(entity);

					if (value != null) {
						// 暗号化
						String enc = crypter.encrypt(value.toString());

						// 暗号化後の値を設定
						Method setter = BeanUtil.getAccessor(f.getName(), entity.getClass(),
								AccessorType.SETTER);
						setter.invoke(entity, enc);
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "entityから値を取得できません", e);
		}

	}

	/**
	 * 指定したentityがEntityかどうか判定する<br>
	 * Entityの場合true<br>
	 * それ以外の場合false
	 *
	 * @param entity
	 *     検査Entity
	 * @return 判定結果
	 */
	private boolean isEntity(Object entity) {
		return entity.getClass().isAnnotationPresent(Entity.class);
	}

	/**
	 * 指定したフィールドが暗号化項目かどうか判定する 暗号化項目の場合true<br>
	 * それ以外の場合false
	 *
	 * @param f
	 *     フィールド
	 * @return 判定結果
	 */
	private boolean isCryptField(Field f) {
		return f.isAnnotationPresent(Crypt.class);
	}
}
