package jp.co.ha.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * CollectionのUtilクラス<br>
 *
 */
public class CollectionUtil {

	/**
	 * プライベートコンストラクタ
	 */
	private CollectionUtil() {
	}

	/**
	 * 指定したリストが空かどうかを返す<br>
	 * null or 空の場合true, それ以外の場合false<br>
	 *
	 * @param list
	 *     対象のリスト
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		return BeanUtil.isNull(list) || list.isEmpty();
	}

	/**
	 * 指定したリストの最初の要素を返す<br>
	 *
	 * @param list
	 *     対象のリスト
	 * @return
	 */
	public static <T> T getFirst(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 指定したリストの最後の要素を返す<br>
	 *
	 * @param list
	 *     対象のリスト
	 * @return
	 */
	public static <T> T getLast(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	/**
	 * 指定したクラス型の空のリストを返す<br>
	 *
	 * @param clazz
	 *     リストの型
	 * @return
	 */
	public static <T> List<T> getEmptyList(Class<T> clazz) {
		return new ArrayList<>();
	}
}
