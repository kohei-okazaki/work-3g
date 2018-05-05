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

	private DateUtil() {
	}

	/**
	 * システム日付を返す<br>
	 * @return
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 取得したlocaleの時間から書式を整えた時間を返却
	 * @param locale
	 * @return 時刻
	 */
	public static String getFormattedTime(Locale locale) {

		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		return format.format(DateUtil.getSysDate()).replaceAll(" JST", StringUtil.EMPTY).trim();

	}

	/**
	 * 指定した文字列型の日付をyyyy/MM/dd hh:mm:ssのフォーマットで返す<br>
	 * @param target
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String target) throws ParseException {
		return DateUtil.toDate(target, DateFormatDefine.YYYYMMDD_HHMMSS);
	}
	/**
	 * 指定した文字列型の日付を指定したフォーマットのDate型で返す<br>
	 * @param target
	 * @param dateFormatDefine
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String target, DateFormatDefine dateFormatDefine) {

		// フォーマットを設定
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatDefine.getValue());
		Date result = null;
		try {
			// Date型に変換
			result = sdf.parse(target);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;

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

	/**
	 * 指定した日付の時分秒を00:00:00に設定する<br>
	 * @param targetDate
	 * @return
	 */
	public static Date toStartDate(Date targetDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.set(0, 0, 0);
		return calendar.getTime();
	}
}
