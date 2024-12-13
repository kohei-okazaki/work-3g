package jp.co.ha.business.api.healthinfoapp.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * 健康情報照会APIリクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthInfoReferenceApiRequest extends BaseAppApiRequest
        implements BaseApiRequest {

    /** 健康情報ID */
    @Required(message = "seq_health_info_idが未設定です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "seq_health_info_idが半角数字でありません")
    @JsonProperty("seq_health_info_id")
    private Long seqHealthInfoId;

    /**
     * seqHealthInfoIdを返す
     *
     * @return seqHealthInfoId
     */
    public Long getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * seqHealthInfoIdを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(Long seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

}
