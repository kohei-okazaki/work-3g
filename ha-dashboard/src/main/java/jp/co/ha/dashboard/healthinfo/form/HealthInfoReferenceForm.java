package jp.co.ha.dashboard.healthinfo.form;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
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
 * @version 1.0.0
 */
public class HealthInfoReferenceForm implements BaseForm {

    /** 健康情報ID */
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "健康情報IDは半角数字で入力してください")
    @Min(size = 1, message = "健康情報IDは1桁以上で入力してください")
    @Max(size = 8, message = "健康情報IDは8桁以下で入力してください")
    private String seqHealthInfoId;
    /** 健康情報作成日直接指定フラグ */
    @Required(message = "健康情報作成日直接指定フラグが未入力です")
    @Flag(message = "健康情報作成日直接指定フラグの値が不正です")
    private String healthInfoRegDateSelectFlag;
    /** 健康情報作成日(開始) */
    @Date(formatType = DateFormatType.YYYYMMDD, message = "健康情報作成日(開始)はyyyy/mm/dd形式で入力してください")
    private String fromHealthInfoRegDate;
    /** 健康情報作成日(終了) */
    @Date(formatType = DateFormatType.YYYYMMDD, message = "健康情報作成日(終了)はyyyy/mm/dd形式で入力してください")
    private String toHealthInfoRegDate;

    /**
     * seqHealthInfoIdを返す
     *
     * @return seqHealthInfoId
     */
    public String getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * seqHealthInfoIdを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(String seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
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
