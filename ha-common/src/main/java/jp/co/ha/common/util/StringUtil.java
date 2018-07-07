package jp.co.ha.common.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文字列のUtilクラス<br>
 *
 */
public class StringUtil {

	public static final String COMMA = ",";
	public static final String HYPHEN = "-";
	public static final String COLON = ":";
	public static final String PERIOD = ".";
	public static final String EMPTY = "";
	public static final String EQUAL = "=";
	public static final String NEW_LINE = "\r\n";
	public static final String SPACE = " ";
	public static final String THRASH = "/";
	public static final String TRUE_FLAG = "1";
	public static final String FALSE_FLAG = "0";

	/**
	 * プライベートコンストラクタ<br>
	 */
	private StringUtil() {
	}

	/**
	 * 区切りたい文字列を区切り文字で、区切ったリストを返す
	 *
	 * @param target
	 *     対象文字列
	 * @param delim
	 *     区切り文字
	 * @return List<String>
	 */
	public static List<String> toStrList(String target, String delim) {
		return isEmpty(target) ? null : Stream.of(target.split(delim)).collect(Collectors.toList());
	}

	/**
	 * 空文字かどうか判定する<br>
	 * 空文字の場合true, それ以外の場合false<br>
	 *
	 * @param target
	 *     対象文字列
	 * @return 判定結果
	 */
	public static boolean isEmpty(String target) {
		return BeanUtil.isNull(target) || EMPTY.equals(target.trim());
	}

	/**
	 * 指定されたflagがtrueかどうか判定する<br>
	 *
	 * @param flag
	 *     フラグ
	 * @return
	 */
	public static boolean isTrue(String flag) {
		return TRUE_FLAG.equals(flag);
	}

	/**
	 * 指定されたflagがfalseかどうか判定する<br>
	 *
	 * @see StringUtil#isTrue(String)
	 * @param flag
	 *     フラグ
	 * @return
	 */
	public static boolean isFalse(String flag) {
		return !isTrue(flag);
	}

}
