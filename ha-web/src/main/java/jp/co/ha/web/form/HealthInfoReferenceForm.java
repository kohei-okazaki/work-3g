package jp.co.ha.web.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ha.common.log.annotation.Ignore;
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
	private BigDecimal healthInfoId;
	/** 登録日直接指定フラグ */
	@NotEmpty(message = "登録日直接指定フラグが未入力です")
	@Pattern(regexp = "^[0-9]*$", message = "登録日直接指定フラグが半角数字でありません")
	@Size(min = 1, max = 1, message = "登録日直接指定フラグが範囲外の値です")
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
	public BigDecimal getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 *            健康情報ID
	 */
	public void setHealthInfoId(BigDecimal healthInfoId) {
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
	 *            fromRegDate
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
	 *            toRegDate
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
	 *            登録日直接指定フラグ
	 */
	public void setRegDateSelectFlag(String regDateSelectFlag) {
		this.regDateSelectFlag = regDateSelectFlag;
	}

}
