package jp.co.ha.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.type.Charset;

/**
 * 文字列のUtilクラス
 *
 * @version 1.0.0
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
    /** タブ */
    public static final String TAB = "	";
    /** 半角ｶﾅ */
    private static final char[] HALF_KANA = { '｡', '｢', '｣', '､', '･',
            'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ',
            'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ',
            'ﾀ', 'ﾁ', 'ﾂ', 'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ',
            'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ', 'ﾘ', 'ﾙ',
            'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ' };
    /** 全角カナ */
    private static final char[] FULLKANA = { '。', '「', '」', '、', '・',
            'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー', 'ア', 'イ',
            'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'サ', 'シ', 'ス', 'セ', 'ソ',
            'タ', 'チ', 'ツ', 'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ',
            'ヘ', 'ホ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ラ', 'リ', 'ル',
            'レ', 'ロ', 'ワ', 'ン', '゛', '゜' };
    /** 半角ｶﾅの1文字目 */
    private static final char HALF_KANA_FIRST_CHAR = HALF_KANA[0];
    /** 半角ｶﾅの最後の1文字目 */
    private static final char HALF_KANA_LAST_CHAR = HALF_KANA[HALF_KANA.length - 1];

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
     * 指定した文字列<code>target</code>が空文字の場合true、それ以外の場合false
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
     *
     * @param target
     *     対象文字列
     * @return 判定結果
     */
    public static boolean hasValue(String target) {
        return !isEmpty(target);
    }

    /**
     * 半角スペースでPaddingする
     *
     * @see StringUtil#padding
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
    public static String paddingSpace(String target, int length, PaddingType paddingType)
            throws BaseException {
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
     */
    public static String padding(String target, int length, String str,
            PaddingType paddingType) {
        if (length <= target.length()) {
            // 指定した文字長がlength以上の場合
            return target;
        }
        String body = target;
        while (body.length() < length) {
            switch (paddingType) {
            case LEFT:
                // 左詰
                body = body + str;
                break;
            case RIGHT:
                // 右詰
                body = str + body;
                break;
            default:
                throw new SystemRuntimeException(CommonErrorCode.UNEXPECTED_ERROR,
                        "paddingTypeの指定が不正です");
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
     * 指定した区切り文字で対象文字列を結合する
     *
     * @param delim
     *     区切り文字
     * @param ignoreRule
     *     結合しない条件
     * @param values
     *     対象文字列
     * @return 結合した文字列
     */
    public String join(String delim, Predicate<String> ignoreRule, String... values) {

        if (isEmpty(delim) || CollectionUtil.isEmpty(Arrays.asList(values))) {
            return null;
        }
        StringJoiner sj = new StringJoiner(delim);
        Arrays.asList(values).stream()
                .filter(e -> ignoreRule == null || !ignoreRule.test(e))
                .forEach(e -> sj.add(e));

        return sj.toString();
    }

    /**
     * 指定した文字列<code>str</code>を<code>length</code>で切り取る<br>
     *
     * @param str
     *     対象文字列
     * @param charset
     *     文字コード
     * @param length
     *     切りたい文字列の長さ
     * @return 切り取った後の文字列
     */
    public static String slice(String str, Charset charset, int length) {

        String result = null;
        if (isEmpty(str)) {
            return result;
        }

        try {
            byte[] b = str.getBytes(charset.getValue());
            if (b.length <= length) {
                result = str;
            } else {
                String s = new String(b, 0, length, charset.getValue());
                // 切り取った文字列の最後の1文字の位置
                int cutLastLen = s.length() - 1;
                // 切り取った文字列の最後の文字
                char c1 = s.charAt(cutLastLen);
                // 切り取る前の文字列の切り取り位置の文字
                char c2 = str.charAt(cutLastLen);
                if (c1 == c2) {
                    result = s;
                } else {
                    // 切り取った文字列の最後の1文字が半端なbyteの場合
                    result = slice(str, charset, length - 1);
                }
            }
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new SystemRuntimeException(e);
        }
        return result;

    }

    /**
     * 全角と半角スペースを除去した値を返す<br>
     * <ul>
     * <li>" A B "の場合"AB"となる</li>
     * </ul>
     *
     * @param value
     *     値
     * @return 全角と半角スペースを除去後の値
     */
    public static String trimFullAndHalfSpace(String value) {
        if (isEmpty(value)) {
            return value;
        }
        return value.replaceAll("(\\s|　)", "");
    }

    /**
     * 文字列中の半角ｶﾅを全角カナに変換した文字列を返す
     *
     * @param s
     *     変換前文字列
     * @return 変換後文字列
     */
    public static String toFullKana(String s) {
        if (s == null || s.length() == 0) {
            return s;
        } else if (s.length() == 1) {
            return toFullKana(s.charAt(0)) + "";
        } else {
            StringBuffer sb = new StringBuffer(s);
            int i = 0;
            for (i = 0; i < sb.length() - 1; i++) {
                char originalChar1 = sb.charAt(i);
                char originalChar2 = sb.charAt(i + 1);
                char margedChar = mergeChar(originalChar1, originalChar2);
                if (margedChar != originalChar1) {
                    sb.setCharAt(i, margedChar);
                    sb.deleteCharAt(i + 1);
                } else {
                    char convertedChar = toFullKana(originalChar1);
                    if (convertedChar != originalChar1) {
                        sb.setCharAt(i, convertedChar);
                    }
                }
            }
            if (i < sb.length()) {
                char originalChar1 = sb.charAt(i);
                char convertedChar = toFullKana(originalChar1);
                if (convertedChar != originalChar1) {
                    sb.setCharAt(i, convertedChar);
                }
            }
            return sb.toString();
        }

    }

    /**
     * 半角カタカナから全角カタカナへ変換します。
     *
     * @param c
     *     変換前の文字
     * @return 変換後の文字
     */
    private static char toFullKana(char c) {
        if (c >= HALF_KANA_FIRST_CHAR && c <= HALF_KANA_LAST_CHAR) {
            return FULLKANA[c - HALF_KANA_FIRST_CHAR];
        } else {
            return c;
        }
    }

    /**
     * 2文字目が濁点・半濁点で、1文字目に加えることができる場合は、合成した文字を返します。 <br>
     * 合成ができないときは、c1を返します。
     *
     * @param c1
     *     変換前の1文字目
     * @param c2
     *     変換前の2文字目
     * @return 変換後の文字
     */
    private static char mergeChar(char c1, char c2) {
        if (c2 == 'ﾞ') {
            if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎ".indexOf(c1) >= 0) {
                switch (c1) {
                case 'ｶ':
                    return 'ガ';
                case 'ｷ':
                    return 'ギ';
                case 'ｸ':
                    return 'グ';
                case 'ｹ':
                    return 'ゲ';
                case 'ｺ':
                    return 'ゴ';
                case 'ｻ':
                    return 'ザ';
                case 'ｼ':
                    return 'ジ';
                case 'ｽ':
                    return 'ズ';
                case 'ｾ':
                    return 'ゼ';
                case 'ｿ':
                    return 'ゾ';
                case 'ﾀ':
                    return 'ダ';
                case 'ﾁ':
                    return 'ヂ';
                case 'ﾂ':
                    return 'ヅ';
                case 'ﾃ':
                    return 'デ';
                case 'ﾄ':
                    return 'ド';
                case 'ﾊ':
                    return 'バ';
                case 'ﾋ':
                    return 'ビ';
                case 'ﾌ':
                    return 'ブ';
                case 'ﾍ':
                    return 'ベ';
                case 'ﾎ':
                    return 'ボ';
                }
            }
        } else if (c2 == 'ﾟ') {
            if ("ﾊﾋﾌﾍﾎ".indexOf(c1) >= 0) {
                switch (c1) {
                case 'ﾊ':
                    return 'パ';
                case 'ﾋ':
                    return 'ピ';
                case 'ﾌ':
                    return 'プ';
                case 'ﾍ':
                    return 'ペ';
                case 'ﾎ':
                    return 'ポ';
                }
            }
        }
        return c1;
    }

    /**
     * {@link StringUtils#abbreviate(String, int)}のラッパーメソッド
     *
     * @param str
     *     文字列
     * @param maxWidth
     *     最大文字数
     * @return 「...」で省略後の文字列
     */
    public static String abbreviate(String str, int maxWidth) {
        return StringUtils.abbreviate(str, maxWidth);
    }

    /**
     * {@link StringUtils#abbreviate(String, int, int)}のラッパーメソッド
     *
     * @param str
     *     文字列
     * @param offset
     *     開始位置
     * @param maxWidth
     *     最大文字数
     * @return 「...」で省略後の文字列
     */
    public static String abbreviate(String str, int offset, int maxWidth) {
        return StringUtils.abbreviate(str, offset, maxWidth);
    }

    /**
     * 指定して桁数のランダム文字列を返す<br>
     * {@link org.apache.commons.lang3.RandomStringUtils#randomAlphabetic(int)}のラッパーメソッド
     *
     * @param length
     *     文字列長
     * @return ランダム文字列
     */
    public static String getRandamStr(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Padding指定の列挙
     *
     * @version 1.0.0
     */
    public static enum PaddingType {
        /** 右詰 */
        RIGHT,
        /** 左詰 */
        LEFT;
    }

    /**
     * {@link org.springframework.util.StringUtils#capitalize(String)} のラッパーメソッド
     *
     * @param str
     *     対象文字列
     * @return capitalize後の文字列
     */
    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

}
