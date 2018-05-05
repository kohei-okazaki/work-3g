package jp.co.ha.web.form;

import jp.co.ha.common.web.BaseForm;

/**
 * 結果検索フォームクラス<br>
 *
 */
public class ResultSearchForm implements BaseForm {

	/** 登録日直接指定フラグ */
	private String regDateSelectFlag;
	/** 登録年(from) */
	private String fromRegYear;
	/** 登録月(from) */
	private String fromRegMonth;
	/** 登録日(from) */
	private String fromRegDay;
	/** 登録年(to) */
	private String toRegYear;
	/** 登録月(to) */
	private String toRegMonth;
	/** 登録日(to) */
	private String toRegDay;

	/**
	 * regDateSelectFlagを返す<br>
	 * @return regDateSelectFlag
	 */
	public String getRegDateSelectFlag() {
		return regDateSelectFlag;
	}
	/**
	 * regDateSelectFlagを設定する<br>
	 * @param regDateSelectFlag
	 */
	public void setRegDateSelectFlag(String regDateSelectFlag) {
		this.regDateSelectFlag = regDateSelectFlag;
	}
	/**
	 * fromRegYearを返す<br>
	 * @return fromRegYear
	 */
	public String getFromRegYear() {
		return fromRegYear;
	}
	/**
	 * fromRegYearを設定する<br>
	 * @param fromRegYear
	 */
	public void setFromRegYear(String fromRegYear) {
		this.fromRegYear = fromRegYear;
	}
	/**
	 * fromRegMonthを返す<br>
	 * @return fromRegMonth
	 */
	public String getFromRegMonth() {
		return fromRegMonth;
	}
	/**
	 * fromRegMonthを設定する<br>
	 * @param fromRegMonth
	 */
	public void setFromRegMonth(String fromRegMonth) {
		this.fromRegMonth = fromRegMonth;
	}
	/**
	 * fromRegDayを返す<br>
	 * @return fromRegDay
	 */
	public String getFromRegDay() {
		return fromRegDay;
	}
	/**
	 * fromRegDayを設定する<br>
	 * @param fromRegDay
	 */
	public void setFromRegDay(String fromRegDay) {
		this.fromRegDay = fromRegDay;
	}
	/**
	 * toRegYearを返す<br>
	 * @return toRegYear
	 */
	public String getToRegYear() {
		return toRegYear;
	}
	/**
	 * toRegYearを設定する<br>
	 * @param toRegYear
	 */
	public void setToRegYear(String toRegYear) {
		this.toRegYear = toRegYear;
	}
	/**
	 * toRegMonthを返す<br>
	 * @return toRegMonth
	 */
	public String getToRegMonth() {
		return toRegMonth;
	}
	/**
	 * toRegMonthを設定する<br>
	 * @param toRegMonth
	 */
	public void setToRegMonth(String toRegMonth) {
		this.toRegMonth = toRegMonth;
	}
	/**
	 * toRegDayを返す<br>
	 * @return toRegDay
	 */
	public String getToRegDay() {
		return toRegDay;
	}
	/**
	 * toRegDayを設定する<br>
	 * @param toRegDay
	 */
	public void setToRegDay(String toRegDay) {
		this.toRegDay = toRegDay;
	}

}
