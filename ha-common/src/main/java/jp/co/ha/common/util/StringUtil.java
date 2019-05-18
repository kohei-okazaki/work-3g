package jp.co.ha.common.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.UnExpectedException;

/**
 * 文字列のUtilクラス
 *
 */
public class StringUtil {

	/** カンマ */
	public static final String COMMA = ",";
	/** ハイフン */
	public static final String HYPHEN = "-";
	/** コロン */
	public static final String COLON = ":";
	/** ピリオド */
	public static final String PERIOD = ".";
	/** 空文字 */
	public static final String EMPTY = "";
	/** イコール */
	public static final String EQUAL = "=";
	/** 改行 */
	public static final String NEW_LINE = System.getProperty("line.separator");
	/** 半角スペース */
	public static final String SPACE = " ";
	/** 半角スラッシュ */
	public static final String THRASH = "/";
	/** アンダースコア */
	public static final String UNDER_SCORE = "_";

	/**
	 * プライベートコンストラクタ
	 */
	private StringUtil() {
	}

	/**
	 * 対象文字列<code>target</code>を区切り文字<code>delim</code>で、区切ったリストを返す
	 *
	 * @param target
	 *     対象文字列
	 * @param delim
	 *     区切り文字
	 * @return List<String>
	 */
	public static List<String> toStrList(String target, String delim) {
		return isEmpty(target) ? null : CollectionUtil.toList(target.split(delim));
	}

	/**
	 * 指定した文字列<code>target</code>が空文字の場合true、それ以外の場合false<br>
	 * トリム処理を行う
	 *
	 * @param target
	 *     対象文字列
	 * @return 判定結果
	 */
	public static boolean isEmpty(String target) {
		Predicate<String> isEmpty = s -> s == null || "".equals(s.trim());
		return isEmpty.test(target);
	}

	/**
	 * 指定した文字列<code>target</code>が空文字の場合true、それ以外の場合false<br>
	 *
	 * @param target
	 *     対象文字列
	 * @return 判定結果
	 */
	public static boolean isBrank(String target) {
		return BeanUtil.isNull(target) || EMPTY.equals(target);
	}

	/**
	 * 指定した文字列<code>target</code>に値が指定されていればtrue、それ以外の場合false
	 * @param target 対象文字列
	 * @return 判定結果
	 */
	public static boolean hasValue(String target) {
		return !isEmpty(target);
	}

	/**
	 * 半角スペースでPaddingする
	 *
	 * @see StringUtil#padding(String target, int length, String str, PaddingType paddingType)
	 *
	 * @param target
	 *     対象文字列
	 * @param length
	 *     padding後の文字長
	 * @param paddingType
	 *     Paddingタイプ(右詰/左詰)
	 * @return Padding後の文字列
	 * @throws BaseException
	 *     PaddingTypeの指定が不正の場合
	 */
	public static String paddingSpace(String target, int length, PaddingType paddingType) throws BaseException {
		return padding(target, length, SPACE, paddingType);
	}

	/**
	 * 指定した文字列を<code>str</code>でpaddingする<br>
	 * <code>target</code>の長さが<code>length</code>以上の文字列の場合、そのまま返す
	 *
	 * @param target
	 *     対象文字列
	 * @param length
	 *     padding後の文字長
	 * @param str
	 *     paddingしたい文字列
	 * @param paddingType
	 *     Paddingタイプ(右詰/左詰)
	 * @return Padding後の文字列
	 * @throws BaseException
	 *     PaddingTypeの指定が不正の場合
	 */
	public static String padding(String target, int length, String str, PaddingType paddingType) throws BaseException {
		if (length <= target.length()) {
			// 指定した文字長がlength以上の場合
			return target;
		}
		String body = target;
		while (body.length() < length) {
			if (PaddingType.LEFT == paddingType) {
				// 左詰
				body = body + str;
			} else if (PaddingType.RIGHT == paddingType) {
				// 右詰
				body = str + body;
			} else {
				throw new UnExpectedException(CommonErrorCode.UNEXPECTED_ERROR, "paddingTypeの指定が不正です");
			}
		}
		return body;
	}

	/**
	 * 指定したbyte配列を16進文字列に変換する
	 *
	 * @param bArray
	 *     byte配列
	 * @return 16進文字列
	 */
	public static String byteToHexString(byte[] bArray) {
		StringBuilder result = new StringBuilder();
		Stream.of(bArray).forEach(b -> result.append(String.format("%02x", b)));
		return result.toString();
	}

	/**
	 * Padding指定の列挙
	 *
	 */
	public static enum PaddingType {
		/** 右詰 */
		RIGHT,
		/** 左詰 */
		LEFT;
	}

}
