package jp.co.ha.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bean系のUtilクラス<br>
 *
 */
public class BeanUtil {

	/**
	 * dataのフィールドをtargetのフィールドにコピーする<br>
	 * コピー先のクラスと同じフィールド名の場合コピー元のフィールドに値を設定する<br>
	 *
	 * @param data
	 *            コピー元
	 * @param target
	 *            コピー先
	 */
	public static void copy(Object data, Object target) {
		copy(data, target, new ArrayList<String>());
	}

	/**
	 * dataのフィールドをtargetのフィールドにコピーする<br>
	 * コピー先のクラスと同じフィールド名の場合コピー元のフィールドに値を設定する<br>
	 * コピー時に無視リストの名前のフィールドの場合コピーを行わない。<br>
	 *
	 * @param data
	 *            コピー元
	 * @param target
	 *            コピー先
	 * @param ignoreList
	 *            無視リスト
	 */
	public static void copy(Object data, Object target, List<String> ignoreList) {

		// コピー元のクラス型
		Class<?> dataClass = data.getClass();
		// コピー先のクラス型
		Class<?> targetClass = target.getClass();
		try {
			for (Field targetField : targetClass.getDeclaredFields()) {
				if (ignore(ignoreList, targetField.getName())) {
					continue;
				}
				PropertyDescriptor targetPd = new PropertyDescriptor(targetField.getName(), targetClass);

				for (Field sourceField : dataClass.getDeclaredFields()) {
					if (isCopyTarget(sourceField, targetField)) {
						PropertyDescriptor sourcePd = new PropertyDescriptor(sourceField.getName(), dataClass);
						// 値を取得(getter呼び出し)
						Object value = sourcePd.getReadMethod().invoke(data);
						// targetに値を設定(setter呼び出し)
						targetPd.getWriteMethod().invoke(target, value);
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * コピー時にコピーを行わないfieldかどうかを判定する<br>
	 * 以下の場合、そのfieldではコピーを行わない<br>
	 * <ul>
	 * <li>fieldNameが"serialVersionUID"の場合</li>
	 * <li>fieldNameがignoreListに含まれてる場合</li>
	 * </ul>
	 *
	 * @param ignoreList
	 * @param fieldName
	 * @return
	 */
	private static boolean ignore(List<String> ignoreList, String fieldName) {
		return "serialVersionUID".equals(fieldName) || (!ignoreList.isEmpty() && ignoreList.contains(fieldName));
	}

	/**
	 * コピー対象かどうか判定する<br>
	 *
	 * @param dataField
	 *            Field コピー元のフィールドクラス
	 * @param targetField
	 *            Field コピー先のフィールドクラス
	 * @return
	 */
	private static boolean isCopyTarget(Field dataField, Field targetField) {
		String sourceFieldName = dataField.getName();
		Class<?> sourcefieldType = dataField.getType();
		String targetFieldName = targetField.getName();
		Class<?> targetFieldType = targetField.getType();
		return targetFieldName.equals(sourceFieldName) && targetFieldType.equals(sourcefieldType);
	}

	/**
	 * targetがnullかどうか判定する<br>
	 * 判定結果:nullの場合true, それ以外の場合false<br>
	 *
	 * @param target
	 *            検査対象インスタンス
	 * @return 判定結果
	 */
	public static boolean isNull(Object target) {
		return Objects.isNull(target);
	}

	/**
	 * targetがnullでないかどうか判定する<br>
	 * 判定結果:nullの場合false, それ以外の場合true<br>
	 *
	 * @see BeanUtil#isNull
	 * @param target
	 *            検査対象インスタンス
	 * @return 判定結果
	 */
	public static boolean notNull(Object target) {
		return !isNull(target);
	}

	/**
	 * パラメータ引数にしているクラス型を取得する<br>
	 *
	 * @param clazz
	 *            対象クラス
	 * @return
	 */
	public static Class<?> getParameterType(Class<?> clazz) {
		ParameterizedType paramType = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<?>) paramType.getActualTypeArguments()[0];

	}

}
