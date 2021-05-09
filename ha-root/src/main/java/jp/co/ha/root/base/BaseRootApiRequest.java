package jp.co.ha.root.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.co.ha.common.web.form.JsonEntity;

/**
 * RootAPI基底リクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRootApiRequest extends JsonEntity {

}
