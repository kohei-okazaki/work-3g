package jp.co.ha.web.form;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.BaseForm;

/**
 * 健康情報照会画面フォームクラス<br>
 *
 */
public class HealthInfoReferenceForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** 健康情報ID */
	private Integer healthInfoId;
	/** 登録日直接指定フラグ */
	@Required(message = "登録日直接指定フラグが未入力です")
	@Flag(message = "登録日直接指定フラグの値が不正です")
	private String regDateSelectFlag;
	/** 登録日(開始) */
	private String fromRegDate;
	/** 登録日(終了) */
	private String toRegDate;

	/**
	 * healthInfoIdを返す<br>
	 *
	 * @return healthInfoId 健康情報ID
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * fromRegDateを返す<br>
	 *
	 * @return fromRegDate fromRegDate
	 */
	public String getFromRegDate() {
		return fromRegDate;
	}

	/**
	 * fromRegDateを設定する<br>
	 *
	 * @param fromRegDate
	 *     fromRegDate
	 */
	public void setFromRegDate(String fromRegDate) {
		this.fromRegDate = fromRegDate;
	}

	/**
	 * toRegDateを返す<br>
	 *
	 * @return toRegDate toRegDate
	 */
	public String getToRegDate() {
		return toRegDate;
	}

	/**
	 * toRegDateを設定する<br>
	 *
	 * @param toRegDate
	 *     toRegDate
	 */
	public void setToRegDate(String toRegDate) {
		this.toRegDate = toRegDate;
	}

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
	 *     登録日直接指定フラグ
	 */
	public void setRegDateSelectFlag(String regDateSelectFlag) {
		this.regDateSelectFlag = regDateSelectFlag;
	}

}
