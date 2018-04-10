package jp.co.ha.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;

public class StringUtil {

	public static final String COMMA = ",";
	public static final String HYPHEN = "-";
	public static final String COLON = ":";
	public static final String PERIOD = ".";
	public static final String EMPTY = "";
	public static final String EQUAL = "=";
	public static final String NEW_LINE = "\r\n";
	public static final String SPACE = " ";

	/** 半角数字 */
	public static final String HALF_NUMBER = "^[0-9]*$";
	/** 半角数字とピリオド */
	public static final String HALF_NUMBER_PERIOD = "^[0-9.]*$";

	/** 半角英数字 */
	public static final String HALF_CHAR = "^[0-9a-zA-Z]*$";


	private StringUtil() {

	}

	/**
	 * 半角数字かピリオドかどうか判定する<br>
	 * @param target
	 * @return
	 */
	public static boolean isHalfNumberPeriod(String target) {
		return Pattern.compile(HALF_NUMBER_PERIOD).matcher(target).find();
	}

	/**
	 * 指定された文字列が半角数字かどうか判定する<br>
	 * 半角数字の場合true, それ以外の場合falseを返す
	 * @param target
	 * @return
	 */
	public static boolean isHalfNumber(String target) {
		return Pattern.compile(HALF_NUMBER).matcher(target).find();
	}

	/**
	 * 指定された文字列が半角英数字かどうか判定する<br>
	 * 半角英数時の場合true, そうでない場合falseを返す<br>
	 * @param target
	 * @return
	 */
	public static boolean isHalfChar(String target) {
		return Pattern.compile(HALF_CHAR).matcher(target).find();
	}

	/**
	 * 区切りたい文字列を区切り文字で、区切ったリストを返す
	 * @param target
	 * @param delim
	 * @return result
	 */
	public static List<String> toStrList(String target, String delim) {

		if (Objects.isNull(target) || EMPTY.equals(target)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(target, COMMA);

		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken().trim());
		}
		return result;

	}

	/**
	 * 空文字かどうか判定する<br>
	 * 空文字の場合true, それ以外の場合false<br>
	 * @param target
	 * @return 判定結果
	 */
	public static boolean isEmpty(String target) {
		return Objects.isNull(target) || EMPTY.equals(target.trim());
	}

	/**
	 * 指定されたflagがtrueかどうか判定する<br>
	 * @param flag
	 * @return
	 */
	public static boolean isTrue(String flag) {
		return CodeManager.getInstance().isEquals(MainKey.FLAG, SubKey.TRUE, flag);
	}

	/**
	 * 指定されたflagがfalseかどうか判定する<br>
	 * @see StringUtil#isTrue(String)
	 * @param flag
	 * @return
	 */
	public static boolean isFalse(String flag) {
		return !isTrue(flag);
	}

}
