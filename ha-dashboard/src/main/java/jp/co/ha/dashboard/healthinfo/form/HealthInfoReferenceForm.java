package jp.co.ha.dashboard.healthinfo.form;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.DateUtil.DateFormatType;
import jp.co.ha.common.validator.annotation.Date;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseForm;

/**
 * 健康情報照会画面フォームクラス
 *
 * @since 1.0
 */
public class HealthInfoReferenceForm implements BaseForm {

	/** 健康情報ID */
	@Pattern(regixPattern = RegexType.HALF_NUMBER, message = "健康情報IDは半角数字で入力してください")
	@Min(size = 1, message = "健康情報IDは1桁以上で入力してください")
	@Max(size = 8, message = "健康情報IDは8桁以下で入力してください")
	private String healthInfoId;
	/** 健康情報作成日直接指定フラグ */
	@Required(message = "健康情報作成日直接指定フラグが未入力です")
	@Flag(message = "健康情報作成日直接指定フラグの値が不正です")
	private String healthInfoRegDateSelectFlag;
	/** 健康情報作成日(開始) */
	@Date(formatType = DateFormatType.YYYYMMDD)
	private String fromHealthInfoRegDate;
	/** 健康情報作成日(終了) */
	@Date(formatType = DateFormatType.YYYYMMDD)
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
