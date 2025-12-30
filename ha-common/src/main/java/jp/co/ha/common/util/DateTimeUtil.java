package jp.co.ha.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * 日付のUtilクラス<br>
 * {@linkplain DateUtil}のJava8対応クラス
 *
 * @version 1.0.0
 */
public class DateTimeUtil {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(DateTimeUtil.class);

    /**
     * プライベートコンストラクタ
     */
    private DateTimeUtil() {
    }

    /**
     * システム日付を返す
     *
     * @return システム日付
     */
    public static LocalDateTime getSysDate() {
        return getSysDateByZoneId(ZoneIdType.TOKYO);
    }

    /**
     * 指定された{@linkplain ZoneIdType}より、システム日時を取得
     *
     * @param zoneIdType
     *     zoneIdType
     * @return システム日時
     */
    public static LocalDateTime getSysDateByZoneId(ZoneIdType zoneIdType) {
        return LocalDateTime.now(zoneIdType.getZoneId());
    }

    /**
     * 指定された日付の月末を返す
     *
     * @param localDateTime
     *     日付
     * @return 月末日
     */
    public static int getLastDayOfMonth(LocalDateTime localDateTime) {
        return getLastDayOfMonth(toLocalDate(localDateTime));
    }

    /**
     * 指定された日付の月末を返す
     *
     * @param localDate
     *     日付
     * @return 月末日
     */
    public static int getLastDayOfMonth(LocalDate localDate) {
        return localDate.lengthOfMonth();
    }

    /**
     * システム日付より取得した月の月初から月末までの日にちのリストを返す
     *
     * @return 月初から月末までの日にちのリスト
     */
    public static List<LocalDate> getLocalDateListThisMonth() {
        return getLocalDateList(toLocalDate(getSysDate()));
    }

    /**
     * 指定した日付の月の月初から月末までの日にちのリストを返す
     *
     * @param localDate
     *     日付
     * @return 月初から月末までの日にちのリスト
     */
    public static List<LocalDate> getLocalDateList(LocalDate localDate) {
        List<LocalDate> list = new ArrayList<>();
        for (int date = 1; date <= getLastDayOfMonth(localDate); date++) {
            list.add(LocalDate.of(localDate.getYear(), localDate.getMonth(),
                    date));
        }
        return list;
    }

