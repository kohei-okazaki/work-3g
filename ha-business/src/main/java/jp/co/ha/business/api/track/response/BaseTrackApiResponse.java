package jp.co.ha.business.api.track.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Track APIの基底レスポンス情報クラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseTrackApiResponse {

}
