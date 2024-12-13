package jp.co.ha.business.api.track.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Track APIの基底リクエスト情報クラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseTrackApiRequest {

}