    /**
     * 指定した日時の0時0分0秒を返す
     *
     * @param localDateTime
     *     日時
     * @return 指定した日時の0時0分0秒
     */
    public static LocalDateTime getStartDay(LocalDateTime localDateTime) {
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 指定した日時の0時0分0秒を返す
     *
     * @param localDate
     *     日時
     * @return 指定した日の0時0分0秒
     */
    public static LocalDateTime getStartDay(LocalDate localDate) {
        return localDate.atTime(0, 0, 0, 0);
    }

    /**
     * 指定した日時の23時59分59秒を返す
     *
     * @param localDateTime
     *     日時
     * @return 指定した日時の23時59分59秒
     */
    public static LocalDateTime getEndDay(LocalDateTime localDateTime) {
        return localDateTime.withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * 指定した日時の23時59分59秒を返す
     *
     * @param localDate
     *     日時
     * @return 指定した日の23時59分59秒
     */
    public static LocalDateTime getEndDay(LocalDate localDate) {
        return localDate.atTime(23, 59, 59);
    }

    /**
     * 指定した日付に加算日分だけ加算した日付を返す
     *
     * @param localDate
     *     日付
     * @param day
     *     加算日
     * @return 指定した日付に加算日分だけ加算した日付
     */
    public static LocalDate addDay(LocalDate localDate, int day) {
        return localDate.plusDays(day);
    }

    /**
     * 指定した日付に減算日分だけ減算した日付を返す
     *
     * @param localDate
     *     日付
     * @param day
     *     減算日
     * @return 指定した日付に減算日分だけ減算した日付
     */
    public static LocalDate minusDay(LocalDate localDate, int day) {
        return localDate.minusDays(day);
    }

    /**
     * 指定した日付に加算月分だけ加算した日付を返す
     *
     * @param localDate
     *     日付
     * @param month
     *     加算月
     * @return 指定した日付に加算月分だけ加算した日付
     */
    public static LocalDate addMonth(LocalDate localDate, int month) {
        return localDate.plusMonths(month);
    }

    /**
     * 指定した日付に減算月分だけ減算した日付を返す
     *
     * @param localDate
     *     日付
     * @param month
     *     減算月
     * @return 指定した日付に減算月分だけ減算した日付
     */
    public static LocalDate minusMonth(LocalDate localDate, int month) {
        return localDate.minusMonths(month);
    }

    /**
     * 指定した日付に加算週分だけ加算した日付を返す
     *
     * @param localDate
     *     日付
     * @param week
     *     加算週
     * @return 指定した日付に加算週分だけ加算した日付
     */
    public static LocalDate addWeek(LocalDate localDate, int week) {
        return localDate.plusWeeks(week);
    }

    /**
     * 指定した日付に減算週分だけ減算した日付を返す
     *
     * @param localDate
     *     日付
     * @param week
     *     減算週
     * @return 指定した日付に減算週分だけ減算した日付
     */
    public static LocalDate minusWeek(LocalDate localDate, int week) {
        return localDate.minusWeeks(week);
    }

    /**
     * 指定した日付に加算年分だけ加算した日付を返す
     *
     * @param localDate
     *     日付
     * @param year
     *     加算年
     * @return 指定した日付に加算年分だけ加算した日付
     */
    public static LocalDate addYear(LocalDate localDate, int year) {
        return localDate.plusYears(year);
    }

    /**
     * 指定した日付に減算年分だけ減算した日付を返す
     *
     * @param localDate
     *     日付
     * @param year
     *     減算年
     * @return 指定した日付に減算年分だけ減算した日付
     */
    public static LocalDate minusYear(LocalDate localDate, int year) {
        return localDate.minusYears(year);
    }

    /**
     * end - beginの差をlongで取得<br>
     *
     * <pre>
     * begin = 21:30とend = 23:00 の場合
     * return = 90
     * </pre>
     *
     * @param begin
     *     開始時間
     * @param end
     *     終了時間
     * @return 差分
     * @see #diffLocalTimeByChronoUnit(LocalTime, LocalTime, ChronoUnit)
     */
    public static long diffLocalTimeByMinute(LocalTime begin, LocalTime end) {
        return diffLocalTimeByChronoUnit(begin, end, ChronoUnit.MINUTES);
    }

    /**
     * 指定した{@linkplain ChronoUnit}でend - beginの差を取得
     *
     * @param begin
     *     開始時間
     * @param end
     *     終了時間
     * @param unit
     *     XX単位で取得する差分
     * @return 差分
     */
    public static long diffLocalTimeByChronoUnit(LocalTime begin, LocalTime end,
            ChronoUnit unit) {
        return unit.between(begin, end);
    }

    /**
     * 検査日時(<code>localDateTime</code>)が比較日時(<code>when</code>)より未来かどうか判定する<br>
     * <ul>
     * <li><code>isEquals = true</code>の場合</li>
     * <ul>
     * <li>比較日時 <= 検査日時の場合、true</li>
     * <li>検査日時 < 比較日時の場合、false</li>
     * </ul>
     * <li><code>isEquals = false</code>の場合</li>
     * <ul>
     * <li>比較日時 < 検査日時の場合、true</li>
     * <li>検査日時 <= 比較日時の場合、false</li>
     * </ul>
     * </ul>
     *
     * @param localDateTime
     *     検査日時
     * @param when
     *     比較日時
     * @param isEquals
     *     同時刻を超過したに含むかどうか
     * @return 判定結果
     */
    public static boolean isAfter(LocalDateTime localDateTime,
            LocalDateTime when, boolean isEquals) {
        if (isEquals) {
            if (localDateTime.isEqual(when)) {
                return true;
            }
            return localDateTime.isAfter(when);
        }
        return localDateTime.isAfter(when);
    }

    /**
     * 検査日(<code>localDate</code>)が比較日(<code>when</code>)より未来かどうか判定する<br>
     * <ul>
     * <li><code>isEquals = true</code>の場合</li>
     * <ul>
     * <li>比較日 <= 検査日の場合、true</li>
     * <li>検査日 < 比較日の場合、false</li>
     * </ul>
     * <li><code>isEquals = false</code>の場合</li>
     * <ul>
     * <li>比較日 < 検査日の場合、true</li>
     * <li>検査日 <= 比較日の場合、false</li>
     * </ul>
     * </ul>
     *
     * @param localDate
     *     検査日
     * @param when
     *     比較日
     * @param isEquals
     *     同時刻を超過したに含むかどうか
     * @return 判定結果
     */
    public static boolean isAfter(LocalDate localDate,
            LocalDate when, boolean isEquals) {
        if (isEquals) {
            if (localDate.isEqual(when)) {
                return true;
            }
            return localDate.isAfter(when);
        }
        return localDate.isAfter(when);
    }

    /**
     * 検査時間(<code>localTime</code>)が比較時間(<code>when</code>)より未来かどうか判定する<br>
     * <ul>
     * <li><code>isEquals = true</code>の場合</li>
     * <ul>
     * <li>比較時間 <= 検査時間の場合、true</li>
     * <li>検査時間 < 比較時間の場合、false</li>
     * </ul>
     * <li><code>isEquals = false</code>の場合</li>
     * <ul>
     * <li>比較時間 < 検査時間の場合、true</li>
     * <li>検査時間 <= 比較時間の場合、false</li>
     * </ul>
     * </ul>
     *
     * @param localTime
     *     検査時間
     * @param when
     *     比較時間
     * @param isEquals
     *     同時刻を超過したに含むかどうか
     * @return 判定結果
     */
    public static boolean isAfter(LocalTime localTime, LocalTime when, boolean isEquals) {
        if (isEquals) {
            if (localTime.equals(when)) {
                return true;
            }
            return localTime.isAfter(when);
        }
        return localTime.isAfter(when);
    }

    /**
     * 検査日時(<code>localDateTime</code>)が比較日時(<code>when</code>)より過去どうか判定する<br>
     * <ul>
     * <li><code>isEquals = true</code>の場合</li>
     * <ul>
     * <li>検査日時 <= 比較日時の場合、true</li>
     * <li>比較日時 < 検査日時の場合、false</li>
     * </ul>
     * <li><code>isEquals = false</code>の場合</li>
     * <ul>
     * <li>検査日時 < 比較日時の場合、true</li>
     * <li>比較日時 <= 検査日時の場合、false</li>
     * </ul>
     * </ul>
     *
     * @param localDateTime
     *     検査日時
     * @param when
     *     比較日時
     * @param isEquals
     *     同時刻を超過したに含むかどうか
     * @return 評価値
     */
    public static boolean isBefore(LocalDateTime localDateTime,
            LocalDateTime when, boolean isEquals) {
        if (isEquals) {
            if (localDateTime.isEqual(when)) {
                return true;
            }
            return localDateTime.isBefore(when);
        }
        return localDateTime.isBefore(when);
    }

    /**
     * 検査日時(<code>localDateTime</code>)が比較日時(<code>when</code>)より過去どうか判定する<br>
     * <ul>
     * <li><code>isEquals = true</code>の場合</li>
     * <ul>
     * <li>検査日時 <= 比較日時の場合、true</li>
     * <li>比較日時 < 検査日時の場合、false</li>
     * </ul>
     * <li><code>isEquals = false</code>の場合</li>
     * <ul>
     * <li>検査日時 < 比較日時の場合、true</li>
     * <li>比較日時 <= 検査日時の場合、false</li>
     * </ul>
     * </ul>
     *
     * @param localDate
     *     検査日時
     * @param when
     *     比較日時
     * @param isEquals
     *     同時刻を超過したに含むかどうか
     * @return 評価値
     */
    public static boolean isBefore(LocalDate localDate, LocalDate when,
            boolean isEquals) {
        if (isEquals) {
            if (localDate.isEqual(when)) {
                return true;
            }
            return localDate.isBefore(when);
        }
        return localDate.isBefore(when);
    }

    /**
     * 指定した検査日時が<b>開始日時より大きく、終了日時より小さい</b>かどうかを検査する<br>
     * startDate < target < endDate
     *
     * @param startDate
     *     開始日時
     * @param target
     *     検査日時
     * @param endDate
     *     終了日時
     * @return 評価値
     */
    public static boolean isBetWeen(LocalDateTime startDate,
            LocalDateTime target, LocalDateTime endDate) {
        return startDate.isBefore(target) && endDate.isAfter(target);
    }

    /**
     * 指定した{@linkplain LocalDate}型の日付を{@linkplain DateFormatType}で整形した文字列を返す
     *
     * @param localDate
     *     日付
     * @param formatType
     *     フォーマット
     * @return 整形後文字列
     */
    public static String toString(LocalDate localDate,
            DateFormatType formatType) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(formatType.getValue(), Locale.JAPANESE);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 指定した{@linkplain LocalDateTime}型の日付を{@linkplain DateFormatType}で整形した文字列を返す
     *
     * @param localDateTime
     *     日付
     * @param formatType
     *     フォーマット
     * @return 整形後文字列
     */
    public static String toString(LocalDateTime localDateTime,
            DateFormatType formatType) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
                formatType.getValue(), Locale.JAPANESE);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 指定した{@linkplain LocalTime}型の時刻を{@linkplain DateFormatType}で整形した文字列を返す
     *
     * @param localTime
     *     時刻
     * @param formatType
     *     フォーマット
     * @return 整形後文字列
     */
    public static String toString(LocalTime localTime, DateFormatType formatType) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(formatType.getValue(), Locale.JAPANESE);
        return localTime.format(dateTimeFormatter);
    }

