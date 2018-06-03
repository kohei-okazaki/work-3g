package jp.co.ha.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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

		// コピー元のクラス型
		Class<?> dataClass = data.getClass();
		// コピー先のクラス型
		Class<?> targetClass = target.getClass();
		try {
			for (Field targetField : targetClass.getDeclaredFields()) {
				PropertyDescriptor targetPd = new PropertyDescriptor(targetField.getName(), targetClass);

				for (Field sourceField : dataClass.getDeclaredFields()) {
					if (isCopyTarget(sourceField, targetField)) {
						PropertyDescriptor sourcePd = new PropertyDescriptor(sourceField.getName(), dataClass);
						// 値を取得
						Object value = sourcePd.getReadMethod().invoke(data);
						// targetに値を設定
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

}
