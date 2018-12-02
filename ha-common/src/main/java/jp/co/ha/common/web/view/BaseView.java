package jp.co.ha.common.web.view;

import java.util.stream.Stream;

/**
 * viewEnumの基底インターフェース<br>
 * すべてのViewEnumはこのインターフェースを継承すること<br>
 *
 */
public interface BaseView {

	/**
	 * 名前を返す<br>
	 *
	 * @return
	 */
	String getName();

	/**
	 * 指定したEnumクラスの指定した値と一致するEnumを返す<br>
	 * 一致するenumがない場合nullを返す<br>
	 *
	 * @param viewClass
	 *     BaseViewを継承したViewのEnum
	 * @param value
	 *     検査したい値
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <V extends BaseView> V of(Class<? extends BaseView> view, String url) {
		return (V) Stream.of(view.getEnumConstants())
						.filter(e -> e.getName().equals(url))
						.findFirst()
						.orElse(null);
	}
}
