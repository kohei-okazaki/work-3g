package jp.co.ha.web.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ha.common.web.BaseForm;

/**
 * 結果検索フォームクラス<br>
 *
 */
public class ResultSearchForm implements BaseForm {

	/** 登録日直接指定フラグ */
	@NotEmpty(message = "登録日直接指定フラグが未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録日直接指定フラグが半角数字でありません")
	@Size(min = 1, max = 1, message = "登録日直接指定フラグが範囲外の値です")
	private String regDateSelectFlag;
	/** 登録年(from) */
	@NotEmpty(message = "登録年(from)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録年(from)が半角数字でありません")
	@Size(min = 4, max = 4, message = "登録年(from)が範囲外の値です")
	private String fromRegYear;
	/** 登録月(from) */
	@NotEmpty(message = "登録月(from)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録月(from)が半角数字でありません")
	@Size(min = 2, max = 2, message = "登録月(from)が範囲外の値です")
	private String fromRegMonth;
	/** 登録日(from) */
	@NotEmpty(message = "登録日(from)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録日(from)が半角数字でありません")
	@Size(min = 2, max = 2, message = "登録日(from)が範囲外の値です")
	private String fromRegDay;
	/** 登録年(to) */
	@NotEmpty(message = "登録年(to)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録年(to)が半角数字でありません")
	@Size(min = 4, max = 4, message = "登録年(to)が範囲外の値です")
	private String toRegYear;
	/** 登録月(to) */
	@NotEmpty(message = "登録月(to)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録月(to)が半角数字でありません")
	@Size(min = 2, max = 2, message = "登録月(to)が範囲外の値です")
	private String toRegMonth;
	/** 登録日(to) */
	@NotEmpty(message = "登録日(to)が未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録日(to)が半角数字でありません")
	@Size(min = 2, max = 2, message = "登録日(to)が範囲外の値です")
	private String toRegDay;

	/**
	 * regDateSelectFlagを返す<br>
	 *
	 * @return regDateSelectFlag 登録日直接指定フラグ
	 */
	public String getRegDateSelectFlag() {
		return regDateSelectFlag;
	}

	/**
	 * regDateSelectFlagを設定する<br>
	 *
	 * @param regDateSelectFlag
	 *            登録日直接指定フラグ
	 */
	public void setRegDateSelectFlag(String regDateSelectFlag) {
		this.regDateSelectFlag = regDateSelectFlag;
	}

	/**
	 * fromRegYearを返す<br>
	 *
	 * @return fromRegYear 登録年(from)
	 */
	public String getFromRegYear() {
		return fromRegYear;
	}

	/**
	 * fromRegYearを設定する<br>
	 *
	 * @param fromRegYear
	 *            登録年(from)
	 */
	public void setFromRegYear(String fromRegYear) {
		this.fromRegYear = fromRegYear;
	}

	/**
	 * fromRegMonthを返す<br>
	 *
	 * @return fromRegMonth 登録月(from)
	 */
	public String getFromRegMonth() {
		return fromRegMonth;
	}

	/**
	 * fromRegMonthを設定する<br>
	 *
	 * @param fromRegMonth
	 *            登録月(from)
	 */
	public void setFromRegMonth(String fromRegMonth) {
		this.fromRegMonth = fromRegMonth;
	}

	/**
	 * fromRegDayを返す<br>
	 *
	 * @return fromRegDay 登録日(from)
	 */
	public String getFromRegDay() {
		return fromRegDay;
	}

	/**
	 * fromRegDayを設定する<br>
	 *
	 * @param fromRegDay
	 *            登録日(from)
	 */
	public void setFromRegDay(String fromRegDay) {
		this.fromRegDay = fromRegDay;
	}

	/**
	 * toRegYearを返す<br>
	 *
	 * @return toRegYear 登録年(to)
	 */
	public String getToRegYear() {
		return toRegYear;
	}

	/**
	 * toRegYearを設定する<br>
	 *
	 * @param toRegYear
	 *            登録年(to)
	 */
	public void setToRegYear(String toRegYear) {
		this.toRegYear = toRegYear;
	}

	/**
	 * toRegMonthを返す<br>
	 *
	 * @return toRegMonth 登録月(to)
	 */
	public String getToRegMonth() {
		return toRegMonth;
	}

	/**
	 * toRegMonthを設定する<br>
	 *
	 * @param toRegMonth
	 *            登録月(to)
	 */
	public void setToRegMonth(String toRegMonth) {
		this.toRegMonth = toRegMonth;
	}

	/**
	 * toRegDayを返す<br>
	 *
	 * @return toRegDay 登録日(to)
	 */
	public String getToRegDay() {
		return toRegDay;
	}

	/**
	 * toRegDayを設定する<br>
	 *
	 * @param toRegDay
	 *            登録日(to)
	 */
	public void setToRegDay(String toRegDay) {
		this.toRegDay = toRegDay;
	}

}
