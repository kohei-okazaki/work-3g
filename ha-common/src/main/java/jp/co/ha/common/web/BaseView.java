package jp.co.ha.common.web;

/**
 * すべてのViewEnumはこのインターフェースを継承すること<br>
 *
 */
public interface BaseView {

	/**
	 * 名前を返す<br>
	 * @return
	 */
	String getName();

	/**
	 *
	 * @param url
	 * @return
	 */
	static <T extends BaseView> T of(String url) {

		return null;
	}
}
