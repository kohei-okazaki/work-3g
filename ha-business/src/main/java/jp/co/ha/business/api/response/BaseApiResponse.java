package jp.co.ha.business.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jp.co.ha.common.api.response.BaseResponse;

/**
 * API共通レスポンス情報保持クラス
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public abstract class BaseApiResponse extends BaseResponse {

}
