package jp.co.ha.business.io.file.csv.model;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * 健康情報CSVアップロードモデル
 *
 * @version 1.0.0
 */
public class HealthInfoCsvUploadModel implements BaseCsvModel {

    /** ユーザID */
    @Required(message = "ユーザIDが未指定です")
    private String seqUserId;
    /** 身長 */
    @Mask
    @Required(message = "身長が未指定です")
    @Pattern(regixPattern = RegexType.DECIMAL, message = "身長が半角数字とピリオドでありません")
    private String height;
    /** 体重 */
    @Mask
    @Required(message = "体重が未指定です")
    @Pattern(regixPattern = RegexType.DECIMAL, message = "体重が半角数字とピリオドでありません")
    private String weight;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public String getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(String seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * heightを返す
     *
     * @return height
     */
    public String getHeight() {
        return height;
    }

    /**
     * heightを設定する
     *
     * @param height
     *     身長
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * weightを返す
     *
     * @return weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * weightを設定する
     *
     * @param weight
     *     体重
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

}
