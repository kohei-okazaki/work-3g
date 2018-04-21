package jp.co.ha.web.form;

import jp.co.ha.common.web.BaseForm;

/**
 * 結果検索フォームクラス<br>
 *
 */
public class ResultSearchForm implements BaseForm {

	/** 登録年 */
	private String regYear;
	/** 登録月 */
	private String regMonth;
	/** 登録日 */
	private String regDay;


	/**
	 * regYearを返す<br>
	 * @return regYear
	 */
	public String getRegYear() {
		return regYear;
	}
	/**
	 * regYearを設定する<br>
	 * @param regYear
	 */
	public void setRegYear(String regYear) {
		this.regYear = regYear;
	}
	/**
	 * regMonthを返す<br>
	 * @return regMonth
	 */
	public String getRegMonth() {
		return regMonth;
	}
	/**
	 * regMonthを設定する<br>
	 * @param regMonth
	 */
	public void setRegMonth(String regMonth) {
		this.regMonth = regMonth;
	}
	/**
	 * regDayを返す<br>
	 * @return regDay
	 */
	public String getRegDay() {
		return regDay;
	}
	/**
	 * regDayを設定する<br>
	 * @param regDay
	 */
	public void setRegDay(String regDay) {
		this.regDay = regDay;
	}

}
