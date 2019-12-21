package jp.co.ha.common.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jp.co.ha.common.crypt.Crypter;
import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;

/**
 * Entityの暗号/復号インターフェース実装クラス
 *
 * @since 1.0
 */
@Component
public class EntityCrypterImpl implements EntityCrypter {

	/** 暗号/復号インターフェース */
	@Autowired
	@Qualifier("aesCrypter")
	private Crypter crypter;

	@Override
	public void encrypt(Object entity) throws BaseException {

		try {
			for (Field f : entity.getClass().getDeclaredFields()) {

				if (!isCryptField(f)) {
					// 暗号化項目でない場合
					continue;
				}

				// 暗号化前の値を取得
				Method getter = BeanUtil.getAccessor(f.getName(), entity.getClass(), AccessorType.GETTER);
				Object value = getter.invoke(entity);

				if (BeanUtil.isNull(value)) {
					continue;
				}
				// 暗号化
				String enc = crypter.encrypt(value.toString());
				// 暗号化後の値を設定
				Method setter = BeanUtil.getAccessor(f.getName(), entity.getClass(), AccessorType.SETTER);
				setter.invoke(entity, enc);

			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "entityから値を取得できません", e);
		}
	}

	@Override
	public void decrypt(Object entity) throws BaseException {

		try {
			for (Field f : entity.getClass().getDeclaredFields()) {

				if (!isCryptField(f)) {
					// 暗号化項目でない場合
					continue;
				}

				// 値を取得
				Method getter = BeanUtil.getAccessor(f.getName(), entity.getClass(), AccessorType.GETTER);
				Object value = getter.invoke(entity);

				if (BeanUtil.isNull(value)) {
					continue;
				}
				// 復号
				String dec = crypter.decrypt(value.toString());
				// 復号後の値を設定
				Method setter = BeanUtil.getAccessor(f.getName(), entity.getClass(), AccessorType.SETTER);
				setter.invoke(entity, dec);

			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "entityから値を取得できません", e);
		}
	}

	/**
	 * 指定したフィールドが暗号化項目かどうか判定する<br>
	 * <ul>
	 * <li>暗号化項目の場合、true</li>
	 * <li>それ以外の場合、false</li>
	 * </ul>
	 *
	 * @param f
	 *     フィールド
	 * @return 判定結果
	 */
	private boolean isCryptField(Field f) {
		return f.isAnnotationPresent(Crypt.class);
	}

}
