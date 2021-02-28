package jp.co.ha.root.contents.healthinfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * 健康情報編集APIリクエスト
 *
 * @version 1.0.0
 */
public class HealthInfoEditApiRequest extends BaseRootApiRequest
        implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    private Integer seqUserId;

}
