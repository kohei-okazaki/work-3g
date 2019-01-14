package jp.co.ha.common.util;

import java.util.List;

import jp.co.ha.common.exception.ErrorCode;
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
	/** 真偽値:true */
	public static final String TRUE_FLAG = "1";
	/** 真偽値:false */
	public static final String FALSE_FLAG = "0";

	/** CR */
	public static final String CR = "\r";
	/** LF */
	public static final String LF = "\n";
	/** CRLF */
	public static final String CRLF = CR + LF;

	/**
	 * プライベートコンストラクタ<br>
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
	 * 空文字かどうか判定する<br>
	 * トリム処理を行う<br>
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
	 * 空文字かどうか判定する<br>
	 * 空文字の場合、true, それ以外の場合false
	 *
	 * @param target
	 *     対象文字列
	 * @return 判定結果
	 */
	public static boolean isBrank(String target) {
		return BeanUtil.isNull(target) || EMPTY.equals(target);
	}

	/**
	 * 指定された<code>flag</code>がtrueかどうか判定する<br>
	 *
	 * @param flag
	 *     フラグ
	 * @return 判定結果
	 */
	public static boolean isTrue(String flag) {
		return TRUE_FLAG.equals(flag);
	}

	/**
	 * 指定された<code>flag</code>がfalseかどうか判定する<br>
	 *
	 * @see StringUtil#isTrue(String)
	 * @param flag
	 *     フラグ
	 * @return 判定結果
	 */
	public static boolean isFalse(String flag) {
		return !isTrue(flag);
	}

	/**
	 * 指定した文字列を半角スペースでpaddingする<br>
	 * <code>target</code>の長さが<code>count</code>以上の文字列の場合、そのまま返す<br>
	 *
	 * @param target
	 *     対象文字列
	 * @param count
	 *     全文字列
	 * @param paddingType
	 *     Paddingタイプ(右詰/左詰)
	 * @return Padding後の文字列
	 * @throws UnExpectedException PaddingTypeの指定が不正の場合
	 */
	public static String padding(String target, int count, PaddingType paddingType) throws UnExpectedException {
		if (count <= target.length()) {
			// 指定した文字長がcount以上の場合
			return target;
		}
		String body = target;
		while (body.length() < count) {
			if (PaddingType.LEFT == paddingType) {
				// 左詰
				body = body + StringUtil.SPACE;
			} else if (PaddingType.RIGHT == paddingType) {
				// 右詰
				body = StringUtil.SPACE + body;
			} else {
				throw new UnExpectedException(ErrorCode.UNEXPECTED_ERROR, "paddingTypeの指定が不正です");
			}
		}
		return body;
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
