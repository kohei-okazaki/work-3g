package jp.co.ha.common.type;

import java.util.stream.Stream;

/**
 * 列挙型の基底インターフェース<br>
 * 単一の値を持つ列挙の親インターフェースとする<br>
 *
 */
public interface BaseEnum {

	/**
	 * 値を返す
	 *
	 * @return value
	 */
	String getValue();

	/**
	 * 指定した文字列が同じ値か検証する
	 *
	 * @param value
	 *     値
	 * @return 同じ値の場合true、それ以外の場合false
	 */
	boolean is(String value);

	/**
	 * 指定した値と列挙と指定した値が一致する列挙型を返す<br>
	 * 一致するenumがない場合nullを返す<br>
	 *
	 * @param enumClass
	 *     BaseEnumを継承した列挙型
	 * @param value
	 *     値
	 * @return BaseEnumを継承した列挙型
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseEnum> T of(Class<? extends BaseEnum> enumClass, String value) {
		return (T) Stream.of(enumClass.getEnumConstants())
				.filter(e -> e.getValue().equals(value))
				.findFirst()
				.orElse(null);
	}

}