    /**
     * 指定した{@linkplain LocalDateTime}型の日付を{@linkplain Date}に変換する
     *
     * @param localDateTime
     *     日付
     * @return {@linkplain Date}型の日付
     */
    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 指定した{@linkplain LocalDate}型の日付を{@linkplain Date}に変換する
     *
     * @param localDate
     *     日付
     * @return {@linkplain Date}型の日付
     */
    public static Date toDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zone);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 指定した{@linkplain Date}型の日付を{@linkplain LocalDateTime}に変換する
     *
     * @param date
     *     日付
     * @return {@linkplain LocalDateTime}型の日付
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 指定した{@linkplain Date}型の日付を{@linkplain LocalDate}に変換する
     *
     * @param date
     *     日付
     * @return {@linkplain LocalDate}型の日付
     */
    public static LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        return zonedDateTime.toLocalDate();
    }

    /**
     * 文字列型の日付を{@linkplain LocalDateTime}型の日付に変換する
     *
     * @param strDate
     *     日付
     * @param formatType
     *     フォーマット
     * @return {@linkplain LocalDateTime}型の日付
     */
    public static LocalDateTime toLocalDateTime(String strDate,
            DateFormatType formatType) {
        if (strDate == null || formatType == null) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern(formatType.getValue())
                .withLocale(Locale.JAPANESE)
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(strDate, dtf);
    }

    /**
     * 文字列型の日付を{@linkplain LocalDate}型の日付に変換する
     *
     * @param strDate
     *     日付
     * @param formatType
     *     フォーマット
     * @return {@linkplain LocalDate}型の日付
     */
    public static LocalDate toLocalDate(String strDate, DateFormatType formatType) {
        if (strDate == null || formatType == null) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern(formatType.getValue())
                .withLocale(Locale.JAPANESE)
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(strDate, dtf);
    }

    /**
     * 指定した{@linkplain LocalDate}型の日付を{@linkplain LocalDateTime}型の日付に変換する
     *
     * @param localDate
     *     日付
     * @return {@linkplain LocalDateTime}型の日付
     */
    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(),
                localDate.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * 指定した{@linkplain LocalDateTime}型の日付を{@linkplain LocalDate}型の日付に変換する
     *
     * @param localDateTime
     *     日付
     * @return {@linkplain LocalDate}型の日付
     */
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth());
    }

    /**
     * 指定された日付が検査日付フォーマット形式である場合Trueを返す<br>
     * 上記以外の場合、falseを返す
     *
     * @param date
     *     検査文字列日付
     * @param format
     *     検査日付フォーマット
     * @return 検査結果
     */
    public static boolean isDate(String date, DateFormatType format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format.getValue());
            sdf.setLenient(false);
            return sdf.parse(date) != null;
        } catch (ParseException e) {
            LOG.warn("指定された日付のフォーマットが不正です format -> " + format.getValue(), e);
            return false;
        }
    }

    /**
     * {@linkplain ZoneId}の列挙型
     *
     * @version 1.0.0
     */
    public static enum ZoneIdType implements BaseEnum {

        /** 東京 */
        TOKYO("Asia/Tokyo");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private ZoneIdType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * {@linkplain ZoneId}を返す
         *
         * @return ZoneId
         */
        public ZoneId getZoneId() {
            return ZoneId.of(this.value);
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return ZoneIdType
         */
        public static ZoneIdType of(String value) {
            return BaseEnum.of(ZoneIdType.class, value);
        }
    }

    /**
     * 日付フォーマットの列挙
     *
     * @version 1.0.0
     */
    public static enum DateFormatType implements BaseEnum {

        /** YYYY/MM */
        YYYYMM("yyyy/MM"),
        /** uuuu/MM */
        YYYYMM_STRICT("uuuu/MM"),
        /** YYYYMM */
        YYYYMM_NOSEP("yyyyMM"),
        /** uuuuMM */
        YYYYMM_NOSEP_STRICT("uuuuMM"),
        /** YYYY/MM/DD */
        YYYYMMDD("yyyy/MM/dd"),
        /** uuuu/MM/DD */
        YYYYMMDD_STRICT("uuuu/MM/dd"),
        /** YYYYMMDD */
        YYYYMMDD_NOSEP("yyyyMMdd"),
        /** uuuuMMDD */
        YYYYMMDD_NOSEP_STRICT("uuuuMMdd"),
        /** YYYY/MM/DD HH:mm:ss */
        YYYYMMDDHHMMSS("yyyy/MM/dd HH:mm:ss"),
        /** YYYYMMDDHHmmss */
        YYYYMMDDHHMMSS_NOSEP("yyyyMMddHHmmss"),
        /** uuuu/MM/DD HH:mm:ss */
        YYYYMMDDHHMMSS_STRICT("uuuu/MM/dd HH:mm:ss"),
        /** uuuuMMDDHHmmss */
        YYYYMMDDHHMMSS_NOSEP_STRICT("uuuuMMddHHmmss"),
        /** YYYY/MM/DD HH:MI:SS.sss */
        YYYYMMDDHHMISSsss("yyyy/MM/dd HH:mm:ss.SSS");

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
