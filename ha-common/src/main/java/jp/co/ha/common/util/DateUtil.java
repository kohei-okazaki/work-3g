package jp.co.ha.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiFunction;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * 日付のUtilクラス
 *
 * @version 1.0.0
 */
@Deprecated
public class DateUtil {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    /**
     * プライベートコンストラクタ
     */
    private DateUtil() {
    }

    /**
     * システム日付を返す
     *
     * @return システム日付
     */
    public static Date getSysDate() {
        return new Date();
    }

    /**
     * 指定した文字列型の日付をyyyy/MM/dd HH:mm:ssのフォーマットで返す
     *
     * @param target
     *     対象日付
     * @return 日付
     */
    public static Date toDate(String target) {
        return DateUtil.toDate(target, DateFormatType.YYYYMMDDHHMMSS);
    }

    /**
     * 指定した文字列型の日付を指定したフォーマットのDate型で返す
     *
     * @param strDate
     *     対象日付
     * @param format
     *     Dateフォーマット
     * @return 日付
     */
    public static Date toDate(String strDate, DateFormatType format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format.getValue());
            sdf.setLenient(false);
            return sdf.parse(strDate);
        } catch (ParseException e) {
            LOG.warn("指定された日付のフォーマットが不正です format -> " + format.getValue(), e);
            return null;
        }
    }

    /**
     * 指定した日付の加算を行う
     *
     * @param targetDate
     *     元の日付
     * @param addDay
     *     加算する日数
     * @return 加算した日付
     */
    public static Date addDate(Date targetDate, int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.DATE, addDay);
        return calendar.getTime();
    }

    /**
     * 指定した月の加算を行う
     *
     * @param targetDate
     *     元の日付
     * @param addMonth
     *     加算する月数
     * @return 加算した日付
     */
    public static Date addMonth(Date targetDate, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.MONTH, addMonth);
        return calendar.getTime();
    }

    /**
     * 指定した年の加算を行う
     *
     * @param targetDate
     *     元の日付
     * @param addYear
     *     加算する年数
     * @return 加算した日付
     */
    public static Date addYear(Date targetDate, int addYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.YEAR, addYear);
        return calendar.getTime();
    }

    /**
     * Date型を指定されたフォーマットに変える
     *
     * @param date
     *     対象日付
     * @param format
     *     Dateフォーマット
     * @return 文字列型の日付
     */
    public static String toString(Date date, DateFormatType format) {

        if (BeanUtil.isNull(format) || StringUtil.isEmpty(format.getValue())) {
            return StringUtil.EMPTY;
        }

        BiFunction<Date, DateFormatType, String> toStringFunction = (d,
                f) -> new SimpleDateFormat(f.getValue())
                        .format(d);
        return toStringFunction.apply(date, format);
    }

    /**
     * 指定した日付の時分秒を00:00:00を返す
     *
     * @param targetDate
     *     対象日付
     * @return 日付
     */
    public static Date toStartDate(Date targetDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateFormatType.YYYYMMDD.getValue() + " 00:00:00");
        return toDate(sdf.format(targetDate));
    }

    /**
     * 指定した日付の時分秒を23:59:59を返す
     *
     * @param targetDate
     *     対象日付
     * @return 日付
     */
    public static Date toEndDate(Date targetDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateFormatType.YYYYMMDD.getValue() + " 23:59:59");
        return toDate(sdf.format(targetDate));
    }

    /**
     * 同じ日付かどうか判定する<br>
     * 同じ日付の場合true, それ以外の場合false
     *
     * @param target1
     *     対象日
     * @param target2
     *     対象日
     * @return 判定結果
     */
    public static boolean isSameDate(Date target1, Date target2) {
        return target1.compareTo(target2) == 0;
    }

    /**
     * 比較対象日がstartDate < target < endDateかどうかを返す
     *
     * @param startDate
     *     開始日
     * @param target
     *     比較対象日
     * @param endDate
     *     終了日
     * @return 判定結果
     */
    public static boolean isBetWeenDate(Date startDate, Date target, Date endDate) {
        return startDate.before(target) && endDate.after(target);
    }

    /**
     * 指定した日付がシステム日付を超過しているかどうか判定する<br>
     * 超過してる場合true, それ以外の場合false
     *
     * @param target
     *     指定日
     * @param isEqual
     *     true:指定日を含む
     * @return 判定結果
     */
    public static boolean isAfter(Date target, boolean isEqual) {
        return isAfter(target, getSysDate(), isEqual);
    }

    /**
     * 指定した日付<code>target</code>が比較対象日付<code>compareDate</code>を超過しているかどうか判定する<br>
     * 超過している場合true, それ以外の場合falseを返す
     *
     * @param target
     *     指定日
     * @param compareDate
     *     比較対象日付
     * @param isEqual
     *     true:指定日を含む
     * @return 判定結果
     */
    public static boolean isAfter(Date target, Date compareDate, boolean isEqual) {
        if (isEqual) {
            return isSameDate(target, compareDate);
        }
        return target.after(compareDate);
    }

    /**
     * 指定した日付がシステム日付より過去かどうか判定する<br>
     * 過去の場合true, それ以外の場合falseを返す
     *
     * @param target
     *     指定日
     * @param isEqual
     *     true:指定日を含む
     * @return 判定結果
     */
    public static boolean isBefore(Date target, boolean isEqual) {
        return isBefore(target, getSysDate(), isEqual);
    }

    /**
     * 指定した日付<code>target</code>が比較対象日付<code>compareDate</code>より過去かどうか判定する<br>
     * 過去の場合true, それ以外の場合falseを返す
     *
     * @param target
     *     指定日
     * @param compareDate
     *     比較対象日付
     * @param isEqual
     *     true:指定日を含む
     * @return 判定結果
     */
    public static boolean isBefore(Date target, Date compareDate, boolean isEqual) {
        if (isEqual) {
            return isSameDate(target, compareDate);
        }
        return target.before(compareDate);
    }

    /**
     * 日付型チェックを行う。
     * <ul>
     * <li>正しい日付の場合、true</li>
     * <li>不正な日付の場合、false</li>
     * </ul>
     *
     * @param strDate
     *     検査対象日付
     * @param formatType
     *     日付フォーマット
     * @return 判定結果
     */
    public static boolean isDate(String strDate, DateFormatType formatType) {
        return toDate(strDate, formatType) != null;

    }

    /**
     * 指定した年月の最終日を返す
     *
     * @param year
     *     年
     * @param month
     *     月
     * @return 最終日
     */
    public static int getLastDay(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日付フォーマットの列挙
     *
     * @version 1.0.0
     */
    public static enum DateFormatType implements BaseEnum {

        /** YYYY/MM */
        YYYYMM("yyyy/MM"),
        /** YYYYMM */
        YYYYMM_NOSEP("yyyyMM"),
        /** YYYY/MM/DD */
        YYYYMMDD("yyyy/MM/dd"),
        /** YYYYMMDD */
        YYYYMMDD_NOSEP("yyyyMMdd"),
        /** YYYY/MM/DD HH:mm:ss */
        YYYYMMDDHHMMSS("yyyy/MM/dd HH:mm:ss"),
        /** YYYYMMDDHHmmss */
        YYYYMMDDHHMMSS_NOSEP("yyyyMMddHHmmss");

        /** 名前 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private DateFormatType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return DateFormatType
         */
        public static DateFormatType of(String value) {
            return BaseEnum.of(DateFormatType.class, value);
        }

    }
}
