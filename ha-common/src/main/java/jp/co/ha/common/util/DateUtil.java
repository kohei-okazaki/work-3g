package jp.co.ha.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 日付のUtilクラス<br>
 *
 */
public class DateUtil {

	public static final String YYYY_MM_DD_HH_MI_SS = "yyyy/MM/dd hh:mm:ss";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";

	private DateUtil() {
	}

	/**
	 * 取得したlocaleの時間から書式を整えた時間を返却
	 * @param locale
	 * @return 時刻
	 */
	public static String getFormattedTime(Locale locale) {

		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		return format.format(new Date()).replaceAll(" JST", StringUtil.EMPTY).trim();

	}

	/**
	 * 文字列型の日付をDate型の日付に変換する<br>
	 * @param target
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String target) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MI_SS);
		Date resultDate = null;
		try {
			resultDate = sdf.parse(target);
		} catch(ParseException e) {
			System.out.println("変換に失敗しました");
		}
		return resultDate;

	}

	/**
	 * 指定した日付の加算を行う<br>
	 * @param targetDate 元の日付
	 * @param add 加算する日数
	 * @return
	 */
	public static Date addDate(Date targetDate, int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.DATE, addDay);
		return calendar.getTime();
	}

	/**
	 * 指定した月の加算を行う<br>
	 * @param targetDate 元の日付
	 * @param addMonth 加算する月数
	 * @return
	 */
	public static Date addMonth(Date targetDate, int addMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.MONTH, addMonth);
		return calendar.getTime();
	}

	/**
	 * Date型を指定されたフォーマットに変える<br>
	 * @return
	 */
	public static String toString(Date targetDate, DateFormatDefine format) {

		if (Objects.isNull(format) || StringUtil.isEmpty(format.getValue())) {
			return StringUtil.EMPTY;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(targetDate);
	}
}
