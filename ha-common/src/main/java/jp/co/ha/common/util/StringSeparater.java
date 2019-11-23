package jp.co.ha.common.util;

/**
 * 文字列をbyte配列で分割するクラス
 *
 * @since 1.0
 */
public class StringSeparater {

	/**
	 * 指定した文字列<code>str</code>を<code>charset</code>のbyte配列に変換し、<br>
	 * <code>len</code>で区切った文字列を返す<br>
	 * <code>str</code>がnull or ブランクの場合、nullを返す
	 *
	 * @param str
	 *     対象文字列
	 * @param charset
	 *     文字コード
	 * @param len
	 *     分割byte位置
	 * @return 区切った文字列
	 */
	public String slice(String str, String charset, int len) {

		String result = null;
		if (StringUtil.isEmpty(str)) {
			return result;
		}

		try {

			byte[] b = str.getBytes(charset);
			String s = new String(b, 0, len, charset);
			if (b.length <= len) {
				// 対象文字列が切り取りたいbyte数以下の場合、文字列をそのまま返す
				result = s;
			} else {
				// 切り取った文字列の最後の1文字の位置
				int cutLastLen = s.length() - 1;
				// 切り取った文字列の最後の文字
				char c1 = s.charAt(cutLastLen);
				// 切り取る前の文字列の切り取り位置の文字
				char c2 = str.charAt(cutLastLen);
				if (c1 == c2) {
					// 切り取った文字列の最後の1文字が半端なbyteなかった場合
					result = s;
				} else {
					// 切り取った文字列の最後の1文字が半端なbyteの場合
					result = new String(b, 0, len - 1, charset);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
