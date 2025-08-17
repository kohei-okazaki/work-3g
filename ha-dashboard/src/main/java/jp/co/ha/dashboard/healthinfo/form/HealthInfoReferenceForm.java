package jp.co.ha.dashboard.healthinfo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 健康情報照会画面フォームクラス
 *
 * @version 1.0.0
 */
public class HealthInfoReferenceForm implements BaseForm {

    /** 健康情報ID */
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "健康情報IDは半角数字で入力してください")
    @Length(length = 1, mode = LengthMode.GREATER_EQUAL, message = "健康情報IDは1桁以上で入力してください")
    @Length(length = 8, mode = LengthMode.LESS_EQUAL, message = "健康情報IDは8桁以下で入力してください")
    private String seqHealthInfoId;
    /** 健康情報作成日直接指定フラグ */
    @Required(message = "健康情報作成日直接指定フラグが未入力です")
    @Flag(message = "健康情報作成日直接指定フラグの値が不正です")
    private String healthInfoRegDateSelectFlag;
    /** 健康情報作成日(開始) */
    @Required(message = "健康情報作成日が未入力です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromHealthInfoRegDate;
    /** 健康情報作成日(終了) */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toHealthInfoRegDate;

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
    public LocalDate getFromHealthInfoRegDate() {
        return fromHealthInfoRegDate;
    }

    /**
     * fromHealthInfoRegDateを設定する
     *
     * @param fromHealthInfoRegDate
     *     健康情報作成日(開始)
     */
    public void setFromHealthInfoRegDate(LocalDate fromHealthInfoRegDate) {
        this.fromHealthInfoRegDate = fromHealthInfoRegDate;
    }

    /**
     * toHealthInfoRegDateを返す
     *
     * @return toHealthInfoRegDate
     */
    public LocalDate getToHealthInfoRegDate() {
        return toHealthInfoRegDate;
    }

    /**
     * toHealthInfoRegDateを設定する
     *
     * @param toHealthInfoRegDate
     *     健康情報作成日(終了)
     */
    public void setToHealthInfoRegDate(LocalDate toHealthInfoRegDate) {
        this.toHealthInfoRegDate = toHealthInfoRegDate;
    }

}
