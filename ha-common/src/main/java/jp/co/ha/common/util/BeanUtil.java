package jp.co.ha.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
			for (Field targetField : BeanUtil.getFieldList(targetClass)) {
				if (ignore(ignoreList, targetField.getName())) {
					continue;
				}

				for (Field sourceField : BeanUtil.getFieldList(dataClass)) {
					if (isCopyTarget(sourceField, targetField)) {
						// getter呼び出し
						Method getter = getAccessor(sourceField.getName(), dataClass, AccessorType.GETTER);
						// setter呼び出し
						Method setter = getAccessor(sourceField.getName(), dataClass, AccessorType.SETTER);

						// 値を設定
						setter.invoke(target, getter.invoke(data));
					}
				}
			}
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
	 *            無視リスト
	 * @param fieldName
	 *            フィールド名
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
		return getParameterType(clazz, 0);
	}

	/**
	 * パラメータ引数にしているクラス型を取得する<br>
	 *
	 * @param clazz
	 *            対象クラス
	 * @param position
	 *            パラメータ引数の位置
	 * @return
	 */
	public static Class<?> getParameterType(Class<?> clazz, int position) {
		ParameterizedType paramType = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<?>) paramType.getActualTypeArguments()[position];
	}

	/**
	 * 指定したクラス型のフィールドをリストで返す<br>
	 *
	 * @param clazz
	 *            クラス型
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> tmpClass = clazz;
		while (BeanUtil.notNull(tmpClass)) {
			fieldList.addAll(List.of(tmpClass.getDeclaredFields()));
			tmpClass = tmpClass.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 指定したclazzのfieldNameのアクセサを返す<br>
	 * typeにSETTERを指定した場合、setter<br>
	 * それ以外の場合getterを取得<br>
	 *
	 * @param fieldName
	 *            フィールド名
	 * @param clazz
	 *            クラス
	 * @param type
	 *            SETTER/GETTER
	 * @return
	 */
	public static Method getAccessor(String fieldName, Class<?> clazz, AccessorType type) {
		Method accessor = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			accessor = type == AccessorType.SETTER ? pd.getWriteMethod() : pd.getWriteMethod();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return accessor;
	}

}
