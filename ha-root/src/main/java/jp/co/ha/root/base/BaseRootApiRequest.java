package jp.co.ha.root.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * RootAPI基底リクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRootApiRequest extends JsonEntity {

}
