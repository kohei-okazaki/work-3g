package jp.co.ha.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	 * 指定した文字列型の日付をyyyy/MM/dd hh:mm:ssのフォーマットで返す<br>
	 * @param target
	 * @return
	 */
	public static Date toDate(String target) {
		return DateUtil.toDate(target, DateFormatDefine.YYYYMMDD_HHMMSS);
	}

	/**
	 * 指定した文字列型の日付を指定したフォーマットのDate型で返す<br>
	 * @param target
	 * @param dateFormatDefine
	 * @return
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
	 * 指定した年の加算を行う<br>
	 * @param targetDate
	 * @param addMonth
	 * @return
	 */
	public static Date addYear(Date targetDate, int addMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.YEAR, addMonth);
		return calendar.getTime();
	}

	/**
	 * Date型を指定されたフォーマットに変える<br>
	 * @param targetDate
	 * @param format
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
