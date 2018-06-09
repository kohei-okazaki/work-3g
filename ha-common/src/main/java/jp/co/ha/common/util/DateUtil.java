package jp.co.ha.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日付のUtilクラス<br>
 *
 */
public class DateUtil {

	/**
	 * プライベートコンストラクタ<br>
	 */
	private DateUtil() {
	}

	/**
	 * システム日付を返す<br>
	 *
	 * @return システム日付
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 指定した文字列型の日付をyyyy/MM/dd hh:mm:ssのフォーマットで返す<br>
	 *
	 * @param target
	 *            対象日付
	 * @return
	 */
	public static Date toDate(String target) {
		return DateUtil.toDate(target, DateFormatDefine.YYYYMMDD_HHMMSS);
	}

	/**
	 * 指定した文字列型の日付を指定したフォーマットのDate型で返す<br>
	 *
	 * @param target
	 *            対象日付
	 * @param dateFormatDefine
	 *            Dateフォーマット
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
	 *
	 * @param targetDate
	 *            元の日付
	 * @param add
	 *            加算する日数
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
	 *
	 * @param targetDate
	 *            元の日付
	 * @param addMonth
	 *            加算する月数
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
	 *
	 * @param targetDate
	 *            元の日付
	 * @param addYear
	 *            加算する年数
	 * @return
	 */
	public static Date addYear(Date targetDate, int addYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.YEAR, addYear);
		return calendar.getTime();
	}

	/**
	 * Date型を指定されたフォーマットに変える<br>
	 *
	 * @param targetDate
	 *            対象日付
	 * @param format
	 *            Dateフォーマット
	 * @return
	 */
	public static String toString(Date targetDate, DateFormatDefine format) {

		if (BeanUtil.isNull(format) || StringUtil.isEmpty(format.getValue())) {
			return StringUtil.EMPTY;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(targetDate);
	}

	/**
	 * 指定した日付の時分秒を00:00:00に設定する<br>
	 *
	 * @param targetDate
	 *            対象日付
	 * @return
	 */
	public static Date toStartDate(Date targetDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.set(0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 比較対象日が開始日 < 比較対象日 < endDateかどうかを返す<br>
	 *
	 * @param startDate
	 *            開始日
	 * @param target
	 *            比較対象日
	 * @param endDate
	 *            終了日
	 * @return
	 */
	public static boolean isBetWeenDate(Date startDate, Date target, Date endDate) {
		return startDate.before(target) && endDate.after(target);
	}
}
