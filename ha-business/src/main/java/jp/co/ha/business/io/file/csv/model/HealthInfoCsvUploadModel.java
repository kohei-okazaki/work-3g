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
     * ユーザIDを返す
     *
     * @return seqUserId
     */
    public String getSeqUserId() {
        return seqUserId;
    }

    /**
     * ユーザIDを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(String seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * 身長を返す
     *
     * @return height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 身長を設定する
     *
     * @param height
     *     身長
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * 体重を返す
     *
     * @return weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 体重を設定する
     *
     * @param weight
     *     体重
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

}
