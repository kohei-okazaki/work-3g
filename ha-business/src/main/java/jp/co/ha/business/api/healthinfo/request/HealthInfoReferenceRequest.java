package jp.co.ha.business.api.healthinfo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseRestApiRequest;

/**
 * 健康情報照会リクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthInfoReferenceRequest extends BaseRestApiRequest
        implements BaseApiRequest {

    /** 健康情報ID */
    @Required(message = "seqHealthInfoIdが未設定です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "seqHealthInfoIdが半角数字でありません")
    @JsonProperty("seqHealthInfoId")
    private Integer seqHealthInfoId;

    /**
     * seqHealthInfoIdを返す
     *
     * @return seqHealthInfoId
     */
    public Integer getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * seqHealthInfoIdを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(Integer seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

}
