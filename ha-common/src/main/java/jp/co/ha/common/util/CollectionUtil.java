package jp.co.ha.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.ha.common.function.ListOperatorFunction;

/**
 * CollectionのUtilクラス
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
	 * @return 判定結果
	 */
	public static boolean isEmpty(List<?> list) {
		Predicate<List<?>> predicate = l -> BeanUtil.isNull(list) || list.isEmpty();
		return predicate.test(list);
	}

	/**
	 * 指定したリストの最初の要素を返す
	 *
	 * @param list
	 *     対象のリスト
	 * @return リストの最初の要素
	 */
	public static <T> T getFirst(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		ListOperatorFunction<T> lastOperator = l -> l.get(0);
		return lastOperator.get(list);
	}

	/**
	 * 指定したリストの最後の要素を返す
	 *
	 * @param list
	 *     対象のリスト
	 * @return リストの最後の要素
	 */
	public static <T> T getLast(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		ListOperatorFunction<T> lastOperator = l -> l.get(l.size() - 1);
		return lastOperator.get(list);
	}

	/**
	 * 指定したリストに要素が含まれているかどうか判定する
	 *
	 * @param list
	 *     対象のリスト
	 * @return リストに要素が1以上の場合true, それ以外の場合false
	 */
	public static boolean exists(List<?> list) {
		Predicate<List<?>> predicate = l -> !isEmpty(list);
		return predicate.test(list);
	}

	/**
	 * 指定した<code>count</code>の個数存在しているかどうか判定する
	 *
	 * @param list
	 *     対象のリスト
	 * @param count
	 *     要素数
	 * @return リストに要素が<code>count</code>以上の場合true, それ以外の場合false
	 */
	public static boolean existsCount(List<?> list, int count) {
		BiPredicate<List<?>, Integer> biPredicate = (l, c) -> {
			if (BeanUtil.isNull(l)) {
				return false;
			}
			return l.size() == c.intValue();
		};
		return biPredicate.test(list, count);
	}

	/**
	 * 指定したクラス型の空のリストを返す
	 *
	 * @param clazz
	 *     リストの型
	 * @return 空のリスト
	 */
	public static <T> List<T> getEmptyList(Class<T> clazz) {
		return new ArrayList<>();
	}

	/**
	 * 指定した配列をリストに変換する
	 *
	 * @param array
	 *     配列
	 * @return リスト
	 */
	public static <T> List<T> toList(T[] array) {
		return Stream.of(array).collect(Collectors.toList());
	}

	/**
	 * 指定したリストをコピーする
	 *
	 * @param srcList
	 *     コピー元リスト
	 * @return リスト
	 */
	public static <T> List<T> copyList(List<T> src) {
		return src.stream().collect(Collectors.toList());
	}

}
