package jp.co.ha.common.log;

import java.lang.reflect.Field;

import jp.co.ha.common.log.annotation.Mask;

/**
 * マスク判定クラス
 *
 * @since 1.0
 */
public class MaskExecutor {

	/** マスク文字列 */
	public static final String MASK = "****";

	/**
	 * マスク対象かどうか判定する<br>
	 * マスク対象の場合true, それ以外の場合false
	 *
	 * @param field
	 *     フィールド
	 * @return 判定結果
	 */
	public static boolean isMask(Field field) {
		return field.isAnnotationPresent(Mask.class);
	}

	/**
	 * マスク文字列を返す
	 *
	 * @param field
	 *     フィールド
	 * @return マスク文字列
	 */
	public static String getMask(Field field) {
		return field.getAnnotation(Mask.class).value();
	}

}
