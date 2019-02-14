package jp.co.ha.web.form;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 健康情報照会画面フォームクラス
 *
 */
public class HealthInfoReferenceForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** 健康情報ID */
	@Pattern(regixPattern = RegixType.HALF_NUMBER, message = "健康情報IDは半角数字で入力してください")
	@Min(size = 1, message = "健康情報IDは1桁以上で入力してください")
	@Max(size = 8, message = "健康情報IDは8桁以下で入力してください")
	private String healthInfoId;
	/** 健康情報作成日直接指定フラグ */
	@Required(message = "健康情報作成日直接指定フラグが未入力です")
	@Flag(message = "健康情報作成日直接指定フラグの値が不正です")
	private String healthInfoRegDateSelectFlag;
	/** 健康情報作成日(開始) */
	private String fromHealthInfoRegDate;
	/** 健康情報作成日(終了) */
	private String toHealthInfoRegDate;

	/**
	 * healthInfoIdを返す
	 *
	 * @return healthInfoId
	 */
	public String getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(String healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * healthInfoRegDateSelectFlagを返す
	 *
	 * @return healthInfoRegDateSelectFlag
	 */
	public String getHealthInfoRegDateSelectFlag() {
		return healthInfoRegDateSelectFlag;
	}

	/**
	 * healthInfoRegDateSelectFlagを設定する
	 *
	 * @param healthInfoRegDateSelectFlag
	 *     健康情報作成日直接指定フラグ
	 */
	public void setHealthInfoRegDateSelectFlag(String healthInfoRegDateSelectFlag) {
		this.healthInfoRegDateSelectFlag = healthInfoRegDateSelectFlag;
	}

	/**
	 * fromHealthInfoRegDateを返す
	 *
	 * @return fromHealthInfoRegDate
	 */
	public String getFromHealthInfoRegDate() {
		return fromHealthInfoRegDate;
	}

	/**
	 * fromHealthInfoRegDateを設定する
	 *
	 * @param fromHealthInfoRegDate
	 *     健康情報作成日(開始)
	 */
	public void setFromHealthInfoRegDate(String fromHealthInfoRegDate) {
		this.fromHealthInfoRegDate = fromHealthInfoRegDate;
	}

	/**
	 * toHealthInfoRegDateを返す
	 *
	 * @return toHealthInfoRegDate
	 */
	public String getToHealthInfoRegDate() {
		return toHealthInfoRegDate;
	}

	/**
	 * toHealthInfoRegDateを設定する
	 *
	 * @param toHealthInfoRegDate
	 *     健康情報作成日(終了)
	 */
	public void setToHealthInfoRegDate(String toHealthInfoRegDate) {
		this.toHealthInfoRegDate = toHealthInfoRegDate;
	}

}
