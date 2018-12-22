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
	 * @return name
	 */
	String getName();

	/**
	 * 指定したEnumと指定した値が一致する列挙を返す<br>
	 * 一致するenumがない場合nullを返す<br>
	 *
	 * @param view
	 *     BaseViewを継承したViewの列挙
	 * @param url
	 *     検査したいURL
	 * @return BaseViewを継承した列挙
	 */
	@SuppressWarnings("unchecked")
	public static <V extends BaseView> V of(Class<? extends BaseView> view, String url) {
		return (V) Stream.of(view.getEnumConstants())
						.filter(e -> e.getName().equals(url))
						.findFirst()
						.orElse(null);
	}
}
